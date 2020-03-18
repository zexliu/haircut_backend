package com.zex.cloud.haircut.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zex.cloud.haircut.config.JsonLongSerializer;
import com.zex.cloud.haircut.enums.ShopWorkStatus;
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
 * @since 2020-02-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="HmStylist对象", description="")
public class HmStylist implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long id;

    private String avatar;

    private String nickname;

    @JsonSerialize(using = JsonLongSerializer.class)
    private Long jobTitleId;

    private String introduction;

    private String coverImages;

    private String htmlIntro;

    private ShopWorkStatus workStatus;
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long shopId;

    @TableLogic
    private Boolean deleteStatus;

    private LocalDateTime createAt;

    private Integer seq;

    private Integer workAge;

}
