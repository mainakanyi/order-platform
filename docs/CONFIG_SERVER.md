# Spring Cloud Config learning guide

## What Config Server does in this project

The two business services keep only two bootstrap settings locally:

```yaml
spring:
  application:
    name: order-service
  config:
    import: "configserver:http://localhost:8888"
```

The application name tells Config Server which file to load. The order service receives settings from:

- `config-repo/application.yml`
- `config-repo/order-service.yml`

The notification service receives settings from:

- `config-repo/application.yml`
- `config-repo/notification-service.yml`

## Phase 1: native filesystem backend

The default Config Server profile is `native`. It reads the included `config-repo` directory. Start it from the repository root:

```powershell
.\mvnw.cmd -pl config-server spring-boot:run
```

Inspect the resolved configuration:

```text
http://localhost:8888/order-service/default
http://localhost:8888/notification-service/default
```

Change a harmless value such as `server.port`, restart the affected client, and observe that the service receives the new value from Config Server.

## Phase 2: Git-backed repository

Create a separate GitHub repository, for example `order-platform-config`, and push only the files currently inside `config-repo`.

Start Config Server with:

```powershell
$env:CONFIG_SERVER_PROFILE="git"
$env:CONFIG_GIT_URI="https://github.com/<your-user>/order-platform-config.git"
$env:CONFIG_GIT_DEFAULT_LABEL="main"
.\mvnw.cmd -pl config-server spring-boot:run
```

Config Server will clone and read that repository. This allows configuration changes to have their own Git history and pull-request workflow.

## Secrets rule

Do not put real database passwords, API keys, access tokens, or private keys into the configuration repository. Local values should come from environment variables. On OpenShift, use Secrets or a dedicated secret manager. Config Server should mainly manage non-secret application configuration.

## Refresh behavior

For the first milestone, restart a client after changing its configuration. Later, add refresh support intentionally using Actuator and, when multiple instances exist, Spring Cloud Bus. Avoid adding dynamic refresh before the basic configuration flow is understood.
