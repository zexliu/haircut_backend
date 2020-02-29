package com.zex.cloud.haircut.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zex.cloud.haircut.dto.OrderGrouponDTO;
import com.zex.cloud.haircut.entity.OmOrder;
import com.zex.cloud.haircut.entity.OmShopOrder;
import com.zex.cloud.haircut.entity.OmUserGroupon;
import com.zex.cloud.haircut.enums.ShopOrderStatus;
import com.zex.cloud.haircut.enums.UserGrouponStatus;
import com.zex.cloud.haircut.exception.ForbiddenException;
import com.zex.cloud.haircut.exception.NotFoundException;
import com.zex.cloud.haircut.mapper.OmUserGrouponMapper;
import com.zex.cloud.haircut.service.IOmUserGrouponService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zex.cloud.haircut.util.DecimalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-02-23
 */
@Service
public class OmUserGrouponServiceImpl extends ServiceImpl<OmUserGrouponMapper, OmUserGroupon> implements IOmUserGrouponService {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onPayHook(OmOrder omOrder) {
        OrderGrouponDTO dto = objectMapper.convertValue(omOrder.getBody(), OrderGrouponDTO.class);
        OmUserGroupon omUserGroupon = new OmUserGroupon();
        //设置一年有效期
        omUserGroupon.setExpireAt(LocalDateTime.now().plusYears(1));
        omUserGroupon.setRemainCount(dto.getCount());
        omUserGroupon.setTotalCount(dto.getCount());
        omUserGroupon.setServiceId(dto.getServiceId());
        omUserGroupon.setStylistId(dto.getStylistId());
        omUserGroupon.setShopId(dto.getShopId());
        omUserGroupon.setSexType(dto.getSexType());
        omUserGroupon.setUserId(omOrder.getUserId());
        omUserGroupon.setAmount(omOrder.getAmount());
        omUserGroupon.setStatus(UserGrouponStatus.PENDING_USE);
        omUserGroupon.setOrderId(omOrder.getId());
        save(omUserGroupon);
    }

    @Override
    public BigDecimal refund(Long id, Long userId) {
        OmUserGroupon omUserGroupon = getById(id);
        if (omUserGroupon == null){
            throw new NotFoundException("团购信息不存在");
        }
        if (omUserGroupon.getStatus()!= UserGrouponStatus.PENDING_USE){
            throw new ForbiddenException("该订单不在待使用状态");
        }
        omUserGroupon.setStatus(UserGrouponStatus.REFUND);
        updateById(omUserGroupon);
        if (omUserGroupon.getRemainCount().equals(omUserGroupon.getTotalCount())){
            //没使用过 全额退款
            return omUserGroupon.getAmount();
        }else {
            //剩余金额 等于 总金额 / 总数量 * 剩余数量
            BigDecimal remainAmount = DecimalUtils.multiply(DecimalUtils.divide(omUserGroupon.getAmount(),new BigDecimal(String.valueOf(omUserGroupon.getTotalCount())))
                    ,new BigDecimal(String.valueOf(omUserGroupon.getRemainCount())));
            //退款金额等于剩余金额 扣除 5%
            return DecimalUtils.multiply(remainAmount,new BigDecimal("0.5"));
        }
    }

    @Override
    public void validUseStatus(Long id, Long userId) {
        OmUserGroupon userGroupon = getById(id);
        if (userGroupon == null){
            throw new NotFoundException();
        }
        if (userGroupon.getStatus() != UserGrouponStatus.PENDING_USE){
            throw new ForbiddenException("订单不在可用状态");
        }
        if (!userGroupon.getUserId().equals(userId)){
            throw new ForbiddenException();
        }
    }

    @Override
    public void use(Long id, Long shopId) {
        OmUserGroupon userGroupon = getById(id);
        if (userGroupon == null){
            throw new NotFoundException();
        }
        if (userGroupon.getStatus() != UserGrouponStatus.PENDING_USE){
            throw new ForbiddenException("订单不在可用状态");
        }
        if (!userGroupon.getShopId().equals(shopId)){
            throw new ForbiddenException();
        }
        userGroupon.setRemainCount(userGroupon.getRemainCount() - 1);
        if (userGroupon.getRemainCount() == 0){
            //设置状态为已用完
            userGroupon.setStatus(UserGrouponStatus.USED);
        }
        // TODO: 2020/2/27 结算店铺金额
        updateById(userGroupon);

    }
}
