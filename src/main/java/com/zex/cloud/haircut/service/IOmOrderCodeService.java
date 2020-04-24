package com.zex.cloud.haircut.service;

import com.google.zxing.WriterException;
import com.zex.cloud.haircut.enums.OrderCodeType;
import com.zex.cloud.haircut.vo.ShopCodeVO;

import java.io.IOException;

public interface IOmOrderCodeService {
    ShopCodeVO getCode(Long id, OrderCodeType type, Long userId) throws IOException, WriterException;

    String use(String code, Long shopId);

}
