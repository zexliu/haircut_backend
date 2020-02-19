package com.zex.cloud.haircut.params;

import com.zex.cloud.haircut.enums.PermissionMethodType;
import lombok.Data;

@Data
public class SyPermissionParam {

    private String name;

    private String url;

    private PermissionMethodType methodType;

    private Integer seq;

    private String remark;

    private Long moduleId;

}
