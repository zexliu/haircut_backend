package com.zex.cloud.haircut.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SyPermissionModuleTree extends AbstractTree<SyPermissionModuleTree> {

    String name;
}
