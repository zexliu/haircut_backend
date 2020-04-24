package com.zex.cloud.haircut.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zex.cloud.haircut.dto.BrokenLinePoint;
import com.zex.cloud.haircut.vo.ShopDetailVO;
import com.zex.cloud.haircut.vo.SmHomeShopVO;
import com.zex.cloud.haircut.vo.SmShopVO;
import com.zex.cloud.haircut.entity.SmShop;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.enums.ShopWorkStatus;
import com.zex.cloud.haircut.params.SmShopCurrentParam;
import com.zex.cloud.haircut.params.SmShopParam;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-19
 */
public interface ISmShopService extends IService<SmShop> {

   IPage<SmShop> list(Page<SmShop> convert, String keywords, ShopWorkStatus workStatus, String provinceCode, String cityCode, String districtCode, Double longitude, Double latitude);

    SmShop customSave(SmShop smShop);

    SmShop updateCurrent(Long id, SmShopCurrentParam param);

    SmShop update(Long id, SmShopParam param);

    SmShop currentWorkStatus(Long id, ShopWorkStatus workStatus);


  void updateTitle(Long id, List<Long> titleIds);

//    void updateHalfTime(Long shopId, List<SmHalfTimeParam> params);


    Long getShopIdByUserId(Long id);

    List<BrokenLinePoint> brokenLines(LocalDate startAt, LocalDate endAt, Integer provinceCode, Integer cityCode);

    int count(LocalDate startAt, LocalDate endAt, Integer provinceCode, Integer cityCode);

    SmShopVO getByShopId(Long shopId);

    IPage<SmHomeShopVO> homeVo(Page<SmHomeShopVO> convert, String keywords, ShopWorkStatus workStatus, String provinceCode, String cityCode, String districtCode, Double longitude, Double latitude);

    ShopDetailVO detail(Long id, Long aLong);

    void updateHalfStatus(Long shopId, Boolean halfStatus);

    IPage<SmHomeShopVO> getCollectShops(Page<SmHomeShopVO> page, Long userId, Double latitude, Double longitude);
}
