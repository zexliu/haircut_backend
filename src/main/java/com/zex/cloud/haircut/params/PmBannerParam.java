package com.zex.cloud.haircut.params;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zex.cloud.haircut.config.JsonLongSerializer;
import com.zex.cloud.haircut.enums.BannerLinkType;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

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
public class PmBannerParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String name;

    private String description;

    private Integer seq;

    @NotBlank
    private String image;

    private String linkUrl;
}
