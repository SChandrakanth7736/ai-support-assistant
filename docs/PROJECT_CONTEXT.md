# Project Context

## Source of truth

The project scope, architecture, learning sequence, and acceptance criteria are defined in the root file `AI_Support_Assistant_ServiceNow_Interview_Project_Guide.md`. This document records the implementation status; it does not replace or change the master guide.

## Project summary

The AI Support Assistant is a staged full-stack learning project. The planned application uses a Lit-based frontend, a Java/Spring Boot REST backend, MongoDB persistence, and an AI integration layer. Later stages introduce conversation history, retrieval-augmented generation, security, testing, Docker, CI/CD, performance work, and server-side rendering.

## Current status

- **Stage:** 3 — JavaScript Fundamentals + Lit Frontend.
- **Completed:** Stage 0 foundation; Stage 1 health/questions endpoints; Stage 2 MongoDB persistence APIs; Stage 3 reusable Lit frontend with sample data and backend connectivity.
- **Backend:** Java 17 / Spring Boot application under `backend/`; MongoDB URI is configured by `MONGODB_URI` and defaults to `mongodb://localhost:27017/ai_support_assistant`. `GET /api/health` responds with `{"status":"UP"}` and `GET /api/questions` remains an empty array.
- **Frontend:** Lit components with local demo conversations, input validation/custom events, loading/error states, and a backend health check via the Vite development proxy. No conversation persistence or AI calls. Demo messages reset on reload.
- **Relationships:** `Conversation.userId` references `User._id`; `Message.conversationId` references `Conversation._id`. References are stored as MongoDB ObjectIds. No authentication or `DBRef` relationship management is implemented.
- **Verification:** `mvn test` passes all 14 Stage 1/2 tests. Stage 2 HTTP tests use mocked repositories; an additional live smoke test against a temporary MongoDB 8.0.12 instance verified user/conversation/message writes and reads, pagination, duplicate-email conflict handling, and invalid-ID validation. No temporary MongoDB files were kept in the repository.
- **Frontend verification:** All 6 Vitest tests and `npm run build` passed. A live check confirmed Vite serves the frontend HTML and proxies `/api/health` to the actual Spring Boot backend with isolated temporary MongoDB data. Test processes were stopped afterward. Visual browser review remains manual; automated component tests use jsdom.
- **CI:** Existing backend-only GitHub Actions workflow builds and tests on pushes and pull requests; it is unchanged by Stage 3.
- **Not started:** Stage 4 conversation UI/API integration and later features, including AI, RAG, authentication, Docker, deployment, performance/scaling, and SSR.

## Stage 0 working agreements

- Follow the stages in the master guide and stop at the current stage's scope.
- Explain design choices before generating implementation code.
- Review every generated file, dependency, and configuration value.
- Test normal behavior and relevant failure cases as features are introduced.
- Use only fake or local credentials; never expose secrets or confidential information to AI tools.

## Next milestone

Review the Stage 3 frontend foundation. Do not begin Stage 4 until approved.
