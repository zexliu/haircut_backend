package com.zex.cloud.haircut.response;

import com.zex.cloud.haircut.params.OmOrderServiceParam;
import lombok.Data;

import java.util.List;

@Data
public class ShopOrderBody {

    List<OmOrderServiceParam> services;

    Long couponId;
}
