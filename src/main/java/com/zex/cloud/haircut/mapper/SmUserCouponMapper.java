package com.zex.cloud.haircut.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.SmUserCoupon;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zex.cloud.haircut.response.SmUserCouponDetail;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Zex
 * @since 2020-02-21
 */
public interface SmUserCouponMapper extends BaseMapper<SmUserCoupon> {

    IPage<SmUserCouponDetail> list(@Param("userId") Long userId,
                                   @Param("shopId")Long shopId,
                                   @Param("useStatus")Boolean useStatus,
                                   @Param("dateStatus")Boolean dateStatus);

}
