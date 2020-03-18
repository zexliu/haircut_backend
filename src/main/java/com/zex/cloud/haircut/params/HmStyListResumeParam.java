package com.zex.cloud.haircut.params;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class HmStyListResumeParam {

    @NotBlank
    private String studioName;
    @NotNull
    private LocalDate startAt;
    @NotNull
    private LocalDate endAt;

}
