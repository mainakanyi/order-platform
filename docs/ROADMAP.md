# Learning Roadmap

## Phase 1: Centralized configuration and CI

- Run Config Server with the native file backend.
- Verify profile-specific configuration for both services.
- Build all three modules in GitHub Actions.
- Switch Config Server to the Git backend after the repository is pushed.

## Phase 2: Containers

- Add Dockerfiles for Config Server, order-service, and notification-service.
- Add the applications to Docker Compose.
- Use container DNS names instead of localhost.

## Phase 3: ELK

- Produce ECS-compatible structured JSON logs.
- Add correlation IDs and Kafka message metadata.
- Ship logs to Elasticsearch and inspect them in Kibana.

## Phase 4: OpenShift

- Build and push container images.
- Create Deployments, Services, Routes, health probes, ConfigMaps, and Secrets.
- Run clients with the `openshift` profile.
- Add a controlled GitHub Actions deployment workflow.

## Redis decision

Redis remains optional. Introduce it only for a concrete use case such as order lookup caching, distributed idempotency keys, or rate limiting. Kafka remains responsible for event delivery.
