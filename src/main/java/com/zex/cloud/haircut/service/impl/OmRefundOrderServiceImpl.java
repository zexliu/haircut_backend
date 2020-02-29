package com.zex.cloud.haircut.service.impl;

import com.zex.cloud.haircut.entity.OmRefundOrder;
import com.zex.cloud.haircut.enums.PayChannelType;
import com.zex.cloud.haircut.exception.ForbiddenException;
import com.zex.cloud.haircut.exception.NotFoundException;
import com.zex.cloud.haircut.mapper.OmRefundOrderMapper;
import com.zex.cloud.haircut.service.IOmRefundOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zex.cloud.haircut.util.DecimalUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-02-23
 */
@Service
public class OmRefundOrderServiceImpl extends ServiceImpl<OmRefundOrderMapper, OmRefundOrder> implements IOmRefundOrderService {

    @Override
    public Long create(Long id, Long userId, BigDecimal amount, String description, PayChannelType channelType) {
        OmRefundOrder omRefundOrder = new OmRefundOrder();
        omRefundOrder.setAmount(amount);
        omRefundOrder.setOrderId(id);
        omRefundOrder.setChannelType(channelType);
        omRefundOrder.setDescription(description);
        omRefundOrder.setStatus(false);
        save(omRefundOrder);
        return omRefundOrder.getId();
    }

    @Override
    public void onRefundHook(Long refundId, BigDecimal amount, String tradeNo) {
        OmRefundOrder omRefundOrder = getById(refundId);
        if (omRefundOrder == null){
            throw new NotFoundException();
        }
        if (DecimalUtils.ne(omRefundOrder.getAmount(),amount)){
            throw  new ForbiddenException("退款金额不符");
        }
        omRefundOrder.setStatus(true);
        omRefundOrder.setThirdPartyId(tradeNo);
        updateById(omRefundOrder);
    }
}
