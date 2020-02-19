package com.zex.cloud.haircut.controller;


import com.zex.cloud.haircut.entity.SmShopApply;
import com.zex.cloud.haircut.exception.NotFoundException;
import com.zex.cloud.haircut.params.SmShopApplyParam;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.ISmShopApplyService;
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
 * @since 2020-02-19
 */
@RestController
@Api(tags = "店铺入驻申请相关接口")
@RequestMapping("/v1/shop/apply")
public class SmShopApplyController {


    @Autowired
    private ISmShopApplyService iSmShopApplyService;

    @GetMapping("/current")
    @ApiOperation("获取当前用户的申请记录")
    public SmShopApply current(){
        SmShopApply smShopApply = iSmShopApplyService.getApplyByUserId(RequestHolder.user().getId());
        if (smShopApply == null){
            throw new NotFoundException("当前用户没有申请记录");
        }
        return smShopApply;
    }


    @PostMapping
    @ApiOperation("创建申请")
    public SmShopApply create(@RequestBody SmShopApplyParam param){
       return iSmShopApplyService.create(param, RequestHolder.user().getId());
    }

    @PutMapping
    @ApiOperation("修改申请(驳回后重新申请)")
    public SmShopApply update(@RequestBody SmShopApplyParam param){
        return iSmShopApplyService.update(param, RequestHolder.user().getId());
    }
    //个人申请

    //驳回  ? am service

    //通过    am service

    //获取申请记录

    //获取单挑申请的详细信息


}
