package com.zex.cloud.haircut.controller;


import com.zex.cloud.haircut.enums.CollectType;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.IUmUserCollectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-03-02
 */
@RestController
@Api(tags = "用户收藏")
@RequestMapping("/api/v1/collect")
public class UmUserCollectController {

    @Autowired
    private IUmUserCollectService iUmUserCollectService;

    @PostMapping("/{id}")
    @ApiOperation("收藏")
    public String collect(@PathVariable Long id , @RequestParam CollectType collectType){
         iUmUserCollectService.collect(id,collectType, RequestHolder.user().getId());
        return SimpleResp.SUCCESS;
    }

    @DeleteMapping("/{id}")
    @ApiOperation("取消收藏")
    public String delete(@PathVariable Long id , @RequestParam CollectType collectType){
        iUmUserCollectService.delete(id,collectType, RequestHolder.user().getId());
        return SimpleResp.SUCCESS;
    }

}
