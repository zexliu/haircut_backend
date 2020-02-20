package com.zex.cloud.haircut.params;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PasswordCurrentParam {


    @NotNull
    String oldPassword;
    @NotNull
    String newPassword;
}
