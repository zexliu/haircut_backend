package com.zex.cloud.haircut.params;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class OmCommentOrderParam {

    @ApiModelProperty("评价内容")
    @NotBlank
    private String content;
    @ApiModelProperty("评价内容")
    private String images;
    @ApiModelProperty("评分")
    @NotNull
    private Integer score;
    @ApiModelProperty("技术评分")
    @NotNull
    private Integer skillScore;
    @ApiModelProperty("卫生评分")
    @NotNull
    private Integer hygieneScore;
    @ApiModelProperty("服务评分")
    @NotNull
    private Integer serviceScore;

    @ApiModelProperty("是否匿名评价")
    @NotNull
    private Boolean anonymousStatus;
}
