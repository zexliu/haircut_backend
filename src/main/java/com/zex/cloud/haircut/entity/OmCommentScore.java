package com.zex.cloud.haircut.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;
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
 * @since 2020-02-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="OmCommentScore对象", description="")
public class OmCommentScore implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long id;
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long commentId;
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long shopId;
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long stylistId;

    private Integer score;

    private Integer skillScore;

    private Integer hygieneScore;

    private Integer serviceScore;
    @TableLogic
    private Boolean deleteStatus;


}
