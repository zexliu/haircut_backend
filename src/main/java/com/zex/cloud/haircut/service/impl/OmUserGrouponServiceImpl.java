package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zex.cloud.haircut.dto.BrokenLinePoint;
import com.zex.cloud.haircut.dto.OrderGrouponDTO;
import com.zex.cloud.haircut.entity.OmOrder;
import com.zex.cloud.haircut.entity.OmUserGroupon;
import com.zex.cloud.haircut.enums.GenderType;
import com.zex.cloud.haircut.enums.OrderType;
import com.zex.cloud.haircut.enums.UserGrouponStatus;
import com.zex.cloud.haircut.exception.ForbiddenException;
import com.zex.cloud.haircut.exception.NotFoundException;
import com.zex.cloud.haircut.mapper.OmUserGrouponMapper;
import com.zex.cloud.haircut.service.ILedgerAccountService;
import com.zex.cloud.haircut.service.IOmUserGrouponService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zex.cloud.haircut.util.DecimalUtils;
import com.zex.cloud.haircut.vo.OmUserGrouponDetailVO;
import com.zex.cloud.haircut.vo.OmUserGrouponVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    @Autowired
    private ILedgerAccountService iLedgerAccountService;

    @Override
    @Transactional
    public void onPayHook(OmOrder omOrder) {
        try {
            OrderGrouponDTO dto = objectMapper.readValue(omOrder.getBody(), OrderGrouponDTO.class);
            OmUserGroupon omUserGroupon = new OmUserGroupon();
            //设置一年有效期
            omUserGroupon.setExpireAt(LocalDateTime.now().plusYears(1));
            omUserGroupon.setRemainCount(dto.getCount());
            omUserGroupon.setTotalCount(dto.getCount());
            omUserGroupon.setServiceId(dto.getServiceId());
            omUserGroupon.setStylistId(dto.getStylistId());
            omUserGroupon.setShopId(dto.getShopId());
            omUserGroupon.setGenderType(dto.getGenderType());
            omUserGroupon.setUserId(omOrder.getUserId());
            omUserGroupon.setAmount(omOrder.getAmount());
            omUserGroupon.setStatus(UserGrouponStatus.PENDING_USE);
            omUserGroupon.setOrderId(omOrder.getId());
            omUserGroupon.setPrice(dto.getPrice());
            save(omUserGroupon);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public BigDecimal refund(Long id, Long userId) {
        OmUserGroupon omUserGroupon = getByOrderId(id);
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
            //退款金额等于剩余金额 扣除 5%
            BigDecimal subtract = DecimalUtils.subtract(omUserGroupon.getAmount(), DecimalUtils.multiply(omUserGroupon.getPrice(), new BigDecimal(String.valueOf(omUserGroupon.getRemainCount()))));
            if (DecimalUtils.le(omUserGroupon.getAmount(),subtract)){
                throw new ForbiddenException("该团购订单已无可退金额");
            }
            return subtract;

//            return DecimalUtils.multiply(omUserGroupon.getPrice(),new BigDecimal(String.valueOf(omUserGroupon.getRemainCount())));
//            return DecimalUtils.multiply(DecimalUtils.divide(omUserGroupon.getAmount(),new BigDecimal(String.valueOf(omUserGroupon.getTotalCount())))
//                    ,new BigDecimal(String.valueOf(omUserGroupon.getRemainCount())));
        }
    }

    private OmUserGroupon getByOrderId(Long id) {
        return getOne(new LambdaQueryWrapper<OmUserGroupon>().eq(OmUserGroupon::getOrderId,id));
    }

    @Override
    public Long validUseStatus(Long id, Long userId) {
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
        return userGroupon.getShopId();
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

        iLedgerAccountService.account(userGroupon.getId(),DecimalUtils.divide(userGroupon.getAmount(),new BigDecimal(String.valueOf(userGroupon.getTotalCount()))), userGroupon.getUserId(), userGroupon.getShopId(), OrderType.SHOP_GROUPON);

        updateById(userGroupon);

    }

    @Override
    public BigDecimal income(LocalDate startAt, LocalDate endAt, Integer provinceCode, Integer cityCode) {
        BigDecimal income = baseMapper.income(startAt,endAt,provinceCode,cityCode);
        if (income == null){
            return new BigDecimal("0");
        }
        return income;
    }

    @Override
    public List<BrokenLinePoint> brokenLinesAgent(LocalDate startAt, LocalDate endAt, Integer provinceCode, Integer cityCode) {
        return baseMapper.brokenLinesAgent(startAt,endAt,provinceCode,cityCode);
    }

    @Override
    public IPage<OmUserGrouponVO> groupons(Page<OmUserGrouponVO> convert, String keywords, Long userId, Long shopId, Long stylistId, Long serviceId, UserGrouponStatus status, GenderType genderType, LocalDateTime startAt, LocalDateTime endAt, Integer provinceCode, Integer cityCode) {
        return baseMapper.groupons(convert,keywords,userId,shopId,stylistId,serviceId,status,genderType,startAt,endAt,provinceCode,cityCode);
    }

    @Override
    public OmUserGrouponDetailVO detail(Long id) {
        return baseMapper.details(id);
    }

}
