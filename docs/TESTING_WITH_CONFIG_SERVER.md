# Testing applications that use Spring Cloud Config

The normal runtime applications retrieve configuration from Config Server.
However, unit and context tests must be able to run without manually starting Config Server.

Both service context tests therefore use inline Spring Boot test properties:

```java
@SpringBootTest(properties = {
        "spring.cloud.config.enabled=false",
        "spring.config.import=optional:configserver:http://localhost:8888"
})
```

The notification-service test additionally prevents Kafka listeners from starting:

```java
"spring.kafka.listener.auto-startup=false"
```

The inline properties are intentional. They are available early enough to affect Spring's
Config Data import phase, unlike relying only on profile-specific configuration loaded later.

Run all tests from IntelliJ's Maven tool window or with Git Bash:

```bash
mvn clean verify
```

If the Maven Wrapper can download its configured Maven distribution, you can also use:

```bash
./mvnw clean verify
```

## Kafka listener placeholders during tests

The notification consumer declares its topic and group ID with placeholders in `@KafkaListener`.
Even when listener startup is disabled, Spring must still resolve those placeholders while creating the bean.
The test therefore supplies these values explicitly:

```yaml
spring:
  kafka:
    consumer:
      group-id: notification-service-test-group

app:
  kafka:
    topics:
      order-created: order-created-test
```

No Kafka connection is made because `spring.kafka.listener.auto-startup=false`.
