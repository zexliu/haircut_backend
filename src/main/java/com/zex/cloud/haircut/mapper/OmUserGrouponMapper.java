package com.zex.cloud.haircut.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zex.cloud.haircut.dto.BrokenLinePoint;
import com.zex.cloud.haircut.entity.OmUserGroupon;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zex.cloud.haircut.enums.GenderType;
import com.zex.cloud.haircut.enums.UserGrouponStatus;
import com.zex.cloud.haircut.vo.OmUserGrouponDetailVO;
import com.zex.cloud.haircut.vo.OmUserGrouponVO;
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
public interface OmUserGrouponMapper extends BaseMapper<OmUserGroupon> {


    BigDecimal income(@Param("startAt")LocalDate startAt, @Param("endAt")LocalDate endAt,
                      @Param("provinceCode")Integer provinceCode, @Param("cityCode")Integer cityCode);

    List<BrokenLinePoint> brokenLinesAgent(@Param("startAt")LocalDate startAt, @Param("endAt")LocalDate endAt,
                                           @Param("provinceCode")Integer provinceCode, @Param("cityCode")Integer cityCode);

    IPage<OmUserGrouponVO> groupons(Page<OmUserGrouponVO> convert, @Param("keywords") String keywords,
                                    @Param("userId") Long userId,
                                    @Param("shopId") Long shopId,
                                    @Param("stylistId") Long stylistId,
                                    @Param("serviceId") Long serviceId,
                                    @Param("status") UserGrouponStatus status,
                                    @Param("genderType") GenderType genderType,
                                    @Param("startAt") LocalDateTime startAt,
                                    @Param("endAt") LocalDateTime endAt,
                                    @Param("provinceCode") Integer provinceCode,
                                    @Param("cityCode") Integer cityCode);

    OmUserGrouponDetailVO details(Long id);
}
