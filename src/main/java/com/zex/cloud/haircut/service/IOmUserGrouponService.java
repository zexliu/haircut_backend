package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.OmOrder;
import com.zex.cloud.haircut.entity.OmUserGroupon;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-23
 */
public interface IOmUserGrouponService extends IService<OmUserGroupon> {


    void onPayHook(OmOrder omOrder);

    BigDecimal refund(Long id, Long userId);

    void validUseStatus(Long id, Long userId);

    void use(Long id, Long shopId);

}
