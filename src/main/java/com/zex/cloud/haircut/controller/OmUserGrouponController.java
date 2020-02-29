package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.OmShopOrder;
import com.zex.cloud.haircut.entity.OmUserGroupon;
import com.zex.cloud.haircut.enums.SexType;
import com.zex.cloud.haircut.enums.UserGrouponStatus;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.service.IOmUserGrouponService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    public IPage<OmUserGroupon> groupons(Pageable pageable, String keywords, Long userId,
                                         Long shopId, Long stylistId, Long serviceId,
                                         UserGrouponStatus status, SexType sexType, LocalDateTime startAt, LocalDateTime endAt) {
        return iOmUserGrouponService.page(pageable.convert(), new LambdaQueryWrapper<OmUserGroupon>()
                .eq(userId != null, OmUserGroupon::getUserId, userId)
                .eq(shopId != null, OmUserGroupon::getShopId, shopId)
                .eq(stylistId != null, OmUserGroupon::getStylistId, stylistId)
                .eq(serviceId != null, OmUserGroupon::getServiceId, serviceId)
                .eq(status != null, OmUserGroupon::getStatus, status)
                .ge(startAt != null, OmUserGroupon::getCreateAt, startAt)
                .le(endAt != null, OmUserGroupon::getCreateAt, endAt)
                .eq(sexType != null, OmUserGroupon::getSexType, sexType)
                .and(StringUtils.isNotBlank(keywords), i ->
                        i.like(OmUserGroupon::getOrderId, keywords)
                                .or()
                                .like(OmUserGroupon::getId, keywords))
                .orderByDesc(OmUserGroupon::getCreateAt));

    }

}
