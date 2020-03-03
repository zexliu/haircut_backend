package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.UmPopularize;
import com.zex.cloud.haircut.enums.PopularizeStatus;
import com.zex.cloud.haircut.enums.PopularizeType;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.response.UmPopularizeUser;
import com.zex.cloud.haircut.service.IUmPopularizeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-03-03
 */
@RestController
@Api(tags = "拉新相关")
@RequestMapping("/api/v1/popularizes")
public class UmPopularizeController {


    @Autowired
    private IUmPopularizeService iUmPopularizeService;
    @ApiOperation("获取拉新分页")
    @GetMapping
    public IPage<UmPopularizeUser> page(Pageable pageable, Long targetId, PopularizeType popularizeType , PopularizeStatus popularizeStatus , LocalDateTime startAt, LocalDateTime endAt){
        return iUmPopularizeService.page(pageable.convert(),targetId,popularizeType,popularizeStatus,startAt,endAt);

    }


}
