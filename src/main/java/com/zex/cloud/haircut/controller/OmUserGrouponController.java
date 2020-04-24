package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.OmUserGroupon;
import com.zex.cloud.haircut.enums.ClientType;
import com.zex.cloud.haircut.enums.GenderType;
import com.zex.cloud.haircut.enums.UserGrouponStatus;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.IOmUserGrouponService;
import com.zex.cloud.haircut.vo.OmUserGrouponDetailVO;
import com.zex.cloud.haircut.vo.OmUserGrouponVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-02-23
 */
@RestController
@RequestMapping("/api/v1/user/groupons")
@Api(tags = "用户团购信息相关")
public class OmUserGrouponController {

    @Autowired
    IOmUserGrouponService iOmUserGrouponService;

    @GetMapping
    public IPage<OmUserGrouponVO> groupons(Pageable pageable, String keywords, Long userId,
                                           Long shopId, Long stylistId, Long serviceId,
                                           UserGrouponStatus status, GenderType genderType, LocalDateTime startAt, LocalDateTime endAt, Integer provinceCode, Integer cityCode) {
        return iOmUserGrouponService.groupons(pageable.convert(), keywords, userId, shopId, stylistId, serviceId, status, genderType, startAt, endAt, provinceCode, cityCode);
//        return iOmUserGrouponService.page(pageable.convert(), new LambdaQueryWrapper<OmUserGroupon>()
//                .eq(userId != null, OmUserGroupon::getUserId, userId)
//                .eq(shopId != null, OmUserGroupon::getShopId, shopId)
//                .eq(stylistId != null, OmUserGroupon::getStylistId, stylistId)
//                .eq(serviceId != null, OmUserGroupon::getServiceId, serviceId)
//                .eq(status != null, OmUserGroupon::getStatus, status)
//                .ge(startAt != null, OmUserGroupon::getCreateAt, startAt)
//                .le(endAt != null, OmUserGroupon::getCreateAt, endAt)
//                .eq(genderType != null, OmUserGroupon::getGenderType, genderType)
//                .and(StringUtils.isNotBlank(keywords), i ->
//                        i.like(OmUserGroupon::getOrderId, keywords)
//                                .or()
//                                .like(OmUserGroupon::getId, keywords))
//                .orderByDesc(OmUserGroupon::getCreateAt));

    }


    @ApiOperation("查询当前用户团购信息")
    @GetMapping("/current")
    public IPage<OmUserGrouponVO> current(Pageable pageable, String keywords,
                                          Long shopId,Long userId, Long stylistId, Long serviceId,
                                          UserGrouponStatus status, GenderType genderType, LocalDateTime startAt, LocalDateTime endAt) {
        if (RequestHolder.user().getClientType() == ClientType.SHOP_CLIENT){
            return groupons(pageable, keywords, userId, RequestHolder.user().getShopId(), stylistId, serviceId, status, genderType, startAt, endAt, null, null);

        }else {
            return groupons(pageable, keywords, RequestHolder.user().getId(), shopId, stylistId, serviceId, status, genderType, startAt, endAt, null, null);

        }

    }

    @ApiOperation("查询团购订单详情")
    @GetMapping("/{id}")
    public OmUserGrouponDetailVO groupons(@PathVariable Long id) {
        return iOmUserGrouponService.detail(id);
    }


}
