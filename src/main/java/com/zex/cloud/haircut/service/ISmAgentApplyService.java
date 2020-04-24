package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.SmAgentApply;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.entity.SmShopApply;
import com.zex.cloud.haircut.params.SmAgentApplyParam;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.response.SmAgentApplyDetail;
import com.zex.cloud.haircut.response.SmShopApplyDetail;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-03-18
 */
public interface ISmAgentApplyService extends IService<SmAgentApply> {

    SmAgentApply getApplyByUserId(Long id);

    SmAgentApply create(SmAgentApplyParam param, Long userId);
    SmAgentApply update(SmAgentApplyParam param, Long userId);

    SmAgentApplyDetail detail(Long id);

    void sendAuthCode(String mobile);

}
