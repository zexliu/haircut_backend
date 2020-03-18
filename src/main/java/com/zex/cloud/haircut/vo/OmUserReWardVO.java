package com.zex.cloud.haircut.vo;

import com.zex.cloud.haircut.entity.OmUserReward;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class OmUserReWardVO  extends OmUserReward {
    @ApiModelProperty("评论记录")
    List<OmCommentVo> comments;
    @ApiModelProperty("当前用户是否点赞过")
    Boolean praised;
}
