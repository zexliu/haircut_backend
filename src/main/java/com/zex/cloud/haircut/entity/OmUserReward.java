package com.zex.cloud.haircut.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.zex.cloud.haircut.enums.GenderType;
import com.zex.cloud.haircut.enums.UserRewardPublishStatus;
import com.zex.cloud.haircut.enums.UserRewardStatus;
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
 * @since 2020-03-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="OmUserReward对象", description="")
public class OmUserReward implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private Long userId;

    private String content;

    private String images;

    private GenderType genderType;

    private Integer height;

    private Integer weight;

    private String job;

    private String hairVolume;

    private BigDecimal rewardAmount;

    private UserRewardStatus rewardStatus;

    private UserRewardPublishStatus publishStatus;

    @TableLogic
    private Boolean deleteStatus;
    private Long orderId;

    private Boolean anonymousStatus;

    private LocalDateTime createAt;

    private String nickname;

    private String avatar;

    private Integer commentCount;

    private Integer praiseCount;


}
