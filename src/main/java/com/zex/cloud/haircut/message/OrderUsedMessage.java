package com.zex.cloud.haircut.message;

import lombok.Data;

@Data
public class OrderUsedMessage {
    private Long orderId;
    private Long userId;
}
