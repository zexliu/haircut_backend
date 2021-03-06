package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.HmJobTitle;
import com.zex.cloud.haircut.entity.HmJobTitle;
import com.zex.cloud.haircut.params.BaseTitleParam;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.service.IHmJobTitleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-02-20
 */
@RestController
@Api(tags = "发型师职称相关接口")
@RequestMapping("/api/v1/stylists/titles")
public class HmJobTitleController {

    @Autowired
    private IHmJobTitleService iHmJobTitleService;

    @GetMapping
    @ApiOperation("查询所有")
    public IPage<HmJobTitle> page(Pageable pageable){
        return iHmJobTitleService.page(pageable.convert(),
                new LambdaQueryWrapper<HmJobTitle>()
                        .orderByDesc(HmJobTitle::getSeq));
    }
    @GetMapping("/{id}")
    @ApiOperation("通过ID查询")
    public HmJobTitle getById(@PathVariable Long id){
        return iHmJobTitleService.getById(id);
    }

    @PostMapping
    @ApiOperation("新增")
    public HmJobTitle save(@RequestBody BaseTitleParam param){
        return iHmJobTitleService.save(param);
    }

    @PutMapping("/{id}")
    @ApiOperation("修改")
    public HmJobTitle update(@PathVariable Long id,@RequestBody BaseTitleParam param){
        return iHmJobTitleService.update(id,param);
    }
    @DeleteMapping("/{id}")
    @ApiOperation("删除")
    public String delete(@PathVariable Long id){
        iHmJobTitleService.delete(id);
        return SimpleResp.SUCCESS;
    }
}
