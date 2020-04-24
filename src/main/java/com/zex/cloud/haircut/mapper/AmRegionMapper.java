package com.zex.cloud.haircut.mapper;

import com.zex.cloud.haircut.entity.AmRegion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zex.cloud.haircut.response.AmRegionTreeVO;
import com.zex.cloud.haircut.response.RegionDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Zex
 * @since 2020-02-19
 */
public interface AmRegionMapper extends BaseMapper<AmRegion> {

    RegionDetail detailByCode(@Param("adCode")String adCode);

    List<AmRegionTreeVO> home();
}
