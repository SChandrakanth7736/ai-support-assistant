# AI Support Assistant

A staged full-stack learning project for building an AI-powered technical support assistant. The project follows the [master project guide](AI_Support_Assistant_ServiceNow_Interview_Project_Guide.md), which is the source of truth for its scope and learning sequence.

## Project layout

- `backend/` — Java and Spring Boot backend workspace.
- `frontend/` — Lit and Web Components frontend workspace.
- `docs/` — Project notes and the current implementation status.
- `AI_Support_Assistant_ServiceNow_Interview_Project_Guide.md` — Master learning and project guide.

The backend implements the Stage 1 Spring Boot skeleton and Stage 2 MongoDB persistence. The frontend remains directory scaffolding only.

## Current status

Stages 1 and 2 are implemented. See [Project Context](docs/PROJECT_CONTEXT.md) for the application status and next milestone.

## Continuous integration

[Backend CI](.github/workflows/backend-ci.yml) runs on pushes to any branch and when pull requests are opened, updated with new commits, or reopened. It checks out the repository, installs Temurin Java 17 (matching `backend/pom.xml`), caches Maven dependencies, and runs:

```sh
cd backend
mvn --batch-mode --no-transfer-progress clean verify
```

This command compiles the backend, runs the existing tests, packages the application, and completes Maven's verification lifecycle. Compilation, test, or verification failures fail the job. The current tests mock persistence operations and disable automatic MongoDB index creation, so CI does not need a MongoDB server. These checks do not replace real MongoDB persistence integration testing.

After pushing, open the repository's **Actions** tab, select **Backend CI**, and open a run to see each step's logs. Pull requests also show the result under **Checks**. GitHub Actions must be enabled in the repository settings; no secrets are required for this workflow.

This is build-and-test CI only: it does not deploy the application, start MongoDB, or build a frontend or containers. Deployment remains a future stage.

## Learning workflow

Work through one stage at a time. Understand a design before generating code, inspect generated code and configuration, test expected and failure behavior, and make a small change yourself before moving on. Never put secrets, production credentials, customer data, or confidential company material in the project or in an external AI tool.
