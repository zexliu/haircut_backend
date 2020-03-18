package com.zex.cloud.haircut.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zex.cloud.haircut.entity.HmStylist;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zex.cloud.haircut.vo.HmStylistCollectVO;
import com.zex.cloud.haircut.vo.HmStylistDetail;
import com.zex.cloud.haircut.vo.HmStylistDetailVO;
import com.zex.cloud.haircut.vo.HmStylistVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Zex
 * @since 2020-02-20
 */
public interface HmStylistMapper extends BaseMapper<HmStylist> {

    List<HmStylistVO> getHmStylistVOsBySHopId(@Param("shopId") Long shopId, @Param("serviceId")Long serviceId);

    HmStylistDetailVO getDetail(@Param("id") Long id, @Param("latitude") Double latitude, @Param("longitude")Double longitude,@Param("userId") Long userId);

    IPage<HmStylistCollectVO> getCollectStylists(Page<HmStylistCollectVO> page,@Param("userId")  Long userId , @Param("serviceId")Long serviceId ,@Param("latitude") Double latitude, @Param("longitude") Double longitude);


    IPage<HmStylistCollectVO> list(Page<HmStylistCollectVO> page,@Param("keywords") String keywords,@Param("serviceId") Long serviceId, @Param("latitude")Double latitude,@Param("longitude") Double longitude);
}
