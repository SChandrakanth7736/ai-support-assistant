# Backend

Java 17 / Spring Boot REST backend using Maven and MongoDB.

## Configure MongoDB

Install and start a local MongoDB Community Server. The default connection is:

```text
mongodb://localhost:27017/ai_support_assistant
```

For a local MongoDB installation on Linux, one simple development setup is:

```sh
mkdir -p "$HOME/data/mongodb"
mongod --dbpath "$HOME/data/mongodb" --bind_ip 127.0.0.1 --port 27017
```

Keep that terminal running while using the backend. If MongoDB is installed as a Windows service, start the `MongoDB` service from an elevated PowerShell terminal instead.

Override the connection string with `MONGODB_URI` if MongoDB runs elsewhere. For example:

```sh
export MONGODB_URI='mongodb://localhost:27017/ai_support_assistant'
```

In PowerShell, the equivalent for the current terminal is:

```powershell
$env:MONGODB_URI = "mongodb://localhost:27017/ai_support_assistant"
```

The database is created by MongoDB when the first document is written. Spring Data creates the declared indexes at application startup.

## Run and test

With MongoDB running, from this directory:

```sh
mvn test
mvn spring-boot:run
```

The server listens on port `8080` by default. Override it with `SERVER_PORT`.

## API

- `GET /api/health` returns `{"status":"UP"}`.
- `GET /api/questions` returns an empty JSON array; question persistence is outside this stage.
- `POST /api/users` creates a user from `{"email":"learner@example.com"}`.
- `GET /api/users/{userId}` retrieves a user.
- `POST /api/conversations` creates a conversation from `{"userId":"<userId>","title":"MongoDB basics"}`.
- `GET /api/conversations/{conversationId}` retrieves one conversation.
- `GET /api/conversations?userId=<userId>&page=0&size=20` lists a user's conversations, newest activity first.
- `POST /api/conversations/{conversationId}/messages` adds a message from `{"role":"USER","content":"How does this work?"}`.
- `GET /api/conversations/{conversationId}/messages/{messageId}` retrieves one message.
- `GET /api/conversations/{conversationId}/messages?page=0&size=50` lists messages in chronological order.

Page numbers start at `0`; page size must be from `1` to `100`. Request validation errors return HTTP 400, missing users/conversations return 404, duplicate emails return 409, and database availability failures return 503.

Example health check:

```sh
curl -i http://localhost:8080/api/health
```

These user endpoints are for the data-model learning stage only. They do not implement authentication or authorization.

## Structure

- `src/main/java/com/example/aisupportassistant/model/` — MongoDB document records and message role.
- `repository/` — Spring Data MongoDB repositories and paginated queries.
- `service/` — persistence and validation-aware business operations.
- `controller/` — REST endpoint mappings.
- `dto/` — request validation and stable API response types.
- `exception/` — consistent API errors and database failure handling.
- `src/main/resources/application.properties` — MongoDB URI, index creation, and server port.
- `src/test/java/` — Stage 1 and Stage 2 endpoint tests.

See the [master project guide](../AI_Support_Assistant_ServiceNow_Interview_Project_Guide.md) for the staged learning plan.
