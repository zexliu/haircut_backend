package com.zex.cloud.haircut.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.OmShopOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zex.cloud.haircut.enums.GenderType;
import com.zex.cloud.haircut.enums.ShopOrderStatus;
import com.zex.cloud.haircut.vo.OmShopOrderVO;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Zex
 * @since 2020-02-23
 */
public interface OmShopOrderMapper extends BaseMapper<OmShopOrder> {

    IPage<OmShopOrderVO> shopOrderVO(IPage<OmShopOrderVO> page, @Param("keywords") String keywords, @Param("shopId") Long shopId, @Param("stylistId") Long stylistId, @Param("userId") Long userId, @Param("status") ShopOrderStatus status, @Param("genderType") GenderType genderType, @Param("startAt") LocalDateTime startAt, @Param("endAt") LocalDateTime endAt, @Param("useStatus") Boolean useStatus, @Param("payStatus") Boolean payStatus);

}
