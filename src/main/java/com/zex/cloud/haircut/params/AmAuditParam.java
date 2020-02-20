package com.zex.cloud.haircut.params;

import com.zex.cloud.haircut.enums.AuditStatus;
import com.zex.cloud.haircut.enums.AuditTargetType;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AmAuditParam {

    @NotNull
    private AuditTargetType targetType;
    @NotNull
    private  Long targetId;
    @NotNull
    private  AuditStatus auditStatus;
    private String message;
}
