package com.zex.cloud.haircut.params;

import com.zex.cloud.haircut.enums.ShopTitleType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ShopTitleParam extends BaseTitleParam {

    ShopTitleType type;
}
