package com.zex.cloud.haircut.vo;

import com.zex.cloud.haircut.entity.SyUserExtension;
import com.zex.cloud.haircut.enums.GenderType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SyUserExtensionVO extends SyUserExtension {

    private GenderType gender;
}
