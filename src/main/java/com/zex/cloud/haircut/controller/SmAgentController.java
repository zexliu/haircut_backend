package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.SmAgent;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.ISmAgentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import retrofit2.http.Body;

import java.util.function.Consumer;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-03-18
 */
@RestController
@RequestMapping("/api/v1/agent")
@Api(tags = "代理商信息")
public class SmAgentController {

    @Autowired
    private ISmAgentService iSmAgentService;

    @GetMapping("/current")
    public SmAgent current() {
        return iSmAgentService.getByUserId(RequestHolder.user().getId());
    }


    @PostMapping("/{id}/enableStatus")
    public String enableStatus(@RequestParam Boolean enableStatus,@PathVariable Long id ){
         iSmAgentService.updateEnableStatus(enableStatus,id);
        return SimpleResp.SUCCESS;
    }
    @GetMapping
    public IPage<SmAgent> list(Pageable pageable, String keywords) {
        return iSmAgentService.page(pageable.convert(), new LambdaQueryWrapper<SmAgent>()
                .and(StringUtils.isNotBlank(keywords), i ->
                        i.like(SmAgent::getLinkMobile,keywords)
                        .or()
                        .like(SmAgent::getEmail,keywords)
                        .or()
                        .like(SmAgent::getName,keywords))
        );
    }
}
