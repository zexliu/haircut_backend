package com.zex.cloud.haircut.params;

import com.zex.cloud.haircut.enums.AuditStatus;
import com.zex.cloud.haircut.enums.AuditTargetType;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AmAuditParam {

    @NotNull
    AuditTargetType targetType;
    @NotNull
    Long targetId;
    @NotNull
    AuditStatus auditStatus;

    private String message;
}
