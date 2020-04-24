package com.zex.cloud.haircut.params;
import com.zex.cloud.haircut.enums.CommentFromType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class OmCommentRewardParam {

    @ApiModelProperty("评论内容")
    @NotBlank
    private String content;
    @ApiModelProperty("评论图片")
    private String images;

    @ApiModelProperty("是否匿名评价")
    @NotNull
    private Boolean anonymousStatus;

    private CommentFromType fromType;
}
