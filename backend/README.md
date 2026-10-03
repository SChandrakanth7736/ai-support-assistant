# Backend

This is the Java 17 / Spring Boot backend for the AI Support Assistant. It uses Maven.

## Run locally

From this directory, run:

```sh
mvn spring-boot:run
```

The server listens on port `8080` by default. Set `SERVER_PORT` to override it.

## Endpoints

- `GET /api/health` returns `{"status":"UP"}`.
- `GET /api/questions` returns an empty JSON array until question data is introduced in a later stage.

Run the tests with:

```sh
mvn test
```

## Structure

- `src/main/java/com/example/aisupportassistant/` — Spring Boot application.
- `controller/` — HTTP endpoint mapping.
- `service/` — health and question application logic.
- `dto/` — JSON response types.
- `src/main/resources/application.properties` — application name and configurable server port.
- `src/test/java/` — endpoint integration tests.

There is no repository implementation in Stage 1 because there is no data source yet. Database access belongs to Stage 2.

See the [master project guide](../AI_Support_Assistant_ServiceNow_Interview_Project_Guide.md) for the complete staged plan.
