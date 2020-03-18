package com.zex.cloud.haircut.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.UmUserHairHistory;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.params.UmUserHairHistoryParam;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.IUmUserHairHistoryService;
import com.zex.cloud.haircut.vo.UmUserHairHistoryDate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-03-16
 */
@RestController
@Api(tags = "用户发型历史")
@RequestMapping("/api/v1/hair/history")
public class UmUserHairHistoryController {


    @Autowired
    private IUmUserHairHistoryService iUmUserHairHistoryService;


    @GetMapping("/current")
    @ApiOperation("查询按日期分组")
    public IPage<UmUserHairHistoryDate> page (Pageable pageable){
        return iUmUserHairHistoryService.datePage(pageable.convert(),RequestHolder.user().getId());
    }

    @PostMapping
    @ApiOperation("新增")
    public UmUserHairHistory create(@RequestBody @Valid UmUserHairHistoryParam param){
        return iUmUserHairHistoryService.create(param, RequestHolder.user().getId());
    }


    @PutMapping("/{id}")
    @ApiOperation("修改")
    public UmUserHairHistory update(@PathVariable Long id , @RequestBody @Valid UmUserHairHistoryParam param){
        return iUmUserHairHistoryService.update(id,param, RequestHolder.user().getId());
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除")
    public String delete(@PathVariable Long id){
         iUmUserHairHistoryService.delete(id,RequestHolder.user().getId());
        return SimpleResp.SUCCESS;
    }
}
