package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zex.cloud.haircut.entity.OmComment;
import com.zex.cloud.haircut.entity.OmCommentScore;
import com.zex.cloud.haircut.entity.OmShopOrder;
import com.zex.cloud.haircut.entity.SmShop;
import com.zex.cloud.haircut.enums.ClientType;
import com.zex.cloud.haircut.enums.CommentFromType;
import com.zex.cloud.haircut.enums.CommentStarLevel;
import com.zex.cloud.haircut.enums.CommentTopicType;
import com.zex.cloud.haircut.exception.NotFoundException;
import com.zex.cloud.haircut.mapper.OmCommentMapper;
import com.zex.cloud.haircut.params.CommentReplyParam;
import com.zex.cloud.haircut.params.OmCommentOrderParam;
import com.zex.cloud.haircut.params.OmCommentRewardParam;
import com.zex.cloud.haircut.response.OmOrderCommentInfo;
import com.zex.cloud.haircut.security.RequestUser;
import com.zex.cloud.haircut.service.IOmCommentScoreService;
import com.zex.cloud.haircut.service.IOmCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zex.cloud.haircut.service.IOmShopOrderService;
import com.zex.cloud.haircut.service.ISmShopService;
import com.zex.cloud.haircut.vo.ScoreCountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-02-29
 */
@Service
public class OmCommentServiceImpl extends ServiceImpl<OmCommentMapper, OmComment> implements IOmCommentService {

    @Autowired
    private IOmShopOrderService iOmShopOrderService;

    @Autowired
    private IOmCommentScoreService iOmCommentScoreService;

    @Autowired
    private ISmShopService iSmShopService;

    @Override
    @Transactional
    public OmComment commentOrder(Long shopOrderId, RequestUser user, OmCommentOrderParam param) {
        OmShopOrder shopOrder = iOmShopOrderService.comment(shopOrderId, user.getId());
        OmComment omComment = new OmComment();
        omComment.setCommentCount(0);
        omComment.setContent(param.getContent());
        omComment.setCreateAt(LocalDateTime.now());
        omComment.setDeleteStatus(false);
        omComment.setFromAvatar(user.getAvatar());
        omComment.setFromName(user.getNickname());
        omComment.setAnonymousStatus(param.getAnonymousStatus());
        omComment.setFromId(user.getId());
        omComment.setFromType(CommentFromType.USER);
        omComment.setImages(param.getImages());
        omComment.setPraiseCount(0);
        omComment.setTopicId(shopOrderId);
        omComment.setTopicType(CommentTopicType.ORDER);
        omComment.setRoot(true);
        save(omComment);

        OmCommentScore omCommentScore = new OmCommentScore();
        omCommentScore.setCommentId(omComment.getId());
        omCommentScore.setDeleteStatus(false);
        omCommentScore.setScore(param.getScore());
        omCommentScore.setHygieneScore(param.getHygieneScore());
        omCommentScore.setServiceScore(param.getServiceScore());
        omCommentScore.setSkillScore(param.getSkillScore());
        omCommentScore.setShopId(shopOrder.getShopId());
        omCommentScore.setStylistId(shopOrder.getStylistId());
        iOmCommentScoreService.save(omCommentScore);
        return omComment;
    }

    @Override
    public OmComment reply(Long id, RequestUser user, CommentReplyParam param) {
        OmComment replyComment = getById(id);
        if (replyComment == null) {
            throw new NotFoundException();
        }
        OmComment omComment = new OmComment();
        omComment.setCommentCount(0);
        omComment.setContent(param.getContent());
        omComment.setCreateAt(LocalDateTime.now());
        omComment.setDeleteStatus(false);
        if (param.getFromType() == CommentFromType.USER) {
            omComment.setFromAvatar(user.getAvatar());
            omComment.setFromName(user.getNickname());
            omComment.setFromId(user.getId());
        } else {
            SmShop smShop = iSmShopService.getById(user.getShopId());
            if (smShop == null) {
                throw new NotFoundException("店铺不存在");
            }
            omComment.setFromAvatar(smShop.getLogo());
            omComment.setFromName(smShop.getName());
            omComment.setFromId(smShop.getId());
        }
        omComment.setAnonymousStatus(param.getAnonymousStatus());
        omComment.setFromType(param.getFromType());
        omComment.setImages(param.getImages());
        omComment.setPraiseCount(0);
        omComment.setTopicId(replyComment.getTopicId());
        omComment.setTopicType(replyComment.getTopicType());
        omComment.setToType(replyComment.getFromType());
        omComment.setToName(replyComment.getFromName());
        omComment.setToId(replyComment.getFromId());
        omComment.setRoot(false);
        omComment.setReplyId(id);
        save(omComment);
        replyComment.setCommentCount(replyComment.getCommentCount() + 1);
        return omComment;
    }

    @Override
    public void delete(Long id) {
        removeById(id);
        iOmCommentScoreService.remove(new LambdaQueryWrapper<OmCommentScore>()
                .eq(OmCommentScore::getCommentId,id));
    }

    @Override
    public IPage<OmOrderCommentInfo> orderCommentPage(Page<OmOrderCommentInfo> page, Long shopId, Long stylistId,Long orderId, CommentStarLevel starLevel) {
        String levelSql = null;
        if (starLevel != null){
            if (starLevel == CommentStarLevel.GOOD){
                levelSql = "oc.score > 3";
            }else if (starLevel == CommentStarLevel.MIDDLE){
                levelSql = "oc.score = 3";
            }else {
                levelSql = "oc.score < 3";
            }
        }
        return baseMapper.orderCommentPage(page,shopId,stylistId,orderId,levelSql);
    }

    @Override
    public ScoreCountVO getScoreCountVo(Long shopId, Long stylistId) {
        return baseMapper.shopScoreCountVO(shopId,stylistId);
    }

    @Override
    public OmComment commentReward(Long id, RequestUser user, OmCommentRewardParam param) {
        OmComment omComment = new OmComment();
        omComment.setCommentCount(0);
        omComment.setContent(param.getContent());
        omComment.setCreateAt(LocalDateTime.now());
        omComment.setDeleteStatus(false);
        omComment.setAnonymousStatus(param.getAnonymousStatus());

        if (param.getFromType() == CommentFromType.USER) {
            omComment.setFromAvatar(user.getAvatar());
            omComment.setFromName(user.getNickname());
            omComment.setFromId(user.getId());
        } else {
            SmShop smShop = iSmShopService.getById(user.getShopId());
            if (smShop == null) {
                throw new NotFoundException("店铺不存在");
            }
            omComment.setFromAvatar(smShop.getLogo());
            omComment.setFromName(smShop.getName());
            omComment.setFromId(smShop.getId());
        }

        omComment.setFromType(param.getFromType());
        omComment.setImages(param.getImages());
        omComment.setPraiseCount(0);
        omComment.setTopicId(id);
        omComment.setTopicType(CommentTopicType.REWARD);
        omComment.setRoot(true);
        save(omComment);
        return omComment;
    }

    @Override
    public void praise(Long id) {
        baseMapper.praise(id);
    }

    @Override
    public void unPraise(Long id) {
        baseMapper.unPraise(id);
    }


}
