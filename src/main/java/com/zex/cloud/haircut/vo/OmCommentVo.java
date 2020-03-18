package com.zex.cloud.haircut.vo;

import com.zex.cloud.haircut.entity.OmComment;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OmCommentVo extends OmComment {
    @ApiModelProperty("当前用户是否点赞过")
    Boolean praised;

    @ApiModelProperty("是否为获得悬赏的评论")
    Boolean rewarded;
}
