package com.zex.cloud.haircut.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zex.cloud.haircut.entity.SmShop;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zex.cloud.haircut.enums.ShopWorkStatus;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Zex
 * @since 2020-02-19
 */
public interface SmShopMapper extends BaseMapper<SmShop> {

    IPage<SmShop> list(Page<SmShop> convert,
                       @Param("keywords") String keywords,
                       @Param("workStatus")ShopWorkStatus workStatus,
                       @Param("provinceCode")String provinceCode,
                       @Param("cityCode")String cityCode,
                       @Param("districtCode")String districtCode,
                       @Param("longitude")Double longitude,
                       @Param("latitude")Double latitude);

    Long getShopIdByUserId(@Param("userId") Long userId);

}
