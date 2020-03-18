package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zex.cloud.haircut.entity.OmComment;
import com.zex.cloud.haircut.entity.OmOrder;
import com.zex.cloud.haircut.entity.OmShopOrder;
import com.zex.cloud.haircut.entity.OmUserReward;
import com.zex.cloud.haircut.enums.CommentFromType;
import com.zex.cloud.haircut.enums.ShopOrderStatus;
import com.zex.cloud.haircut.enums.UserRewardPublishStatus;
import com.zex.cloud.haircut.enums.UserRewardStatus;
import com.zex.cloud.haircut.exception.ForbiddenException;
import com.zex.cloud.haircut.exception.NotFoundException;
import com.zex.cloud.haircut.exception.ParameterException;
import com.zex.cloud.haircut.exception.ServerException;
import com.zex.cloud.haircut.mapper.OmUserRewardMapper;
import com.zex.cloud.haircut.params.OmOrderParam;
import com.zex.cloud.haircut.params.OmShopOrderBodyParam;
import com.zex.cloud.haircut.params.OmUserRewardBodyParam;
import com.zex.cloud.haircut.security.RequestUser;
import com.zex.cloud.haircut.service.IOmCommentService;
import com.zex.cloud.haircut.service.IOmShopTransactionService;
import com.zex.cloud.haircut.service.IOmUserRewardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zex.cloud.haircut.service.IOmUserTransactionService;
import com.zex.cloud.haircut.vo.OmUserReWardVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-03-02
 */
@Service
@Slf4j
public class OmUserRewardServiceImpl extends ServiceImpl<OmUserRewardMapper, OmUserReward> implements IOmUserRewardService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IOmCommentService iOmCommentService;

    @Autowired
    private IOmUserTransactionService iOmUserTransactionService;

    @Autowired
    private IOmShopTransactionService iOmShopTransactionService;
    @Override
    public String validOrderAndCreate(OmOrderParam param, RequestUser user, Long generateId) throws JsonProcessingException {

        OmUserRewardBodyParam body = param.getRewardBody();

        if (body == null) {
            throw new ParameterException("rewardBody不能为空");
        }

        OmUserReward userReward = new OmUserReward();
        userReward.setAnonymousStatus(body.getAnonymousStatus());
        userReward.setAvatar(user.getAvatar());
        userReward.setCommentCount(0);
        userReward.setContent(body.getContent());
        userReward.setHairVolume(body.getHairVolume());
        userReward.setHeight(body.getHeight());
        userReward.setImages(body.getImages());
        userReward.setJob(body.getJob());
        userReward.setOrderId(generateId);
        userReward.setNickname(user.getNickname());
        userReward.setPraiseCount(0);
        userReward.setGenderType(body.getSexType());
        userReward.setRewardStatus(UserRewardStatus.PENDING_REWARD);
        userReward.setPublishStatus(UserRewardPublishStatus.PENDING_PAY);
        userReward.setRewardAmount(param.getAmount());
        userReward.setUserId(user.getId());
        userReward.setWeight(body.getWeight());
        save(userReward);
        return objectMapper.writeValueAsString(userReward);
    }

    @Override
    public void cancelOrder(Long id) {
        OmUserReward reward = getByOrderId(id);
        if (reward == null || reward.getPublishStatus() != UserRewardPublishStatus.PENDING_PAY) {
            log.warn("user reward not found , orderId = {}", id);
            return;
        }
        reward.setPublishStatus(UserRewardPublishStatus.CANCEL);
        updateById(reward);
    }

    @Override
    public void onPayHook(OmOrder omOrder) {
        OmUserReward reward = getByOrderId(omOrder.getId());
        if (reward == null || reward.getPublishStatus() != UserRewardPublishStatus.PENDING_PAY) {
            throw new ServerException();
        }
        reward.setPublishStatus(UserRewardPublishStatus.PUBLISHED);
        updateById(reward);
    }

    @Override
    @Transactional
    public void rewardStatus(Long id, Long commentId, Long userId) {
        OmUserReward userReward = getById(id);
        if (userReward == null) {
            throw new NotFoundException("悬赏动态不存在");
        }
        if (userReward.getPublishStatus() != UserRewardPublishStatus.PUBLISHED){
            throw new ForbiddenException("当前动态不在发布状态");
        }
        if (userReward.getRewardStatus() != UserRewardStatus.PENDING_REWARD){
            throw new ForbiddenException("当前动态不在待悬赏状态");
        }
        OmComment omComment = iOmCommentService.getById(commentId);
        if (omComment == null){
            throw new NotFoundException("评论不存在");
        }
        if (!omComment.getTopicId().equals(id)){
            throw new ForbiddenException("该评论不属于该动态");
        }
        if (omComment.getFromType() == CommentFromType.SHOP){
            iOmShopTransactionService.onReward(omComment.getFromId(),id,userReward.getRewardAmount());
        }else if (omComment.getFromType() == CommentFromType.USER){
            iOmUserTransactionService.onReward(omComment.getFromId(),id,userReward.getRewardAmount());
        }


    }

    @Override
    public IPage<OmUserReWardVO> page(Page<OmUserReWardVO> convert, UserRewardStatus rewardStatus, UserRewardPublishStatus publishStatus,Long currentUserId,Long userId) {
        return baseMapper.queryUserRewardVO(convert,rewardStatus,publishStatus,currentUserId,userId);
    }

    private OmUserReward getByOrderId(Long id) {
        return getOne(new LambdaQueryWrapper<OmUserReward>().eq(OmUserReward::getOrderId, id));
    }
}
