package com.wilnan.notification_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(properties = {
        "spring.cloud.config.enabled=false",
        "spring.config.import=optional:configserver:http://localhost:8888",
        "spring.kafka.consumer.group-id=notification-service-test-group",
        "app.kafka.topics.order-created=order-created-test",
        "spring.kafka.listener.auto-startup=false"
})
class NotificationServiceApplicationTests {

    @Test
    void contextLoads() {
    }
}
