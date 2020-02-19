package com.zex.cloud.haircut.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zex.cloud.haircut.enums.AuditStatus;

public interface AuditProcessor {


    String auditProcess(Long id , AuditStatus auditStatus) ;


}
