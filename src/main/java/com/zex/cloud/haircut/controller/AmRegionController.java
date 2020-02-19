package com.zex.cloud.haircut.controller;


import com.zex.cloud.haircut.entity.AmRegion;
import com.zex.cloud.haircut.response.AmRegionTree;
import com.zex.cloud.haircut.response.RegionDetail;
import com.zex.cloud.haircut.service.IAmRegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-02-19
 */
@RestController
@RequestMapping("/api/v1/regions")
@Api(tags = "地区管理")

public class AmRegionController {
    @Autowired
    private IAmRegionService iAmRegionService;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @GetMapping("/{id}")
    @ApiOperation("通过ID获取地区信息")
    public AmRegion region(@PathVariable Long id) {
        return iAmRegionService.getById(id);
    }


    @ApiOperation("获取地区列表(省市区街道)")
    @GetMapping
    public List<AmRegion> regions(@ApiParam(value = "父级ID")@RequestParam(required = false) Integer parentId, @ApiParam(value = "最大数据级别 (如只要省市数据 传 2 省市区 传3 所有传null) ") @RequestParam(required = false)Integer level) {
        return iAmRegionService.list(parentId,level);
    }

    @ApiOperation("获取省市区街道树形结构")
    @GetMapping("/tree")
    public List<AmRegionTree> tree(@ApiParam(value = "最大数据级别 (如只要省市数据 传 2 省市区 传3 所有传null) ") @RequestParam(required = false) Integer level) {
        Object o = redisTemplate.opsForValue().get("REGION_TREE_" + level);
        if (o == null){
            List<AmRegionTree> tree = iAmRegionService.tree(level);
            redisTemplate.opsForValue().set("REGION_TREE_"+level,tree, 1,TimeUnit.DAYS);
            return tree;
        }
        return (List<AmRegionTree>) o;
    }

    @ApiOperation("获取省市区街道编号")
    @GetMapping("/code/{adCode}")
    public RegionDetail getCode(@PathVariable String adCode) {
        return iAmRegionService.detailByCode(adCode);
    }


}
