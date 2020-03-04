package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zex.cloud.haircut.config.Constants;
import com.zex.cloud.haircut.dto.BrokenLinePoint;
import com.zex.cloud.haircut.entity.SmHalfTime;
import com.zex.cloud.haircut.entity.SmShop;
import com.zex.cloud.haircut.entity.SyUser;
import com.zex.cloud.haircut.entity.SyUserRoleRel;
import com.zex.cloud.haircut.enums.ShopWorkStatus;
import com.zex.cloud.haircut.exception.ExistsException;
import com.zex.cloud.haircut.mapper.SmShopMapper;
import com.zex.cloud.haircut.params.SmHalfTimeParam;
import com.zex.cloud.haircut.params.SmShopParam;
import com.zex.cloud.haircut.service.ISmHalfTimeService;
import com.zex.cloud.haircut.service.ISmShopService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zex.cloud.haircut.service.ISmShopServiceRelationService;
import com.zex.cloud.haircut.service.ISyUserRoleRelService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-02-19
 */
@Service
public class SmShopServiceImpl extends ServiceImpl<SmShopMapper, SmShop> implements ISmShopService {

    @Autowired
    private ISmShopServiceRelationService iSmShopServiceRelationService;

    @Autowired
    private ISyUserRoleRelService iSyUserRoleRelService;
    @Autowired
    private ISmHalfTimeService iSmHalfTimeService;
    @Override
    public IPage<SmShop> list(Page<SmShop> convert, String keywords, ShopWorkStatus workStatus, String provinceCode, String cityCode, String districtCode, Double longitude, Double latitude) {
        return baseMapper.list(convert, keywords, workStatus, provinceCode, cityCode, districtCode, longitude, latitude);
    }

    @Override
    @Transactional
    public SmShop customSave(SmShop smShop) {
        valid(null, smShop.getUserId());
        // 保存角色信息
        iSyUserRoleRelService.save(Constants.SHOP_ADMIN_ROLE_ID,smShop.getUserId());
        //保存店铺
        save(smShop);
        return smShop;
    }



    @Override
    public SmShop updateCurrent(Long id, SmShopParam param) {
        SmShop smShop =  new SmShop();
        BeanUtils.copyProperties(param, smShop);
        updateById(smShop);
        return smShop;
    }



    @Override
    public SmShop update(Long id, SmShopParam param) {
        SmShop smShop = getById(id);
        if (!param.getUserId().equals(smShop.getUserId())){
            valid(id,param.getUserId());
            //移除旧用户的角色信息
            iSyUserRoleRelService.remove(Constants.SHOP_ADMIN_ROLE_ID,smShop.getUserId());
            iSyUserRoleRelService.save(Constants.SHOP_ADMIN_ROLE_ID,param.getUserId());
        }
        smShop.setId(id);
        BeanUtils.copyProperties(param, smShop);
        updateById(smShop);
        return smShop;
    }

    @Override
    public SmShop currentWorkStatus(Long id, ShopWorkStatus workStatus) {
        SmShop smShop = new SmShop();
        smShop.setId(id);
        smShop.setWorkStatus(workStatus);
        updateById(smShop);
        return smShop;
    }

    @Override
    public void updateTitle(Long id, List<Long> titleIds) {
        iSmShopServiceRelationService.updateRelations(id,titleIds);
    }

    @Override
    public void updateHalfTime(Long shopId, List<SmHalfTimeParam> params) {
        iSmHalfTimeService.updateRelations(shopId,params);
    }

    @Override
    public Long getShopIdByUserId(Long id) {
        return baseMapper.getShopIdByUserId(id);
    }

    @Override
    public List<BrokenLinePoint> brokenLines(LocalDate startAt, LocalDate endAt) {
        return baseMapper.brokenLines(startAt,endAt);
    }

    @Override
    public int count(LocalDate startAt, LocalDate endAt) {
        return baseMapper.count(startAt,endAt);
    }

    private void valid(Long id, Long userId) {
        int count = count(new LambdaQueryWrapper<SmShop>().eq(SmShop::getUserId, userId)
                .ne(id != null, SmShop::getId, id));
        if (count > 0) {
            throw new ExistsException("该用户已经拥有一个店铺了");
        }

    }
}
