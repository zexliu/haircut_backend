package com.zex.cloud.haircut.service.impl;

import com.zex.cloud.haircut.entity.SmFeedback;
import com.zex.cloud.haircut.enums.FeedbackStatus;
import com.zex.cloud.haircut.mapper.SmFeedbackMapper;
import com.zex.cloud.haircut.params.SmFeedbackParam;
import com.zex.cloud.haircut.service.ISmFeedbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-03-01
 */
@Service
public class SmFeedbackServiceImpl extends ServiceImpl<SmFeedbackMapper, SmFeedback> implements ISmFeedbackService {

    @Override
    public SmFeedback save(SmFeedbackParam param, Long userId) {
        SmFeedback feedback = new SmFeedback();
        BeanUtils.copyProperties(param,feedback);
        feedback.setUserId(userId);
        feedback.setStatus(FeedbackStatus.PENDING);
        save(feedback);
        return feedback;
    }

    @Override
    public void updateStatus(Long id, FeedbackStatus status, Long userId, String ip) {
        SmFeedback feedback = new SmFeedback();
        feedback.setId(id);
        feedback.setStatus(status);
        feedback.setOperatorAt(LocalDateTime.now());
        feedback.setOperatorId(userId);
        feedback.setOperatorIp(ip);
        updateById(feedback);
    }
}
