package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.AmRegion;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.response.AmRegionTree;
import com.zex.cloud.haircut.response.AmRegionTreeVO;
import com.zex.cloud.haircut.response.RegionDetail;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-19
 */
public interface IAmRegionService extends IService<AmRegion> {

    List<AmRegion> list(Integer parentId, Integer level);

    List<AmRegionTree> tree(Integer level);

    RegionDetail detailByCode(String adCode);

    List<AmRegionTreeVO> home();

}
