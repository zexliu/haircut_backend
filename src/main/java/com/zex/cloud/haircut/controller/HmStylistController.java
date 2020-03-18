package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.HmStylist;
import com.zex.cloud.haircut.entity.SmShopTitle;
import com.zex.cloud.haircut.enums.ShopWorkStatus;
import com.zex.cloud.haircut.params.BaseTitleParam;
import com.zex.cloud.haircut.params.HmStylistParam;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.IHmStylistService;
import com.zex.cloud.haircut.service.ISmShopTitleService;
import com.zex.cloud.haircut.vo.HmStylistCollectVO;
import com.zex.cloud.haircut.vo.HmStylistDetail;
import com.zex.cloud.haircut.vo.HmStylistDetailVO;
import com.zex.cloud.haircut.vo.HmStylistVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
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
@Api(tags = "发型师相关操作")
@RequestMapping("/api/v1/stylists")
public class HmStylistController {


    @Autowired
    private IHmStylistService iHmStylistService;

    @GetMapping
    @ApiOperation("查询设计师列表")
    public IPage<HmStylist> list(Pageable pageable, Long shopId, String keywords, ShopWorkStatus workStatus){
        return iHmStylistService.page(pageable.convert(),new LambdaQueryWrapper<HmStylist>()
        .eq(shopId != null,HmStylist::getShopId,shopId)
        .eq(StringUtils.isNotBlank(keywords),HmStylist::getNickname,keywords)
        .eq(workStatus != null,HmStylist::getWorkStatus,workStatus)
        .orderByDesc(HmStylist::getSeq));
    }

    @GetMapping("/client")
    @ApiOperation("客户端搜索设计师")
    public IPage<HmStylistCollectVO> list(Pageable pageable, String keywords,@RequestParam Double latitude, @RequestParam Double longitude){
        return iHmStylistService.list(pageable.convert(),keywords,latitude,longitude);
    }

    @GetMapping("/{id}")
    @ApiOperation("通过ID查询")
    public HmStylistDetail getById(@PathVariable Long id){
        return iHmStylistService.getDetailById(id);
    }


    @GetMapping("/details/{id}")
    @ApiOperation("客户端获取发型师详情")
    public HmStylistDetailVO detail(@PathVariable Long id,@RequestParam Double latitude,@RequestParam Double longitude){
        return iHmStylistService.detailVO(id,latitude,longitude,RequestHolder.user().getId());
    }

    @PostMapping
    @ApiOperation("新增")
    public HmStylist save(@RequestBody HmStylistParam param){
        return iHmStylistService.save(param, RequestHolder.user().getShopId());
    }

    @PutMapping("/{id}")
    @ApiOperation("修改")
    public HmStylist update(@PathVariable Long id,@RequestBody HmStylistParam param){
        return iHmStylistService.update(id,param,RequestHolder.user().getShopId());
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除")
    public String delete(@PathVariable Long id){
        iHmStylistService.delete(id,RequestHolder.user().getShopId());
        return SimpleResp.SUCCESS;
    }


    @GetMapping("/collect")
    @ApiOperation("获取收藏的设计师")
    public IPage<HmStylistCollectVO>  collectStylist(Pageable pageable,@RequestParam Double latitude ,
                                                     @RequestParam Double longitude){
       return iHmStylistService.getCollectStylist(pageable.convert(),RequestHolder.user().getId(),latitude,longitude);
    }

}
