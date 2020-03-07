package com.zex.cloud.haircut.params;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class HmServiceParam extends BaseTitleParam {

    Boolean groupStatus;
}
