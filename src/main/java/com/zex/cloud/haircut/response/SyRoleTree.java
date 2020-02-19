package com.zex.cloud.haircut.response;

import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class SyRoleTree extends AbstractTree<SyRoleTree> {
    private String name;
}
