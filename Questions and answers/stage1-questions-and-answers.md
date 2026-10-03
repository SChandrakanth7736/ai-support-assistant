# Stage 1 — Questions and Answers

## 1. What are Gradle and Maven? What is the difference?

**Maven** and **Gradle** are build automation tools. For a Java project, they help you:

- Download and manage libraries (dependencies).
- Compile Java source code.
- Run tests.
- Package the application, for example as a JAR file.
- Run repeatable build steps locally and in CI.

### Maven

Maven describes a project and its build in an XML file named `pom.xml`. It follows standard conventions, so many Java projects have a familiar layout and predictable commands.

Example Maven dependency:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

Common commands:

```sh
mvn test
mvn package
mvn spring-boot:run
```

### Gradle

Gradle describes a project and its build in a build script, usually `build.gradle` or `build.gradle.kts`. It supports flexible, programmable build logic and can be useful for complex or highly customized builds.

Example Gradle dependency using the Groovy DSL:

```groovy
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
}
```

Common commands:

```sh
./gradlew test
./gradlew build
./gradlew bootRun
```

The `./gradlew` command uses the project's Gradle Wrapper, which selects the Gradle version configured for that project. Maven projects can also include a Maven Wrapper, commonly run as `./mvnw`.

### Main differences

| Area | Maven | Gradle |
| --- | --- | --- |
| Build file | `pom.xml` (XML) | `build.gradle` (Groovy) or `build.gradle.kts` (Kotlin) |
| Style | Convention-driven and declarative | Flexible build scripts with programmable logic |
| Typical strength | Predictable structure and broad familiarity | Flexible customization and potentially faster incremental builds |
| Common commands | `mvn test`, `mvn package` | `./gradlew test`, `./gradlew build` |
| Build tool version | Can use an installed Maven or Maven Wrapper | Often uses the Gradle Wrapper |

Both tools can build Java and Spring Boot applications, manage dependencies, and run tests. Neither is universally better; the right choice depends on the project, team familiarity, and build needs.

### Example in this project

The AI Support Assistant backend currently uses Maven, defined by `backend/pom.xml`. From the `backend/` directory:

```sh
mvn test
mvn spring-boot:run
```

If this project used Gradle instead, it would have a Gradle build file and wrapper, and its equivalent commands would typically be:

```sh
./gradlew test
./gradlew bootRun
```

Usually, a project chooses one build tool rather than maintaining both Maven and Gradle build definitions.

### In one sentence

**Maven** is a convention-based Java build tool configured mainly through XML; 
**Gradle** is a flexible build tool configured through Groovy or Kotlin scripts. Both manage dependencies, run tests, and build applications.

## 2. How does Maven work in this project?

Maven reads `backend/pom.xml` to learn the project's identity, Java version, dependencies, and build plugins. When you run a Maven command, Maven uses that information to perform the requested build task.

### What happens when you run the backend?

From the `backend/` directory, run:

```sh
mvn spring-boot:run
```

At a high level:

1. Maven reads `pom.xml`, including the Spring Boot parent and the web and test dependencies.
2. Maven downloads any dependencies that are not already in its local cache.
3. The Spring Boot Maven plugin compiles the Java application and starts it.
4. Spring Boot starts an embedded web server on port `8080` by default.
5. Spring discovers the application class and its controller and service classes, then wires the services into the controller using constructor injection.
6. The application waits for HTTP requests.

Maven is responsible for building and launching the app; the running Spring Boot application handles web requests.

### What happens for a health request?

Send this request while the app is running:

```sh
curl http://localhost:8080/api/health
```

The request flows through the application like this:

```text
curl/browser
  -> embedded web server
  -> ApiController.getHealth()
  -> HealthService.getHealth()
  -> HealthResponse("UP")
  -> JSON response: {"status":"UP"}
```

Spring MVC maps `GET /api/health` to the controller method because it is annotated with `@GetMapping("/health")` inside a controller mapped to `/api`. The returned Java record is serialized to JSON by Spring's web support.

### What happens when you run the tests?

From `backend/`, run:

```sh
mvn test
```

Maven compiles the main and test source, then runs the tests using the test dependencies in `pom.xml`. The Stage 1 `ApiControllerTest` uses MockMvc to send simulated HTTP requests through Spring's MVC layer and checks the returned status and JSON. It does not require a real browser or start a separate server.

### Where Gradle fits

Gradle performs the same broad build tasks using its Gradle build file and tasks. In a Gradle version of this project, `./gradlew bootRun` would start the app and `./gradlew test` would run tests. This project uses Maven, so use the Maven commands above rather than running both build systems.
