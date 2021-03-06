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
 * @since 2020-02-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SmShopApply对象", description="")
public class SmShopApply implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long id;

    private String name;

    private String address;

    private Integer provinceCode;

    private Integer cityCode;

    private Integer districtCode;

    private String businessLicense;

    private String photo;

    private String leaderName;

    private String leaderMobile;

    private AuditStatus auditStatus;

    private Double longitude;

    private Double latitude;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private Integer version;

    @JsonSerialize(using = JsonLongSerializer.class)
    private Long userId;

    private String identityCardFront;
    private String identityCardBack;
    private String socialCreditCode;

    private String coverImage;

}
