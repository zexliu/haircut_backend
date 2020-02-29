package com.zex.cloud.haircut.dto;

import com.zex.cloud.haircut.enums.OrderCodeType;
import lombok.Data;

@Data
public class OrderCode {
    OrderCodeType type;
    Long id;
}
