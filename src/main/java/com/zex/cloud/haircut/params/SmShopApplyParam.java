package com.zex.cloud.haircut.params;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SmShopApplyParam {


    @NotBlank
    private String name;
    private String address;
    @NotNull
    private Integer provinceCode;
    @NotNull
    private Integer cityCode;
    @NotNull
    private Integer districtCode;
    @NotBlank
    private String businessLicense;
    @NotBlank
    private String photo;
    @NotBlank
    private String leaderName;
    @NotBlank
    private String leaderMobile;
    @NotNull
    private Double longitude;
    @NotNull
    private Double latitude;
    @NotBlank
    private String identityCardFront;
    @NotBlank
    private String identityCardBack;
    @NotBlank
    private String socialCreditCode;

    private String coverImage;

    // private Integer auditStatus;

    // private LocalDateTime createAt;
    //
    // private LocalDateTime updateAt;

    // private Integer version;

    // private Long userId;

}
