# Spring Cloud Config Server Learning Guide

## Why it is included

`config-server` gives the two services one central source for environment-specific settings. The clients keep only their application name, active profile, and Config Server import locally.

The local learning setup uses the Config Server **native** backend and reads files from `config-repository/`. A later exercise switches the same server to the **Git** backend so configuration is read from GitHub.

The client import is optional in the local developer profile so Maven context tests and IDE imports do not depend on a running Config Server. In OpenShift, set `SPRING_CONFIG_IMPORT=configserver:http://config-server:8888` without the `optional:` prefix so deployment fails when centralized configuration is unavailable.

## Configuration resolution

When `order-service` starts with the `local` profile, Config Server combines:

1. `config-repository/application.yml`
2. `config-repository/order-service.yml`
3. `config-repository/order-service-local.yml`

For OpenShift, use the `openshift` profile and the third file becomes `order-service-openshift.yml`.

## Local startup order

From the repository root:

```powershell
# Terminal 1: infrastructure
 docker compose -f infrastructure/docker-compose.yml up -d

# Terminal 2: Config Server
.\mvnw.cmd -pl config-server spring-boot:run

# Terminal 3: order producer
.\mvnw.cmd -pl order-service spring-boot:run

# Terminal 4: notification consumer
.\mvnw.cmd -pl notification-service spring-boot:run
```

## Verify Config Server

Open these URLs:

```text
http://localhost:8888/actuator/health
http://localhost:8888/order-service/local
http://localhost:8888/notification-service/local
```

The second and third endpoints should return the merged property sources used by each client.

## Switch to a Git backend

After pushing the repository to GitHub, run Config Server with:

```powershell
$env:SPRING_PROFILES_ACTIVE="git"
$env:CONFIG_GIT_URI="https://github.com/YOUR_USERNAME/order-platform.git"
$env:CONFIG_GIT_DEFAULT_LABEL="main"
$env:CONFIG_GIT_SEARCH_PATHS="config-repository"
.\mvnw.cmd -pl config-server spring-boot:run
```

For a private repository, do not commit credentials. Supply authentication through environment variables, Git credential configuration, or an OpenShift Secret later.

## Important secret rule

Config Server centralizes configuration, but a normal Git repository is not a secret manager. Keep real database passwords, tokens, and private keys in environment variables or OpenShift Secrets. Later, Vault can be introduced if there is a real requirement.
