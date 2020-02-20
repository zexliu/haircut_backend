package com.zex.cloud.haircut.service.impl;

import com.zex.cloud.haircut.entity.HmStylist;
import com.zex.cloud.haircut.exception.ForbiddenException;
import com.zex.cloud.haircut.mapper.HmStylistMapper;
import com.zex.cloud.haircut.params.HmStylistParam;
import com.zex.cloud.haircut.service.IHmStylistDomainRelationService;
import com.zex.cloud.haircut.service.IHmStylistService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zex.cloud.haircut.service.IHmStylistServiceRelationService;
import com.zex.cloud.haircut.service.IHmWorkCaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-02-20
 */
@Service
public class HmStylistServiceImpl extends ServiceImpl<HmStylistMapper, HmStylist> implements IHmStylistService {

    @Autowired
    private IHmStylistDomainRelationService iHmStylistDomainRelationService;

    @Autowired
    private IHmWorkCaseService iHmWorkCaseService;

    @Autowired
    private IHmStylistServiceRelationService iHmStylistServiceRelationService;
    @Override
    @Transactional
    public HmStylist save(HmStylistParam param,Long shopId) {
        HmStylist stylist = new HmStylist();
        BeanUtils.copyProperties(param,stylist);
        stylist.setDeleteStatus(false);
        stylist.setId(shopId);
        save(stylist);
        iHmStylistDomainRelationService.updateRelations(stylist.getId(),param.getDomainIds());
        iHmStylistServiceRelationService.updateRelations(stylist.getId(),param.getServices());
        return stylist;
    }

    @Override
    @Transactional
    public HmStylist update(Long id, HmStylistParam param,Long shopId) {
        HmStylist stylist = getById(id);
        if (stylist == null || !stylist.getShopId().equals(shopId)){
            throw new ForbiddenException();
        }
        BeanUtils.copyProperties(param,stylist);
        stylist.setId(id);
        updateById(stylist);
        iHmStylistDomainRelationService.updateRelations(stylist.getId(),param.getDomainIds());
        iHmStylistServiceRelationService.updateRelations(stylist.getId(),param.getServices());
        return stylist;
    }

    @Override
    @Transactional
    public void delete(Long id, Long shopId) {
        HmStylist stylist = getById(id);
        if (stylist == null || !stylist.getShopId().equals(shopId)){
            throw new ForbiddenException();
        }
        removeById(id);
        //删除关联的擅长领域
        iHmStylistDomainRelationService.removeByStylistId(id);
        //删除关联的服务项目
        iHmStylistServiceRelationService.removeByStylistId(id);
        //删除关联的案例作品
        iHmWorkCaseService.removeByStylistId(id);
    }
}
