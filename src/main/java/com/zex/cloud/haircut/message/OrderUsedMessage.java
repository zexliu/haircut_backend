package com.zex.cloud.haircut.message;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderUsedMessage implements Serializable {
    private Long orderId;
    private Long userId;
}
