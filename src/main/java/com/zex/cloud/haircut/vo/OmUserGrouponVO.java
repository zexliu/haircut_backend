package com.zex.cloud.haircut.vo;

import com.zex.cloud.haircut.entity.OmUserGroupon;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(
        callSuper = true
)
public class OmUserGrouponVO  extends OmUserGroupon {
    String stylistName;
    String shopName;
    String serviceName;
    String username;
}
