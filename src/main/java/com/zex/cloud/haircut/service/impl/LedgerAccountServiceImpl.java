package com.zex.cloud.haircut.service.impl;

import com.zex.cloud.haircut.entity.*;
import com.zex.cloud.haircut.enums.*;
import com.zex.cloud.haircut.service.*;
import com.zex.cloud.haircut.util.DecimalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class LedgerAccountServiceImpl implements ILedgerAccountService {

    @Autowired
    private ISmBasicSettingService iSmBasicSettingService;

    @Autowired
    private ISmShopService iSmShopService;

    @Autowired
    private IOmShopTransactionService iOmShopTransactionService;


    @Autowired
    private IOmAgentTransactionService iOmAgentTransactionService;

    @Autowired
    private IOmUserTransactionService iOmUserTransactionService;

    @Autowired
    private IOmPlatformTransactionService iOmPlatformTransactionService;
    @Autowired
    private ISmAgentService iSmAgentService;

    @Autowired
    private IOmOrderService iOmOrderService;

    @Autowired
    private IUmPopularizeService iUmPopularizeService;

    @Override
    public void account(Long id, BigDecimal amount, Long userId, Long shopId, OrderType orderType) {
        //获取配置信息
        SmBasicSetting basicSetting = iSmBasicSettingService.current();
        //总佣金
        BigDecimal totalAmount = DecimalUtils.multiply(amount, basicSetting.getShopCommissionProportion());
        //店铺所得金额 = 总金额 - 店铺佣金
        SmShop shop = iSmShopService.getById(shopId);
        //判断店铺是否在免佣时间内
        BigDecimal shopAmount;
        if (shop.getCreateAt().plusMonths(basicSetting.getShopFreeMonth()).isBefore(LocalDateTime.now())) {
            BigDecimal platformAmount = totalAmount;

            shopAmount = DecimalUtils.subtract(amount, totalAmount);
            //查询店铺所在地是否有代理商
            SmAgent smAgent = iSmAgentService.getByLocation(shop.getProvinceCode(), shop.getCityCode());
            if (smAgent != null) {
                BigDecimal agentAmount = DecimalUtils.multiply(totalAmount, basicSetting.getAgentCommissionProportion());
                //代理商加钱
                OmAgentTransaction omAgentTransaction = new OmAgentTransaction();
                omAgentTransaction.setTargetId(id);
                omAgentTransaction.setIncrStatus(true);
                omAgentTransaction.setAmount(agentAmount);
                omAgentTransaction.setAgentId(smAgent.getId());
                omAgentTransaction.setType(orderType == OrderType.SHOP_SERVICE ? AgentTransactionType.SERVICE_INCOME : AgentTransactionType.GROUP_INCOME);
                omAgentTransaction.setCreateAt(LocalDateTime.now());
                iOmAgentTransactionService.save(omAgentTransaction);
                platformAmount = DecimalUtils.subtract(totalAmount, agentAmount);
            }

            //查询是否有推荐人
            UmPopularize popularize = iUmPopularizeService.getByUserId(userId);
            if (popularize != null && popularize.getTargetType() == PopularizeType.USER) {
                BigDecimal popularizeAmount = DecimalUtils.multiply(totalAmount, basicSetting.getUserCommissionProportion());
                OmUserTransaction omUserTransaction = new OmUserTransaction();
                omUserTransaction.setTargetId(id);
                omUserTransaction.setIncrStatus(true);
                omUserTransaction.setAmount(popularizeAmount);
                omUserTransaction.setUserId(popularize.getId());
                omUserTransaction.setType(UserTransactionType.POPULARIZE);
                omUserTransaction.setCreateAt(LocalDateTime.now());
                iOmUserTransactionService.save(omUserTransaction);
                platformAmount = DecimalUtils.subtract(totalAmount, popularizeAmount);

                //查询用户是否首次消费
                boolean isFirst = iOmOrderService.checkFirstPay(userId);
                if (isFirst) {
                    OmUserTransaction firstTransaction = new OmUserTransaction();
                    firstTransaction.setTargetId(id);
                    firstTransaction.setIncrStatus(true);
                    firstTransaction.setAmount(basicSetting.getUserFirstAmount());
                    firstTransaction.setUserId(popularize.getId());
                    firstTransaction.setType(UserTransactionType.POPULARIZE_FIRST);
                    firstTransaction.setCreateAt(LocalDateTime.now());
                    iOmUserTransactionService.save(firstTransaction);

                    //平台减钱
                    OmPlatformTransaction omPlatformTransaction = new OmPlatformTransaction();
                    omPlatformTransaction.setTargetId(id);
                    omPlatformTransaction.setIncrStatus(false);
                    omPlatformTransaction.setAmount(basicSetting.getUserFirstAmount());
                    omPlatformTransaction.setType(PlatformTransactionType.POPULARIZE_FIRST);
                    omPlatformTransaction.setCreateAt(LocalDateTime.now());
                    iOmPlatformTransactionService.save(omPlatformTransaction);
                }

            }
            //平台加钱
            OmPlatformTransaction omPlatformTransaction = new OmPlatformTransaction();
            omPlatformTransaction.setTargetId(id);
            omPlatformTransaction.setIncrStatus(true);
            omPlatformTransaction.setAmount(platformAmount);
            omPlatformTransaction.setType(orderType == OrderType.SHOP_SERVICE ? PlatformTransactionType.SERVICE_INCOME : PlatformTransactionType.GROUP_INCOME);
            omPlatformTransaction.setCreateAt(LocalDateTime.now());
            iOmPlatformTransactionService.save(omPlatformTransaction);

        } else {
            shopAmount = amount;
        }
        //店铺加钱
        OmShopTransaction omShopTransaction = new OmShopTransaction();
        omShopTransaction.setShopId(shopId);
        omShopTransaction.setAmount(shopAmount);
        omShopTransaction.setIncrStatus(true);
        omShopTransaction.setTargetId(id);
        omShopTransaction.setType(orderType == OrderType.SHOP_SERVICE ? ShopTransactionType.SERVICE_INCOME : ShopTransactionType.GROUP_INCOME);
        omShopTransaction.setCreateAt(LocalDateTime.now());
        iOmShopTransactionService.save(omShopTransaction);
    }
}
