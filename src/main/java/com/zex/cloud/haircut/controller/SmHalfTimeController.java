package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.SmHalfTime;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.params.SmHalfTimeParam;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.service.ISmHalfTimeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-02-21
 */
@RestController
@Api(tags = "半价时间")
@RequestMapping("/api/v1/half/time")
public class SmHalfTimeController {

    @Autowired
    private ISmHalfTimeService iSmHalfTimeService;

    @PostMapping
    @ApiOperation("新增")
    public SmHalfTime create(@RequestBody @Valid SmHalfTimeParam param) {
        return iSmHalfTimeService.create(param);
    }

    @PutMapping("/{id}")
    @ApiOperation("修改")
    public SmHalfTime create(@PathVariable Long id, @RequestBody @Valid SmHalfTimeParam param) {
        return iSmHalfTimeService.update(id, param);
    }

    @GetMapping
    public IPage<SmHalfTime> list(Pageable pageable) {
        return iSmHalfTimeService.page(pageable.convert(), new LambdaQueryWrapper<SmHalfTime>()
        .orderByAsc(SmHalfTime::getWeekDay,SmHalfTime::getStartAt));
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        iSmHalfTimeService.removeById(id);
        return SimpleResp.SUCCESS;
    }

    @GetMapping("/{id}")
    public SmHalfTime detail(@PathVariable Long id){
        return iSmHalfTimeService.getById(id);
    }
}
