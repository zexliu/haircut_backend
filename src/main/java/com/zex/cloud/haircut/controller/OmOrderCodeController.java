package com.zex.cloud.haircut.controller;

import com.google.zxing.WriterException;
import com.zex.cloud.haircut.enums.OrderCodeType;
import com.zex.cloud.haircut.params.CodeParam;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.IOmOrderCodeService;
import com.zex.cloud.haircut.vo.ShopCodeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@Api(tags = "核销码相关")
@RestController
@RequestMapping("/api/v1/order/code")
public class OmOrderCodeController {

    @Autowired
    private IOmOrderCodeService iOmOrderCodeService;

    @GetMapping("/{id}")
    @ApiOperation("通过订单ID获取核销码")
    public ShopCodeVO code(@PathVariable  Long id, @RequestParam OrderCodeType type) throws IOException, WriterException {
        return  iOmOrderCodeService.getCode(id,type, RequestHolder.user().getId());
    }

    @PostMapping
    @ApiOperation("使用核销码 核销订单")
    public String use(@RequestBody @Valid CodeParam code){
        return iOmOrderCodeService.use(code.getCode(),RequestHolder.user().getShopId());
    }

}
