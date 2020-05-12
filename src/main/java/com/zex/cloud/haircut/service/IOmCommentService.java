package com.zex.cloud.haircut.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zex.cloud.haircut.entity.OmComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.enums.CommentStarLevel;
import com.zex.cloud.haircut.params.CommentReplyParam;
import com.zex.cloud.haircut.params.OmCommentOrderParam;
import com.zex.cloud.haircut.params.OmCommentRewardParam;
import com.zex.cloud.haircut.response.OmOrderCommentInfo;
import com.zex.cloud.haircut.security.RequestUser;
import com.zex.cloud.haircut.vo.ScoreCountVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-29
 */
public interface IOmCommentService extends IService<OmComment> {

    OmComment commentOrder(Long shopOrderId, RequestUser user, OmCommentOrderParam param, Boolean auto);

    OmComment reply(Long id, RequestUser user, CommentReplyParam param);

    void delete(Long id);

    IPage<OmOrderCommentInfo> orderCommentPage(Page<OmOrderCommentInfo> page, Long shopId,Long stylistId, Long orderId, CommentStarLevel starLevel);

    ScoreCountVO getScoreCountVo(Long shopId, Long stylistId);

    OmComment commentReward(Long id, RequestUser user, OmCommentRewardParam param);

    void praise(Long id);

    void unPraise(Long id);
}
