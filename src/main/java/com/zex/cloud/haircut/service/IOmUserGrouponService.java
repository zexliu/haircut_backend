package com.zex.cloud.haircut.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zex.cloud.haircut.dto.BrokenLinePoint;
import com.zex.cloud.haircut.entity.OmOrder;
import com.zex.cloud.haircut.entity.OmUserGroupon;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.enums.GenderType;
import com.zex.cloud.haircut.enums.UserGrouponStatus;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.vo.OmUserGrouponDetailVO;
import com.zex.cloud.haircut.vo.OmUserGrouponVO;

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
public interface IOmUserGrouponService extends IService<OmUserGroupon> {


    void onPayHook(OmOrder omOrder);

    BigDecimal refund(Long id, Long userId);

    Long validUseStatus(Long id, Long userId);

    void use(Long id, Long shopId);

    BigDecimal income(LocalDate startAt, LocalDate endAt, Integer provinceCode, Integer cityCode);

    List<BrokenLinePoint> brokenLinesAgent(LocalDate startAt, LocalDate endAt, Integer provinceCode, Integer cityCode);


    IPage<OmUserGrouponVO> groupons(Page<OmUserGrouponVO> convert, String keywords, Long userId, Long shopId, Long stylistId, Long serviceId, UserGrouponStatus status, GenderType genderType, LocalDateTime startAt, LocalDateTime endAt, Integer provinceCode, Integer cityCode);

    OmUserGrouponDetailVO detail(Long id);
}
