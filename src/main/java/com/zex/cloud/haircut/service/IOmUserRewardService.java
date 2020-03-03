package com.zex.cloud.haircut.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zex.cloud.haircut.entity.OmOrder;
import com.zex.cloud.haircut.entity.OmUserReward;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.params.OmOrderParam;
import com.zex.cloud.haircut.security.RequestUser;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-03-02
 */
public interface IOmUserRewardService extends IService<OmUserReward> {

    String validOrderAndCreate(OmOrderParam param, RequestUser user, Long generateId) throws JsonProcessingException;

    void cancelOrder(Long id);

    void onPayHook(OmOrder omOrder);

    void rewardStatus(Long id, Long commentId, Long userId);
}
