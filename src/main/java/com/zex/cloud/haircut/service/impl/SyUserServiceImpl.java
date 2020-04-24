package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zex.cloud.haircut.config.Constants;
import com.zex.cloud.haircut.dto.BrokenLinePoint;
import com.zex.cloud.haircut.entity.*;
import com.zex.cloud.haircut.enums.*;
import com.zex.cloud.haircut.exception.AuthenticationException;
import com.zex.cloud.haircut.exception.ExistsException;
import com.zex.cloud.haircut.exception.NotFoundException;
import com.zex.cloud.haircut.mapper.SyUserMapper;
import com.zex.cloud.haircut.params.*;
import com.zex.cloud.haircut.response.SyUserDetail;
import com.zex.cloud.haircut.security.RequestUser;
import com.zex.cloud.haircut.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zex.cloud.haircut.util.PasswordEncoder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-02-16
 */
@Service
public class SyUserServiceImpl extends ServiceImpl<SyUserMapper, SyUser> implements ISyUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ISyGroupUserRelService iSyGroupUserRelService;

    @Autowired
    private ISyUserRoleRelService iSyUserRoleRelService;

    @Autowired
    private ISyUserExtensionService iSyUserExtensionService;

    @Autowired
    private ISyUserSocialService iSyUserSocialService;

    @Autowired
    private ISmShopService iSmShopService;

    @Autowired
    private IUmPopularizeService iUmPopularizeService;

    @Autowired
    private IUmUserCollectService iUmUserCollectService;

    @Override
    @Transactional
    public SyUser save(SyUserParam param, String operatorIp, Long operatorId) {
        valid(null, param.getUsername(), param.getMobile(), param.getEmail());
        SyUser syUser = adaptUser(param, operatorIp, operatorId);
        save(syUser);
        iSyGroupUserRelService.updateRelations(syUser.getId(), param.getGroupIds());
        iSyUserRoleRelService.updateRelations(syUser.getId(), param.getRoleIds());
        return syUser;
    }

    private void valid(Long id, String username, String mobile, String email) {
        List<SyUser> users = list(new LambdaQueryWrapper<SyUser>()
                .nested(i -> i.eq(SyUser::getUsername, username).or()
                        .eq(StringUtils.isNotBlank(mobile), SyUser::getMobile, mobile)
                        .eq(StringUtils.isNotBlank(email), SyUser::getEmail, email))
                .ne(id != null, SyUser::getId, id));
        for (SyUser user : users) {
            if (StringUtils.equals(user.getUsername(), username)) {
                throw new ExistsException("用户名已存在");
            }
            if (StringUtils.equals(user.getMobile(), mobile)) {
                throw new ExistsException("手机号码已存在");
            }
            if (StringUtils.equals(user.getEmail(), email)) {
                throw new ExistsException("邮箱已存在");
            }
        }
    }

    @Override
    @Transactional
    public SyUser update(Long id, SyUserParam param, String operatorIp, Long operatorId) {
        valid(id, param.getUsername(), param.getMobile(), param.getEmail());
        SyUser syUser = adaptUser(param, operatorIp, operatorId);
        syUser.setId(id);
        updateById(syUser);
        iSyGroupUserRelService.updateRelations(syUser.getId(), param.getGroupIds());
        iSyUserRoleRelService.updateRelations(syUser.getId(), param.getRoleIds());
        return syUser;
    }

    @Override
    public RequestUser getRequestUser(String username, String password, ClientType clientType) {
        SyUser syUser = getOne(new LambdaQueryWrapper<SyUser>()
                .eq(SyUser::getUsername, username));
        if (syUser == null) {
            throw new AuthenticationException("用户不存在");
        }
        if (!passwordEncoder.matches(password, syUser.getPassword())) {
            throw new AuthenticationException("密码错误");
        }
        if (!syUser.getEnable()) {
            throw new AuthenticationException("用户不可用");
        }
        if (syUser.getLocked()) {
            throw new AuthenticationException("用户以被锁定");
        }
        if (syUser.getExpireAt() != null && syUser.getExpireAt().isBefore(LocalDateTime.now())) {
            throw new AuthenticationException("用户已过期");
        }
        syUser.setLoginAt(LocalDateTime.now());
        updateById(syUser);
        return adaptRequestUser(syUser, clientType, null, null);
    }

    private RequestUser adaptRequestUser(SyUser syUser, ClientType clientType, String sessionKey, WxUserInfoParam userInfo) {
        RequestUser user = new RequestUser();
        user.setAvatar(syUser.getAvatar());
        user.setId(syUser.getId());
        user.setNickname(syUser.getNickname());
        user.setUsername(syUser.getUsername());
        user.setMobile(syUser.getMobile());
        if (userInfo != null){
            user.setOpenId(userInfo.getOpenId());
        }
        List<String> roles = iSyUserRoleRelService.getRequestRoles(user.getId());
        user.setRoles(roles);
        if (CollectionUtils.isNotEmpty(roles) && roles.contains(Constants.SHOP_ADMIN_ROLE_NAME)) {
            Long shopId = iSmShopService.getShopIdByUserId(user.getId());
            user.setShopId(shopId);
        }
        user.setClientType(clientType);
        SyUserSocial syUserSocial = iSyUserSocialService.getById(syUser.getId());
        if (syUserSocial != null) {
            user.setOpenId(syUserSocial.getOpenId());

        }
        SyUserExtension syUserExtension = iSyUserExtensionService.getById(syUser.getId());
        if (syUserExtension != null) {
            user.setUnionId(syUserExtension.getUnionId());
        }
        user.setSessionKey(sessionKey);
        return user;
    }

    @Override
    public IPage<SyUser> list(String keywords, Boolean locked, Boolean enable, Long groupId, Page<SyUser> page) {
        return baseMapper.list(keywords, locked, enable, groupId, page);
    }

    @Override
    public SyUserDetail detail(Long id) {
        SyUser syUser = getById(id);
        SyUserDetail detail = new SyUserDetail();
        BeanUtils.copyProperties(syUser, detail);
        List<Long> roleIds = iSyUserRoleRelService.getRoleIdsByUserId(id);
        List<Long> groupIds = iSyGroupUserRelService.getGroupIdsByUserId(id);
        detail.setRoleIds(roleIds);
        detail.setGroupIds(groupIds);
        return detail;
    }

    @Override
    public void adminPassword(Long id, String password, String operatorIp, Long operatorId) {
        SyUser syUser = new SyUser();
        syUser.setOperatorIp(operatorIp);
        syUser.setOperatorAt(LocalDateTime.now());
        syUser.setOperatorId(operatorId);
        syUser.setId(id);
        syUser.setPassword(passwordEncoder.encode(password));
        updateById(syUser);
    }

    @Override
    public void password(PasswordParam param) {
        SyUser syUser = getOne(new LambdaQueryWrapper<SyUser>().eq(SyUser::getUsername, param.getUsername()));
        if (syUser == null) {
            throw new NotFoundException("用户不存在");
        }
        if (!passwordEncoder.matches(param.getOldPassword(), syUser.getPassword())) {
            throw new NotFoundException("旧密码不对");
        }
        syUser.setPassword(passwordEncoder.encode(param.getNewPassword()));
        updateById(syUser);
    }

    @Override
    public void passwordCurrent(PasswordCurrentParam param, Long id) {
        SyUser syUser = getOne(new LambdaQueryWrapper<SyUser>().eq(SyUser::getId, id));
        if (syUser == null) {
            throw new NotFoundException("用户不存在");
        }
        if (!passwordEncoder.matches(param.getOldPassword(), syUser.getPassword())) {
            throw new NotFoundException("旧密码不对");
        }
        syUser.setPassword(passwordEncoder.encode(param.getNewPassword()));
        updateById(syUser);
    }

    @Override
    public RequestUser getRequestUser(Long userId) {
        SyUser syUser = getById(userId);
        return adaptRequestUser(syUser, null, null, null);
    }

    @Override
    public List<BrokenLinePoint> brokenLines(LocalDate startAt, LocalDate endAt) {
        return baseMapper.brokenLines(startAt, endAt);
    }

    @Override
    public int count(LocalDate startAt, LocalDate endAt) {
        return baseMapper.count(startAt, endAt);
    }

    @Override
    @Transactional
    public RequestUser getRequestUser(WxUserInfoParam userInfo, String sessionKey, ClientType clientType, Long popularizeId, PopularizeType popularizeType) {
        SyUserSocial social = iSyUserSocialService.getByOpenId(userInfo.getOpenId(), userInfo.getWatermark().getAppid());
        if (social == null) {
            //保存用户信息
            SyUser syUser = new SyUser();
            syUser.setPassword("un_used");
            syUser.setLoginAt(LocalDateTime.now());
            syUser.setEnable(true);
            syUser.setLocked(false);
            syUser.setNickname(userInfo.getNickName());
            syUser.setAvatar(userInfo.getAvatarUrl());
            syUser.setGender(GenderType.getByValue(userInfo.getGender()));
            save(syUser);
            //保存默认用户角色信息
            iSyGroupUserRelService.save(Constants.USER_GROUP_ID, syUser.getId());
            social = new SyUserSocial();
            social.setAppId(userInfo.getWatermark().getAppid());
            social.setOpenId(userInfo.getOpenId());
            social.setSocialType(SocialType.WX);
            social.setUserId(syUser.getId());
            iSyUserSocialService.save(social);
            //保存拓展信息
            if (StringUtils.isNotBlank(userInfo.getUnionId())){
                SyUserExtension extension = iSyUserExtensionService.getByUnionId(userInfo.getUnionId());
                if (extension == null) {
                    extension = new SyUserExtension();
                    extension.setUnionId(userInfo.getUnionId());
                    extension.setUserId(syUser.getId());
                    iSyUserExtensionService.save(extension);
                }
            }
            //保存推广信息
            if (popularizeId != null && popularizeType != null) {
                UmPopularize umPopularize = new UmPopularize();
                umPopularize.setTargetId(popularizeId);
                umPopularize.setTargetType(popularizeType);
                umPopularize.setUserId(syUser.getId());
                umPopularize.setAmount(new BigDecimal("0"));
                umPopularize.setStatus(PopularizeStatus.PENDING_FIRST_PAY);
                iUmPopularizeService.save(umPopularize);
                //收藏
                if (popularizeType == PopularizeType.SHOP) {
                    iUmUserCollectService.collect(popularizeId, CollectType.SHOP, syUser.getId());
                }
            }

        }
        SyUser syUser = getById(social.getUserId());
        return adaptRequestUser(syUser, clientType, sessionKey,userInfo);
    }

    @Override
    public SyUser register(SyUserRegisterParam param, String ip) {
        valid(null, param.getUsername(), null, null);
        SyUser syUser = new SyUser();
        syUser.setUsername(param.getUsername());
        String password = passwordEncoder.encode(param.getPassword());
        syUser.setPassword(password);
        save(syUser);
        //默认用户组
        iSyGroupUserRelService.save(Constants.USER_GROUP_ID, syUser.getId());
        return syUser;
    }


    private SyUser adaptUser(SyUserParam param, String operatorIp, Long operatorId) {
        SyUser syUser = new SyUser();
        BeanUtils.copyProperties(param, syUser);
        syUser.setOperatorIp(operatorIp);
        syUser.setOperatorAt(LocalDateTime.now());
        syUser.setOperatorId(operatorId);
        if (StringUtils.isNotBlank(param.getPassword())) {
            String password = passwordEncoder.encode(param.getPassword());
            syUser.setPassword(password);
        }
        return syUser;
    }

}
