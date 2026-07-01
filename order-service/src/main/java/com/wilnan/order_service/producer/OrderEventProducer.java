package com.wilnan.order_service.producer;

import com.wilnan.order_service.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventProducer {

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    @Value("${app.kafka.topics.order-created}")
    private String orderCreatedTopic;

    public void publishOrderCreatedEvent(OrderCreatedEvent event) {
        String messageKey = event.getOrderId().toString();

        kafkaTemplate
                .send(orderCreatedTopic, messageKey, event)
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        log.error(
                                "Failed to publish OrderCreatedEvent for order ID {}",
                                event.getOrderId(),
                                exception
                        );

                        return;
                    }

                    log.info(
                            "OrderCreatedEvent published: orderId={}, topic={}, partition={}, offset={}",
                            event.getOrderId(),
                            result.getRecordMetadata().topic(),
                            result.getRecordMetadata().partition(),
                            result.getRecordMetadata().offset()
                    );
                });
    }
}
