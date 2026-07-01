# Apply This Patch

This ZIP is a complete repository and supersedes the earlier patch.

1. Back up your current folders.
2. Extract this archive.
3. Open the extracted `order-platform` root folder in IntelliJ.
4. Import the root `pom.xml` as a Maven project.
5. Start XAMPP MySQL and create `order_db`.
6. Start Kafka with Docker Compose.
7. Run `config-server` before the two client services.
8. Run `mvnw.cmd clean verify` before pushing to GitHub.

Do not copy only the new Config Server folder into the old patch; the root and module POM files, client bootstrap configuration, tests, workflow, and documentation all changed together.
