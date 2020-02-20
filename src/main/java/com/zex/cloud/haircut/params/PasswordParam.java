package com.zex.cloud.haircut.params;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PasswordParam {

    @NotNull
    String username;
    @NotNull
    String oldPassword;
    @NotNull
    String newPassword;
}
