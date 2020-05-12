package com.zex.cloud.haircut.params;

import com.zex.cloud.haircut.enums.ShopWorkStatus;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.List;

@Data
public class SmShopParam {

    @NotNull
    private String name;
    @NotNull
    private Long userId;

    private String logo;

    private String coverImage;

    private String introduction;
    @NotNull
    private String address;
    @NotNull
    private Integer provinceCode;
    @NotNull
    private Integer cityCode;
    @NotNull
    private Integer districtCode;

    private String businessLicense;
    @NotNull
    private String leaderName;
    @NotNull
    private String leaderMobile;

    private Boolean enable;
    @NotNull
    private Double longitude;
    @NotNull
    private Double latitude;

    private String htmlInfo;

    private LocalTime workStartAt;

    private LocalTime workEndAt;

    private ShopWorkStatus workStatus;
    private String identityCardFront;
    private String identityCardBack;

}
