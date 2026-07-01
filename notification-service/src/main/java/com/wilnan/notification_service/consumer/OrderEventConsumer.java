package com.wilnan.notification_service.consumer;

import com.wilnan.notification_service.event.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderEventConsumer {

    @KafkaListener(
            topics = "${app.kafka.topics.order-created}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeOrderCreatedEvent(OrderCreatedEvent event) {

        log.info(
                "Order-created event received: orderId={}, customerName={}, item={}, amount={}, status={}, createdAt={}",
                event.getOrderId(),
                event.getCustomerName(),
                event.getItem(),
                event.getAmount(),
                event.getStatus(),
                event.getCreatedAt()
        );

        log.info(
                "Notification: Hello {}, your order for {} has been received.",
                event.getCustomerName(),
                event.getItem()
        );
    }
}
