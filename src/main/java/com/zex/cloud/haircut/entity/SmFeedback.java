package com.zex.cloud.haircut.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zex.cloud.haircut.config.JsonLongSerializer;
import com.zex.cloud.haircut.enums.FeedbackStatus;
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
 * @since 2020-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SmFeedback对象", description="")
public class SmFeedback implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long id;

    @JsonSerialize(using = JsonLongSerializer.class)
    private Long userId;

    private String linkMobile;

    private String linkName;

    private String content;

    private String images;

    private FeedbackStatus status;

    private LocalDateTime createAt;

    @JsonSerialize(using = JsonLongSerializer.class)
    private Long operatorId;

    private String operatorIp;

    private LocalDateTime operatorAt;
}
