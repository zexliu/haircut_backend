package com.zex.cloud.haircut.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zex.cloud.haircut.config.JsonLongSerializer;
import com.zex.cloud.haircut.enums.AuditStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Zex
 * @since 2020-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SmAgentApply对象", description="")
public class SmAgentApply implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long id;

    private Integer provinceCode;

    private Integer cityCode;

    private String name;

    private String linkMobile;

    private String email;

    private String identityCardFront;

    private String identityCardBack;

    private String identityCardNo;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private Integer version;

    private Long userId;

    private AuditStatus auditStatus;

    private String remark;


}
