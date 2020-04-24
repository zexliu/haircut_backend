package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.OmComment;
import com.zex.cloud.haircut.enums.CommentFromType;
import com.zex.cloud.haircut.enums.CommentStarLevel;
import com.zex.cloud.haircut.enums.CommentTopicType;
import com.zex.cloud.haircut.params.CommentReplyParam;
import com.zex.cloud.haircut.params.OmCommentOrderParam;
import com.zex.cloud.haircut.params.OmCommentRewardParam;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.response.OmOrderCommentInfo;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.IOmCommentService;
import com.zex.cloud.haircut.vo.ScoreCountVO;
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
    public IPage<OmOrderCommentInfo> orderPage(Pageable pageable, Long shopId, Long stylistId, Long orderId, CommentStarLevel starLevel) {
        return  iOmCommentService.orderCommentPage(pageable.convert(),shopId,stylistId,orderId,starLevel);
    }

    @ApiOperation("获取当前店铺 评分和评论总数")
    @GetMapping("/score")
    public ScoreCountVO shopScoreCountVO(){
        return iOmCommentService.getScoreCountVo(RequestHolder.user().getShopId(),null);
    }


    @ApiOperation("客户端获取评论和评分数")
    @GetMapping("/client/score")
    public ScoreCountVO shopScoreCountVO(Long shopId, Long stylistId){
        return iOmCommentService.getScoreCountVo(shopId,stylistId);
    }

    @ApiOperation("获取当前店铺评分列表")
    @GetMapping("/order/current")
    public IPage<OmOrderCommentInfo> currentOrderPage(Pageable pageable,Long stylistId, Long orderId,CommentStarLevel starLevel) {
        Long shopId = RequestHolder.user().getShopId();
        return  orderPage(pageable, shopId,stylistId, orderId, starLevel);
    }

    @ApiOperation("获取订单评论列表")
    @GetMapping
    public IPage<OmComment> commentPage(Pageable pageable, Long topicId, CommentTopicType topicType,
                                        Long fromId, CommentFromType fromType, CommentFromType toType,  Long replyId,Boolean root,Long toId) {
        return  iOmCommentService.page(pageable.convert(),new LambdaQueryWrapper<OmComment>()
        .eq(topicId != null , OmComment::getTopicId,topicId)
        .eq(topicType != null,OmComment::getTopicType,topicType)
        .eq(fromId != null, OmComment::getFromId,fromId)
        .eq(fromType != null,OmComment::getFromType,fromType)
        .eq(toType != null,OmComment::getToType,toType)
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



    @ApiOperation("评论悬赏")
    @PostMapping("/reward/{id}")
    public OmComment commentReward(@PathVariable Long id, @RequestBody @Valid OmCommentRewardParam param) {
        return iOmCommentService.commentReward(id, RequestHolder.user(), param);
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
