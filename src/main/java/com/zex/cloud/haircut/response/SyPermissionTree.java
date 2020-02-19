package com.zex.cloud.haircut.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SyPermissionTree extends AbstractTree<SyPermissionTree>{
   private String name;
    private boolean module;

}
