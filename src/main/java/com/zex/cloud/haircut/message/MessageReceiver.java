package com.zex.cloud.haircut.message;

import com.zex.cloud.haircut.config.MqConfig;
import com.zex.cloud.haircut.entity.OmOrder;
import com.zex.cloud.haircut.enums.OrderStatus;
import com.zex.cloud.haircut.enums.OrderType;
import com.zex.cloud.haircut.params.OmCommentOrderParam;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.security.RequestUser;
import com.zex.cloud.haircut.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageReceiver {

    @Autowired
    private IOmOrderService iOmOrderService;

    @Autowired
    private IOmShopOrderService iOmShopOrderService;

    @Autowired
    private IOmCommentService iOmCommentService;

    @Autowired
    private ISyUserService iSyUserService;

    @Autowired
    private IOmUserRewardService iOmUserRewardService;

    @RabbitListener(queues = MqConfig.DElAY_PAY_QUEUE)
    public void cancelOrder(OrderCreatedMessage msg){
        log.info("received order created msg  = {}",msg);
        OmOrder omOrder = iOmOrderService.getById(msg.getOrderId());
        if (omOrder == null || omOrder.getStatus() != OrderStatus.PENDING){
            return;
        }
        //设置订单为已过期
        omOrder.setStatus(OrderStatus.EXPIRE);
        iOmOrderService.updateById(omOrder);
        if (omOrder.getOrderType() == OrderType.SHOP_SERVICE){
            iOmShopOrderService.cancelOrder(omOrder.getId());
        }else if (omOrder.getOrderType() == OrderType.USER_REWARD){
            iOmUserRewardService.cancelOrder(omOrder.getId());
        }
    }


    @RabbitListener(queues = MqConfig.DElAY_COMMENT_QUEUE)
    public void commentOrder(OrderUsedMessage msg){
        OmCommentOrderParam param = new OmCommentOrderParam();
        param.setContent("系统默认好评!!!");
        param.setScore(5);
        param.setSkillScore(5);
        param.setAnonymousStatus(true);
        param.setHygieneScore(5);
        param.setSkillScore(5);
        RequestUser requestUser = iSyUserService.getRequestUser(msg.getUserId());
        iOmCommentService.commentOrder(msg.getOrderId(),requestUser,param);
    }
}
