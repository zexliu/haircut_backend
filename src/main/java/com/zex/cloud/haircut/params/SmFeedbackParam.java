package com.zex.cloud.haircut.params;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zex.cloud.haircut.config.JsonLongSerializer;
import com.zex.cloud.haircut.enums.FeedbackStatus;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Zex
 * @since 2020-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SmFeedback对象", description="")
public class SmFeedbackParam implements Serializable {


    private String linkMobile;

    private String linkName;

    private String content;

    private String images;

}
