package com.zex.cloud.haircut.controller;


import com.zex.cloud.haircut.entity.SmBasicSetting;
import com.zex.cloud.haircut.params.SmBasicSettingParam;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.ISmBasicSettingService;
import com.zex.cloud.haircut.util.NetWorkUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-03-01
 */
@RestController
@Api(tags = "基础设置")
@RequestMapping("/api/v1/setting/basic")
public class SmBasicSettingController {

    @Autowired
    private ISmBasicSettingService iSmBasicSettingService;

    @GetMapping
    public SmBasicSetting current(){
        return iSmBasicSettingService.current();
    }

    @PostMapping
    public SmBasicSetting create(@RequestBody @Valid SmBasicSettingParam param, HttpServletRequest request){
        return iSmBasicSettingService.save(param, RequestHolder.user().getId(), NetWorkUtils.getRemoteHost(request));
    }
//
//    @PutMapping("/{id}")
//    public SmBasicSetting create(@PathVariable Long id ,  SmBasicSettingParam param, HttpServletRequest request){
//        return iSmBasicSettingService.update(id,param, RequestHolder.user().getId(), NetWorkUtils.getRemoteHost(request));
//    }

}
