package com.zex.cloud.haircut.params;

import lombok.Data;

import java.util.List;


@Data
public class SyRoleParam {

    private String name;

    private Long parentId;

    private String remark;

    private Integer seq;

    List<Long> permissionIds;
}
