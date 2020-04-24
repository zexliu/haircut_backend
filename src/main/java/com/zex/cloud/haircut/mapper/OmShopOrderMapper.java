package com.zex.cloud.haircut.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.dto.BrokenLinePoint;
import com.zex.cloud.haircut.entity.OmShopOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zex.cloud.haircut.enums.GenderType;
import com.zex.cloud.haircut.enums.ShopOrderStatus;
import com.zex.cloud.haircut.vo.OmShopOrderDetailVO;
import com.zex.cloud.haircut.vo.OmShopOrderVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Zex
 * @since 2020-02-23
 */
public interface OmShopOrderMapper extends BaseMapper<OmShopOrder> {

    IPage<OmShopOrderVO> shopOrderVO(IPage<OmShopOrderVO> page, @Param("keywords") String keywords, @Param("shopId") Long shopId, @Param("stylistId") Long stylistId, @Param("userId") Long userId, @Param("status") ShopOrderStatus status, @Param("genderType") GenderType genderType, @Param("startAt") LocalDateTime startAt, @Param("endAt") LocalDateTime endAt, @Param("useStatus") Boolean useStatus, @Param("payStatus") Boolean payStatus, @Param("isToday") Boolean isToday, @Param("isAfterToday") Boolean isAfterToday,  @Param("provinceCode") Integer provinceCode, @Param("cityCode")  Integer cityCode);

    BigDecimal income(@Param("startAt")LocalDate startAt, @Param("endAt")LocalDate endAt,
                      @Param("provinceCode")Integer provinceCode, @Param("cityCode")Integer cityCode);

    List<BrokenLinePoint> brokenLinesAgent(@Param("startAt")LocalDate startAt, @Param("endAt")LocalDate endAt,
                                           @Param("provinceCode")Integer provinceCode, @Param("cityCode")Integer cityCode);


    OmShopOrderDetailVO getDetailById(@Param("id")Long id);



}
