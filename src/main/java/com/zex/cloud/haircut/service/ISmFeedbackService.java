package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.SmFeedback;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.enums.FeedbackStatus;
import com.zex.cloud.haircut.params.SmFeedbackParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-03-01
 */
public interface ISmFeedbackService extends IService<SmFeedback> {

    SmFeedback save(SmFeedbackParam param, Long userId);

    void updateStatus(Long id, FeedbackStatus status, Long userId, String ip);

}
