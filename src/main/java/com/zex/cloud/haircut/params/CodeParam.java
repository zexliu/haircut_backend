package com.zex.cloud.haircut.params;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CodeParam {

    @NotBlank
    String code;
}
