package com.zex.cloud.haircut.controller;


import com.zex.cloud.haircut.entity.UmPopularizeQrCode;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.IUmPopularizeQrCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-03-06
 */
@RestController
@RequestMapping("/api/v1/popularize/code")
@Api(tags = "推广二维码")
public class UmPopularizeQrCodeController {

    @Autowired
    private IUmPopularizeQrCodeService iUmPopularizeQrCodeService;
    @GetMapping("/current")
    @ApiOperation("获取推广二维码")
    public UmPopularizeQrCode getCode (){
        return iUmPopularizeQrCodeService.getQrCode(RequestHolder.user());
    }




}
