package com.wilnan.order_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;

@Profile("!test")
@Configuration
public class KafkaTopicConfig {

    @Value("${app.kafka.topics.order-created}")
    private String orderCreatedTopic;

    @Value("${app.kafka.topic-settings.order-created.partitions}")
    private int orderCreatedPartitions;

    @Value("${app.kafka.topic-settings.order-created.replicas}")
    private int orderCreatedReplicas;

    @Bean
    public NewTopic orderCreatedTopic() {
        return TopicBuilder
                .name(orderCreatedTopic)
                .partitions(orderCreatedPartitions)
                .replicas(orderCreatedReplicas)
                .build();
    }
}
