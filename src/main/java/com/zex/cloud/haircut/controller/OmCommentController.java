package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.OmComment;
import com.zex.cloud.haircut.enums.CommentFromType;
import com.zex.cloud.haircut.enums.CommentStarLevel;
import com.zex.cloud.haircut.enums.CommentTopicType;
import com.zex.cloud.haircut.params.CommentReplyParam;
import com.zex.cloud.haircut.params.OmCommentOrderParam;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.response.OmOrderCommentInfo;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.IOmCommentService;
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
 * @since 2020-02-29
 */
@RestController
@RequestMapping("/api/v1/comments")
@Api(tags = "评论相关")
public class OmCommentController {

    @Autowired
    private IOmCommentService iOmCommentService;

    @ApiOperation("获取订单评论列表")
    @GetMapping("/order")
    public IPage<OmOrderCommentInfo> orderPage(Pageable pageable, Long shopId, Long orderId, CommentStarLevel starLevel) {
        return  iOmCommentService.orderCommentPage(pageable.convert(),shopId,orderId,starLevel);
    }


    @ApiOperation("获取订单评论列表")
    @GetMapping
    public IPage<OmComment> commentPage(Pageable pageable, Long topicId, CommentTopicType topicType,
                                        Long fromId, CommentFromType fromType,  Long replyId,Boolean root,Long toId) {
        return  iOmCommentService.page(pageable.convert(),new LambdaQueryWrapper<OmComment>()
        .eq(topicId != null , OmComment::getTopicId,topicId)
        .eq(topicType != null,OmComment::getTopicType,topicType)
        .eq(fromId != null, OmComment::getFromId,fromId)
        .eq(fromType != null,OmComment::getFromType,fromType)
        .eq(replyId != null,OmComment::getReplyId,replyId)
        .eq(root != null,OmComment::getRoot,root)
        .eq(toId != null, OmComment::getToId,toId)
        .orderByDesc(OmComment::getCreateAt));
    }



    @ApiOperation("评价订单")
    @PostMapping("/order/{shopOrderId}")
    public OmComment commentOrder(@PathVariable Long shopOrderId, @RequestBody @Valid OmCommentOrderParam param) {
        return iOmCommentService.commentOrder(shopOrderId, RequestHolder.user(), param);
    }

    @ApiOperation("回复评价")
    @PostMapping("/reply/{id}")
    public OmComment reply(@PathVariable Long id, @RequestBody @Valid CommentReplyParam param) {
        return iOmCommentService.reply(id, RequestHolder.user(), param);
    }


    @ApiOperation("删除评论")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        iOmCommentService.delete(id);
        return SimpleResp.SUCCESS;
    }


}
