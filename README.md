# Order Platform Learning Project

This repository contains three Spring Boot services/components:

- `config-server`: centrally serves configuration for each application and environment.
- `order-service`: stores orders in XAMPP MySQL/MariaDB and publishes `order-created` events.
- `notification-service`: consumes `order-created` events.

Kafka runs through Docker Compose. The first milestone is a green GitHub Actions workflow for all three Maven modules.

## Repository structure

```text
order-platform/
├── .github/workflows/ci.yml
├── .mvn/wrapper/
├── config-repository/
├── config-server/
├── docs/
├── infrastructure/docker-compose.yml
├── notification-service/
├── order-service/
├── .env.example
├── mvnw
├── mvnw.cmd
└── pom.xml
```

## 1. Start Kafka

```bash
docker compose -f infrastructure/docker-compose.yml up -d
```

Kafka UI: `http://localhost:8080`

## 2. Prepare XAMPP MySQL/MariaDB

Start MySQL in XAMPP and create the database:

```sql
CREATE DATABASE order_db;
```

The `local` profile defaults are stored in `config-repository/order-service-local.yml`:

```text
jdbc:mysql://localhost:3306/order_db
username: root
password: empty
```

## 3. Build all modules

```powershell
.\mvnw.cmd clean verify
```

## 4. Start Config Server first

```powershell
.\mvnw.cmd -pl config-server spring-boot:run
```

Verify:

```text
http://localhost:8888/actuator/health
http://localhost:8888/order-service/local
http://localhost:8888/notification-service/local
```

## 5. Start the applications

Order service:

```powershell
.\mvnw.cmd -pl order-service spring-boot:run
```

Notification service:

```powershell
.\mvnw.cmd -pl notification-service spring-boot:run
```

Endpoints:

```text
Order API: http://localhost:8081
Order health: http://localhost:8081/actuator/health
Notification health: http://localhost:8082/actuator/health
```

## GitHub Actions milestone

Push the repository and open **Actions**. `Java CI` runs separate builds for `config-server`, `order-service`, and `notification-service`.

## Learning roadmap

1. Config Server locally with the native repository.
2. GitHub Actions build and tests.
3. Switch Config Server to the GitHub-backed repository.
4. Dockerfiles and container image workflow.
5. Structured JSON logs and ELK.
6. OpenShift manifests, Secrets, Config Server profile, and deployment workflow.
7. Optional Redis only after selecting a measured caching or idempotency use case.

See `docs/CONFIG_SERVER_GUIDE.md` for the walkthrough.
