package com.zex.cloud.haircut.response;

import com.zex.cloud.haircut.entity.OmComment;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class OmOrderCommentInfo extends OmComment  {
    private Long shopId;

    private Long stylistId;

    private Integer score;

    private Integer skillScore;

    private Integer hygieneScore;

    private Integer serviceScore;

    private String stylistName;
}
