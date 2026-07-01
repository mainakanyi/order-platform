package com.wilnan.order_service.dto;

import com.wilnan.order_service.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderResponse {

    private Long id;

    private String customerName;

    private String item;

    private BigDecimal amount;

    private OrderStatus status;
}