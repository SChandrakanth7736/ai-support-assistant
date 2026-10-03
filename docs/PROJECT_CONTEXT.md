# Project Context

## Source of truth

The project scope, architecture, learning sequence, and acceptance criteria are defined in the root file `AI_Support_Assistant_ServiceNow_Interview_Project_Guide.md`. This document records the implementation status; it does not replace or change the master guide.

## Project summary

The AI Support Assistant is a staged full-stack learning project. The planned application uses a Lit-based frontend, a Java/Spring Boot REST backend, MongoDB persistence, and an AI integration layer. Later stages introduce conversation history, retrieval-augmented generation, security, testing, Docker, CI/CD, performance work, and server-side rendering.

## Current status

- **Stage:** 1 — Java Fundamentals + Spring Boot Backend Skeleton.
- **Completed:** Stage 0 foundation and Stage 1 Maven/Spring Boot backend skeleton with `GET /api/health`, `GET /api/questions`, application properties, and endpoint integration tests.
- **Backend:** Java 17 / Spring Boot application under `backend/`; `/api/health` responds with `{"status":"UP"}` and `/api/questions` currently responds with an empty array.
- **Frontend:** Directory structure exists; no application code or build configuration has been added.
- **Repository:** No persistence repository is implemented; a database/data-access layer is deferred to Stage 2.
- **Verification:** `mvn test` completed successfully using a temporary Maven 3.9.12 distribution: 2 endpoint integration tests passed, with no failures or errors. Maven is not installed globally in the current environment. Once Maven is available, run tests with `cd backend && mvn test` and start the app with `cd backend && mvn spring-boot:run`.
- **Not started:** Stage 2 and all later stages, including MongoDB, frontend application, AI integration, RAG, authentication, Docker, CI/CD, and SSR.

## Stage 0 working agreements

- Follow the stages in the master guide and stop at the current stage's scope.
- Explain design choices before generating implementation code.
- Review every generated file, dependency, and configuration value.
- Test normal behavior and relevant failure cases as features are introduced.
- Use only fake or local credentials; never expose secrets or confidential information to AI tools.

## Next milestone

Review the Stage 1 backend skeleton. Do not begin Stage 2 until approved.
