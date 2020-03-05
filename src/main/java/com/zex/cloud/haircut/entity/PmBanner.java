package com.zex.cloud.haircut.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zex.cloud.haircut.config.JsonLongSerializer;
import com.zex.cloud.haircut.enums.BannerLinkType;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Zex
 * @since 2020-03-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="PmBanner对象", description="")
public class PmBanner implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long id;

    private String name;

    private String description;

    private Integer seq;

    private String image;

    private LocalDateTime createAt;

    private BannerLinkType linkType;

    private String linkUrl;

    private Boolean enableStatus;


}
