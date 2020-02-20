package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.HmDomain;
import com.zex.cloud.haircut.entity.SmShopTitle;
import com.zex.cloud.haircut.params.BaseTitleParam;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.service.IHmDomainService;
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
@Api(tags = "擅长领域相关接口")
@RequestMapping("/api/v1/stylist/domains")
public class HmDomainController {

    @Autowired
    private IHmDomainService iHmDomainService;


    @GetMapping
    @ApiOperation("查询所有")
    public IPage<HmDomain> page(Pageable pageable){
        return iHmDomainService.page(pageable.convert(),
                new LambdaQueryWrapper<HmDomain>()
                        .orderByDesc(HmDomain::getSeq));
    }
    @GetMapping("/{id}")
    @ApiOperation("通过ID查询")
    public HmDomain getById(@PathVariable Long id){
        return iHmDomainService.getById(id);
    }
    @PostMapping
    @ApiOperation("新增")
    public HmDomain save(@RequestBody BaseTitleParam param){
        return iHmDomainService.save(param);
    }

    @PutMapping("/{id}")
    @ApiOperation("修改")
    public HmDomain update(@PathVariable Long id,@RequestBody BaseTitleParam param){
        return iHmDomainService.update(id,param);
    }



    @DeleteMapping("/{id}")
    @ApiOperation("删除")
    public String delete(@PathVariable Long id){
        iHmDomainService.delete(id);
        return SimpleResp.SUCCESS;
    }
}
