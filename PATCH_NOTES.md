# Patch Notes: Config Server Edition

This patch supersedes the earlier GitHub CI patch.

## Added

- `config-server` Spring Boot module using Spring Cloud Config Server.
- Spring Cloud 2025.1.2 BOM, compatible with Spring Boot 4.1.0.
- `config-repository/` with common, local, and OpenShift profile files.
- Config Client integration in order-service and notification-service.
- Native backend for local learning and Git backend profile for the GitHub exercise.
- Actuator/web support for notification-service health checks.
- CI build matrix entry for Config Server.
- Config Server learning guide and updated startup instructions.

## Configuration ownership

The service-local `application.yml` files now contain only bootstrap information needed to find Config Server. Database, Kafka, ports, topic, listener, and actuator settings are served centrally.

## XAMPP

The local order-service profile still targets XAMPP MySQL/MariaDB at `localhost:3306`, database `order_db`, user `root`, and an empty password by default.

## Redis

Redis is not added. It is unrelated to centralized configuration and is best introduced later for a specific caching, idempotency, or rate-limiting exercise.

## Config Client test bootstrap fix

- Updated `OrderServiceApplicationTests` to disable Spring Cloud Config using inline test properties.
- Updated `NotificationServiceApplicationTests` to disable Spring Cloud Config and Kafka listener startup using inline test properties.
- This prevents `mvn clean verify` and GitHub Actions from requiring a running Config Server or Kafka cluster.
- The existing `application-test.yml` files remain responsible for H2 and other test-only settings.

## Notification-service test property fix

- Added a dedicated test consumer group ID: `notification-service-test-group`.
- Added a dedicated test topic name: `order-created-test`.
- Supplies both values through `application-test.yml` and inline test properties so the `@KafkaListener` placeholders can be resolved while Config Server is disabled.
- Kafka listeners remain disabled during the context test, so no running Kafka broker is required.
