package com.zex.cloud.haircut.params;
import lombok.Data;

@Data
public class SmShopApplyParam {


    private String name;

    private String address;

    private Integer provinceCode;

    private Integer cityCode;

    private Integer districtCode;

    private String businessLicense;

    private String photo;

    private String leaderName;

    private String leaderMobile;

    private Double longitude;

    private Double latitude;

    // private Integer auditStatus;

    // private LocalDateTime createAt;
    //
    // private LocalDateTime updateAt;

    // private Integer version;

    // private Long userId;

}
