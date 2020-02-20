package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zex.cloud.haircut.entity.SyUser;
import com.zex.cloud.haircut.exception.AuthenticationException;
import com.zex.cloud.haircut.exception.ExistsException;
import com.zex.cloud.haircut.exception.NotFoundException;
import com.zex.cloud.haircut.mapper.SyUserMapper;
import com.zex.cloud.haircut.params.PasswordCurrentParam;
import com.zex.cloud.haircut.params.PasswordParam;
import com.zex.cloud.haircut.params.SyUserParam;
import com.zex.cloud.haircut.response.SyUserDetail;
import com.zex.cloud.haircut.security.RequestUser;
import com.zex.cloud.haircut.service.ISyGroupUserRelService;
import com.zex.cloud.haircut.service.ISyUserRoleRelService;
import com.zex.cloud.haircut.service.ISyUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zex.cloud.haircut.util.PasswordEncoder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public SyUser save(SyUserParam param, String operatorIp, Long operatorId) {
        valid(null, param.getUsername(),param.getMobile(),param.getEmail());
        SyUser syUser = adaptUser(param, operatorIp, operatorId);
        save(syUser);
        iSyGroupUserRelService.updateRelations(syUser.getId(), param.getGroupIds());
        iSyUserRoleRelService.updateRelations(syUser.getId(), param.getRoleIds());
        return syUser;
    }

    private void valid(Long id, String username, String mobile, String email) {
       List<SyUser>  users= list(new LambdaQueryWrapper<SyUser>()
                .nested(i-> i.eq(SyUser::getUsername, username).or()
                .eq(StringUtils.isNotBlank(mobile),SyUser::getMobile,mobile)
                .eq(StringUtils.isNotBlank(email),SyUser::getEmail,email))
                .ne(id != null, SyUser::getId, id));
        for (SyUser user : users) {
            if (StringUtils.equals(user.getUsername(),username)){
                throw new ExistsException("用户名已存在");
            }
            if (StringUtils.equals(user.getMobile(),mobile)){
                throw new ExistsException("手机号码已存在");
            }
            if (StringUtils.equals(user.getEmail(),email)){
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
    public RequestUser getRequestUser(String username, String password) {
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
        RequestUser user = new RequestUser();
        user.setAvatar(syUser.getAvatar());
        user.setId(syUser.getId());
        user.setNickname(syUser.getNickname());
        user.setUsername(syUser.getUsername());
        user.setMobile(syUser.getMobile());
        List<String> roles = iSyUserRoleRelService.getRequestRoles(user.getId());
        user.setRoles(roles);
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
        SyUser syUser = getOne(new LambdaQueryWrapper<SyUser>().eq(SyUser::getUsername,param.getUsername()));
        if (syUser == null){
            throw new NotFoundException("用户不存在");
        }
        if (!passwordEncoder.matches(param.getOldPassword(),syUser.getPassword())){
            throw new NotFoundException("旧密码不对");
        }
        syUser.setPassword(passwordEncoder.encode(param.getNewPassword()));
        updateById(syUser);
    }

    @Override
    public void passwordCurrent(PasswordCurrentParam param, Long id) {
        SyUser syUser = getOne(new LambdaQueryWrapper<SyUser>().eq(SyUser::getId,id));
        if (syUser == null){
            throw new NotFoundException("用户不存在");
        }
        if (!passwordEncoder.matches(param.getOldPassword(),syUser.getPassword())){
            throw new NotFoundException("旧密码不对");
        }
        syUser.setPassword(passwordEncoder.encode(param.getNewPassword()));
        updateById(syUser);
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
