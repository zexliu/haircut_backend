package com.zex.cloud.haircut.params;

import com.zex.cloud.haircut.enums.ShopWorkStatus;
import lombok.Data;

import java.time.LocalTime;

@Data
public class SmShopParam {

    private String name;

    private Long userId;

    private String logo;

    private String coverImage;

    private String introduction;

    private String address;

    private String provinceCode;

    private String cityCode;

    private String districtCode;

    private String businessLicense;

    private String leaderName;

    private String leaderMobile;

    private Boolean enable;

    private Double longitude;

    private Double latitude;

    private String htmlInfo;

    private LocalTime workStartAt;

    private LocalTime workEndAt;

    private ShopWorkStatus workStatus;

}
