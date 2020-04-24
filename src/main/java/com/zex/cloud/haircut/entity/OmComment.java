package com.zex.cloud.haircut.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zex.cloud.haircut.config.JsonLongSerializer;
import com.zex.cloud.haircut.enums.CommentFromType;
import com.zex.cloud.haircut.enums.CommentTopicType;
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
@ApiModel(value="OmComment对象", description="")
public class OmComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long id;
    @JsonSerialize(using = JsonLongSerializer.class)

    private Long topicId;

    private CommentTopicType topicType;
    @JsonSerialize(using = JsonLongSerializer.class)

    private Long fromId;

    private String fromAvatar;

    private String fromName;

    private CommentFromType fromType;
    @JsonSerialize(using = JsonLongSerializer.class)

    private Long toId;

    private String toName;

    private CommentFromType toType;

    private String content;

    private String images;
    @JsonSerialize(using = JsonLongSerializer.class)

    private Long replyId;

    @TableLogic
    private Boolean deleteStatus;

    private Boolean root;

    private LocalDateTime createAt;

    private Integer praiseCount;

    private Integer commentCount;

    private Boolean anonymousStatus;
}
