package com.zex.cloud.haircut.service.impl;

import com.zex.cloud.haircut.dto.OrderCode;
import com.zex.cloud.haircut.enums.OrderCodeType;
import com.zex.cloud.haircut.exception.NotFoundException;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.service.IOmOrderCodeService;
import com.zex.cloud.haircut.service.IOmShopOrderService;
import com.zex.cloud.haircut.service.IOmUserGrouponService;
import com.zex.cloud.haircut.util.RedisKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class OmOrderCodeServiceImpl implements IOmOrderCodeService {
    //获取核销码

    @Autowired
    private IOmShopOrderService iOmShopOrderService;

    @Autowired
    private IOmUserGrouponService iOmUserGrouponService;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Override
    public String getCode(Long id, OrderCodeType type, Long userId) {
        if (type == OrderCodeType.SERVICE){
            iOmShopOrderService.validUseStatus(id,userId);
        }else if (type == OrderCodeType.GROUPON){
            iOmUserGrouponService.validUseStatus(id,userId);
        }
        String uuid = UUID.randomUUID().toString();
        OrderCode orderCode = new OrderCode();
        orderCode.setType(type);
        orderCode.setId(id);
        redisTemplate.opsForValue().set(RedisKeys.orderCodeKey(uuid),orderCode,1, TimeUnit.DAYS);
        return uuid;
    }

    @Override
    public String use(String code, Long shopId) {
        OrderCode orderCode = (OrderCode) redisTemplate.opsForValue().get(RedisKeys.orderCodeKey(code));
        if (orderCode == null){
            throw new NotFoundException("核销码不存在,或已过期");
        }
        if (orderCode.getType() == OrderCodeType.SERVICE){
            iOmShopOrderService.use(orderCode.getId(),shopId);
        }else if (orderCode.getType() == OrderCodeType.GROUPON){
            iOmUserGrouponService.use(orderCode.getId(),shopId);
        }
        return SimpleResp.SUCCESS;
    }


}
