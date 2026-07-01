# Configuration repository

This directory is the local learning backend for Spring Cloud Config Server.

- `application.yml` contains settings shared by every client.
- `order-service.yml` contains settings loaded when `spring.application.name=order-service`.
- `notification-service.yml` contains settings loaded when `spring.application.name=notification-service`.

Do not commit real passwords, tokens, private keys, or production connection strings. Keep secrets in standard Spring Boot environment variables locally and later in OpenShift Secrets or a dedicated secret manager.

After the native setup works, create a separate GitHub repository for these files and start Config Server with:

```powershell
$env:CONFIG_SERVER_PROFILE="git"
$env:CONFIG_GIT_URI="https://github.com/<your-user>/order-platform-config.git"
.\mvnw.cmd -pl config-server spring-boot:run
```
