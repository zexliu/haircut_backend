package com.zex.cloud.haircut.params;

import com.zex.cloud.haircut.enums.CommentFromType;
import lombok.Data;

@Data
public class CommentReplyParam {

    private String content;

    private String images;

    private CommentFromType fromType;

    private Boolean anonymousStatus;
}
