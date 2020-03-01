package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.SmFeedback;
import com.zex.cloud.haircut.enums.FeedbackStatus;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.params.SmFeedbackParam;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.ISmFeedbackService;
import com.zex.cloud.haircut.util.NetWorkUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/api/v1/feedback")
@Api(tags = "意见反馈相关")
public class SmFeedbackController {
@Autowired
private ISmFeedbackService iSmFeedbackService;

    @GetMapping
    @ApiOperation("查询列表")
    public IPage<SmFeedback>list(Pageable pageable, FeedbackStatus status ,Long userId){
        return iSmFeedbackService.page(pageable.convert(),new LambdaQueryWrapper<SmFeedback>()
        .eq(status != null,SmFeedback::getStatus, status)
        .eq(userId != null,SmFeedback::getUserId,userId));
    }

    @PostMapping
    @ApiOperation("新增")
    public SmFeedback create(@Valid @RequestBody SmFeedbackParam param){
        return  iSmFeedbackService.save(param, RequestHolder.user().getId());

    }

    @PutMapping("/status/{id}")
    @ApiOperation("处理")
    public String updateStatus(@PathVariable  Long id , FeedbackStatus status, HttpServletRequest request){
         iSmFeedbackService.updateStatus(id,status,RequestHolder.user().getId(), NetWorkUtils.getRemoteHost(request));
        return SimpleResp.SUCCESS;

    }

}
