package com.zex.cloud.haircut.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zex.cloud.haircut.config.JsonLongSerializer;
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
 * @since 2020-02-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SyRole对象", description="")
public class SyRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long id;

    private String name;
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long parentId;
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long operatorId;

    private String operatorIp;

    private LocalDateTime operatorAt;

    private LocalDateTime createAt;

    private String remark;

    private Integer seq;


}
