package com.zex.cloud.haircut.params;

import com.zex.cloud.haircut.enums.PermissionMethodType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SyPermissionParam {

    @NotBlank
    private String name;

    @NotBlank
    private String url;

    @NotNull
    private PermissionMethodType methodType;

    private Integer seq;

    private String remark;

    @NotNull
    private Long moduleId;

}
