# Project Context

## Source of truth

The project scope, architecture, learning sequence, and acceptance criteria are defined in the root file `AI_Support_Assistant_ServiceNow_Interview_Project_Guide.md`. This document records the implementation status; it does not replace or change the master guide.

## Project summary

The AI Support Assistant is a staged full-stack learning project. The planned application uses a Lit-based frontend, a Java/Spring Boot REST backend, MongoDB persistence, and an AI integration layer. Later stages introduce conversation history, retrieval-augmented generation, security, testing, Docker, CI/CD, performance work, and server-side rendering.

## Current status

- **Stage:** 0 — Project Setup and Engineering Mindset.
- **Completed:** Initial repository documentation, project context, ignore rules, and empty backend/frontend source directory scaffolding.
- **Backend:** Directory structure exists; no application code or build configuration has been added.
- **Frontend:** Directory structure exists; no application code or build configuration has been added.
- **Verification:** Check the directory tree and confirm that the master guide files have not changed.
- **Not started:** Stage 1 and all later stages, including the Spring Boot application, frontend application, database, and AI integration.

## Stage 0 working agreements

- Follow the stages in the master guide and stop at the current stage's scope.
- Explain design choices before generating implementation code.
- Review every generated file, dependency, and configuration value.
- Test normal behavior and relevant failure cases as features are introduced.
- Use only fake or local credentials; never expose secrets or confidential information to AI tools.

## Next milestone

Review the Stage 0 structure, then begin Stage 1 only when approved. Stage 1 will establish the Spring Boot backend skeleton and health endpoint.
