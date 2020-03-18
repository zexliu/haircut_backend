package com.zex.cloud.haircut.vo;

import com.zex.cloud.haircut.entity.OmFlower;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OmFlowerVo extends OmFlower {

    private String userNickname;

    private String shopName;
}
