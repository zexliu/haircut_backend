package com.zex.cloud.haircut.params;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PasswordAuthCodeParam {

    @NotBlank
    String username;
    @NotBlank
    String authCode;
    @NotBlank
    String newPassword;
}
