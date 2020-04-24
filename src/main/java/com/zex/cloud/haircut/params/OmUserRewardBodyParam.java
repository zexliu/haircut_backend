package com.zex.cloud.haircut.params;

import com.zex.cloud.haircut.enums.GenderType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class OmUserRewardBodyParam {

    @NotBlank
    private String content;

    private String images;
    @NotNull
    private GenderType sexType;
    @NotNull
    private Integer height;
    @NotNull
    private Integer weight;

    private String job;

    private String hairVolume;

    @NotNull
    private Boolean anonymousStatus;
    private Integer age;

}
