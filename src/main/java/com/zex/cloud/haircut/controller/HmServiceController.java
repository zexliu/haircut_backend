package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.HmService;
import com.zex.cloud.haircut.entity.HmService;
import com.zex.cloud.haircut.params.BaseTitleParam;
import com.zex.cloud.haircut.params.HmServiceParam;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.service.IHmServiceService;
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
@RequestMapping("/api/v1/service/projects")
@Api(tags = "服务项目操作接口")
public class HmServiceController {

    @Autowired
    private IHmServiceService iHmServiceService;

  
    @GetMapping
    @ApiOperation("查询所有")
    public IPage<HmService> page(Pageable pageable){
        return iHmServiceService.page(pageable.convert(),
                new LambdaQueryWrapper<HmService>()
                        .orderByDesc(HmService::getSeq));
    }
    @GetMapping("/{id}")
    @ApiOperation("通过ID查询")
    public HmService getById(@PathVariable Long id){
        return iHmServiceService.getById(id);
    }
    
    @PostMapping
    @ApiOperation("新增")
    public HmService save(@RequestBody HmServiceParam param){
        return iHmServiceService.save(param);
    }

    @PutMapping("/{id}")
    @ApiOperation("修改")
    public HmService update(@PathVariable Long id,@RequestBody HmServiceParam param){
        return iHmServiceService.update(id,param);
    }
    @DeleteMapping("/{id}")
    @ApiOperation("删除")
    public String delete(@PathVariable Long id){
        iHmServiceService.removeById(id);
        return SimpleResp.SUCCESS;
    }

    
}
