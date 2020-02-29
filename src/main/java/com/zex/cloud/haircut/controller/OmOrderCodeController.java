package com.zex.cloud.haircut.controller;

import com.zex.cloud.haircut.enums.OrderCodeType;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.IOmOrderCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "核销码相关")
@RestController
@RequestMapping("/api/v1/order/code")
public class OmOrderCodeController {

    @Autowired
    private IOmOrderCodeService iOmOrderCodeService;

    @GetMapping("/{id}")
    @ApiOperation("通过订单ID获取核销码")
    public String code(@PathVariable  Long id, @RequestParam OrderCodeType type){
        return  iOmOrderCodeService.getCode(id,type, RequestHolder.user().getId());
    }

    @PostMapping("/{code}")
    @ApiOperation("使用核销码 核销订单")
    public String use(@PathVariable  String code){
        return iOmOrderCodeService.use(code,RequestHolder.user().getShopId());
    }

}
