package com.wilnan.order_service.event;

import com.wilnan.order_service.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {

    private Long orderId;

    private String customerName;

    private String item;

    private BigDecimal amount;

    private OrderStatus status;

    private LocalDateTime createdAt;
}
