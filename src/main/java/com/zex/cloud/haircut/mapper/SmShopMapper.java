package com.zex.cloud.haircut.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zex.cloud.haircut.dto.BrokenLinePoint;
import com.zex.cloud.haircut.entity.SmShop;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zex.cloud.haircut.enums.ShopWorkStatus;
import com.zex.cloud.haircut.vo.SmHomeShopVO;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

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

    List<BrokenLinePoint> brokenLines(@Param("startAt") LocalDate startAt,@Param("endAt")  LocalDate endAt);

    int count(@Param("startAt")LocalDate startAt, @Param("endAt")LocalDate endAt);


    IPage<SmHomeShopVO> homeVo(Page<SmHomeShopVO> page,
                               @Param("keywords") String keywords,
                               @Param("workStatus")ShopWorkStatus workStatus,
                               @Param("provinceCode")String provinceCode,
                               @Param("cityCode")String cityCode,
                               @Param("districtCode")String districtCode,
                               @Param("longitude")Double longitude,
                               @Param("latitude")Double latitude);


    IPage<SmHomeShopVO> getCollectShops(Page<SmHomeShopVO> page,
                                        @Param("userId") Long userId,
                                        @Param("latitude") Double latitude,
                                        @Param("longitude") Double longitude);
}
