package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.AmAuditHistory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.enums.AuditTargetType;
import com.zex.cloud.haircut.params.AmAuditParam;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-19
 */
public interface IAmAuditHistoryService extends IService<AmAuditHistory>  {

    List<AmAuditHistory> getByTargetIdAndType(Long targetId, AuditTargetType targetType);

    void audit(AmAuditParam param, String operatorIp, Long operatorId);
}
