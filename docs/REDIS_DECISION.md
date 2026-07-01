# Redis decision

## Decision for the current milestone

Do not add Redis yet.

Kafka is responsible for transporting order events from the producer to the notification consumer. MySQL remains the system of record for orders. Redis is not needed to make GitHub Actions, ELK, or OpenShift work.

## Good future use cases

Add Redis only when the application has a clear requirement such as:

1. Cache frequently requested order data to reduce repeated MySQL reads.
2. Store short-lived idempotency keys to prevent duplicate order submissions.
3. Apply API rate limiting.
4. Store short-lived distributed locks when multiple replicas must coordinate a critical operation.

## Recommended first Redis exercise

After the services are deployed and observable, cache `GET /orders/{id}` in `order-service` with a short TTL and evict the cache when the order is updated or deleted. Measure the database-query reduction before and after enabling the cache.

## What Redis should not replace here

- It should not replace MySQL as the order system of record.
- It should not replace Kafka for durable order-created event delivery.
- Redis Pub/Sub should not be introduced as a second event bus for the same notification flow.
