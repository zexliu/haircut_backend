package com.zex.cloud.haircut.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zex.cloud.haircut.config.JsonListLongSerializer;
import com.zex.cloud.haircut.entity.SmShop;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class SmShopVO extends SmShop {

    @JsonSerialize(using = JsonListLongSerializer.class)
    List<Long> serviceIds;
    @JsonSerialize(using = JsonListLongSerializer.class)
    List<Long> safetyIds;



}
