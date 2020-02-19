package com.zex.cloud.haircut.params;

import lombok.Data;

import java.util.List;

@Data
public class SyUserGroupParam {

    private String name;

    private String remark;

    private Integer seq;
    private List<Long> roleIds;
}
