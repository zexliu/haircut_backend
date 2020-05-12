package com.zex.cloud.haircut.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.zex.cloud.haircut.dto.BrokenLinePoint;
import com.zex.cloud.haircut.entity.OmShopOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.enums.GenderType;
import com.zex.cloud.haircut.enums.ShopOrderStatus;
import com.zex.cloud.haircut.params.OmOrderParam;
import com.zex.cloud.haircut.vo.OmShopOrderDetailVO;
import com.zex.cloud.haircut.vo.OmShopOrderVO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-23
 */
public interface IOmShopOrderService extends IService<OmShopOrder> {

    String validOrderAndCreate(OmOrderParam param, Long userId, Long orderId) throws JsonProcessingException;

    void cancelOrder(Long id);

    void onPayHook(Long orderId);

    void refund(Long id, Long userId);

    Long validUseStatus(Long id, Long userId);

    void use(Long id, Long shopId);

    OmShopOrder comment(Long shopOrderId, Long userId, Boolean auto);

    IPage<OmShopOrderVO> shopOrderVO(IPage<OmShopOrderVO> page, String keywords, Long shopId, Long stylistId, Long userId, ShopOrderStatus status, GenderType genderType, LocalDateTime startAt, LocalDateTime endAt, Boolean useStatus, Boolean payStatus, Boolean isToday, Boolean isAfterToday, Integer provinceCode, Integer cityCode);

    int getWaitCount(Long shopId, Long stylistId, LocalDateTime now);

    int stylistOrderCount(Long id);

    BigDecimal income(LocalDate startAt, LocalDate endAt, Integer provinceCode, Integer cityCode);

    List<BrokenLinePoint> brokenLinesAgent(LocalDate startAt, LocalDate endAt, Integer provinceCode, Integer cityCode);


    OmShopOrderDetailVO getDetailById(Long id);


}
