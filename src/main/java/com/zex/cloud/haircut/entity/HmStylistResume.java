package com.zex.cloud.haircut.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
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
 * @since 2020-03-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="HmStylistResume对象", description="")
public class HmStylistResume implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonSerialize(using = JsonLongSerializer.class)

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private String studioName;

    private LocalDate startAt;

    private LocalDate endAt;

    private LocalDateTime createAt;
    @JsonSerialize(using = JsonLongSerializer.class)

    private Long stylistId;


}
