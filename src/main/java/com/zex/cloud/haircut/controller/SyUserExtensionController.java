package com.zex.cloud.haircut.controller;


import com.zex.cloud.haircut.entity.SyUserExtension;
import com.zex.cloud.haircut.params.SyUserExtensionParam;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.ISyUserExtensionService;
import com.zex.cloud.haircut.vo.SyUserExtensionVO;
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
 * @since 2020-03-16
 */
@RestController
@RequestMapping("/api/v1/user/extension")
@Api(tags = "用户拓展信息")
public class SyUserExtensionController {
    @Autowired
    private ISyUserExtensionService iSyUserExtensionService;


    @GetMapping
    @ApiOperation("获取用户拓展信息")
    public SyUserExtensionVO extension() {
        return iSyUserExtensionService.extension(RequestHolder.user().getId());
    }

    @PutMapping
    @ApiOperation("修改用户拓展信息")
    public SyUserExtension update(@RequestBody @Valid SyUserExtensionParam param) {
        return iSyUserExtensionService.update(RequestHolder.user().getId(),param);
    }


}
