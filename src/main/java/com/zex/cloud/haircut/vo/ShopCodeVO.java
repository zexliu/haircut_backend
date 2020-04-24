package com.zex.cloud.haircut.vo;

import com.zex.cloud.haircut.entity.SmShop;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.annotation.security.DenyAll;

@Data
public class ShopCodeVO{

    private String qrCodeUrl;

    private String name;

    private String address;

    private String mobile;
}
