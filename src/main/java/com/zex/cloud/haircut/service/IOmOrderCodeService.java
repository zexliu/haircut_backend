package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.enums.OrderCodeType;

public interface IOmOrderCodeService {
    String getCode(Long id, OrderCodeType type, Long userId);

    String use(String code, Long shopId);

}
