package com.zex.cloud.haircut.response;

import com.zex.cloud.haircut.entity.AmAuditHistory;
import com.zex.cloud.haircut.entity.SmAgentApply;
import com.zex.cloud.haircut.entity.SmShopApply;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class SmAgentApplyDetail extends SmAgentApply {
    List<AmAuditHistory> auditHistories;
}
