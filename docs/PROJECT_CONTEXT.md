# Project Context

## Source of truth

The project scope, architecture, learning sequence, and acceptance criteria are defined in the root file `AI_Support_Assistant_ServiceNow_Interview_Project_Guide.md`. This document records the implementation status; it does not replace or change the master guide.

## Project summary

The AI Support Assistant is a staged full-stack learning project. The planned application uses a Lit-based frontend, a Java/Spring Boot REST backend, MongoDB persistence, and an AI integration layer. Later stages introduce conversation history, retrieval-augmented generation, security, testing, Docker, CI/CD, performance work, and server-side rendering.

## Current status

- **Stage:** 2 — Data Model + MongoDB.
- **Completed:** Stage 0 foundation; Stage 1 health/questions endpoints; Stage 2 MongoDB documents for users, conversations, and messages; Spring Data repositories; user/conversation/message APIs; validation, error handling, query indexes, pagination, and endpoint tests.
- **Backend:** Java 17 / Spring Boot application under `backend/`; MongoDB URI is configured by `MONGODB_URI` and defaults to `mongodb://localhost:27017/ai_support_assistant`. `GET /api/health` responds with `{"status":"UP"}` and `GET /api/questions` remains an empty array.
- **Frontend:** Directory structure exists; no application code or build configuration has been added.
- **Relationships:** `Conversation.userId` references `User._id`; `Message.conversationId` references `Conversation._id`. References are stored as MongoDB ObjectIds. No authentication or `DBRef` relationship management is implemented.
- **Verification:** `mvn test` passes all 14 Stage 1/2 tests. Stage 2 HTTP tests use mocked repositories; an additional live smoke test against a temporary MongoDB 8.0.12 instance verified user/conversation/message writes and reads, pagination, duplicate-email conflict handling, and invalid-ID validation. No temporary MongoDB files were kept in the repository.
- **Not started:** Stage 3 and later stages, including frontend application, AI integration, RAG, authentication, Docker, CI/CD, performance/scaling, and SSR.

## Stage 0 working agreements

- Follow the stages in the master guide and stop at the current stage's scope.
- Explain design choices before generating implementation code.
- Review every generated file, dependency, and configuration value.
- Test normal behavior and relevant failure cases as features are introduced.
- Use only fake or local credentials; never expose secrets or confidential information to AI tools.

## Next milestone

Review the Stage 2 MongoDB persistence layer. Do not begin Stage 3 until approved.
