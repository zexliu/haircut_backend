package com.zex.cloud.haircut.params;

import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class UmUserHairHistoryParam {

    @NotBlank
    private String images;

}
