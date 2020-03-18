package com.zex.cloud.haircut.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zex.cloud.haircut.entity.HmStylist;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.params.HmStylistParam;
import com.zex.cloud.haircut.vo.HmStylistCollectVO;
import com.zex.cloud.haircut.vo.HmStylistDetail;
import com.zex.cloud.haircut.vo.HmStylistDetailVO;
import com.zex.cloud.haircut.vo.HmStylistVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-20
 */
public interface IHmStylistService extends IService<HmStylist> {

    HmStylist save(HmStylistParam param, Long shopId);

    HmStylist update(Long id, HmStylistParam param, Long shopId);

    void delete(Long id, Long shopId);

    HmStylistDetail getDetailById(Long id);

    List<HmStylistVO> getStylistVoByShopId(Long id);

    HmStylistDetailVO detailVO(Long id, Double latitude, Double longitude, Long userId);

    IPage<HmStylistCollectVO> getCollectStylist(Page<HmStylistCollectVO> page, Long userId, Double latitude, Double longitude);

    IPage<HmStylistCollectVO> list(Page<HmStylistCollectVO> page, String keywords,Double latitude, Double longitude);
}
