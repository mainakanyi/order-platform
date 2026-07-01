package com.wilnan.order_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(properties = {
        "spring.cloud.config.enabled=false",
        "spring.config.import=optional:configserver:http://localhost:8888"
})
class OrderServiceApplicationTests {

    @Test
    void contextLoads() {
    }
}
