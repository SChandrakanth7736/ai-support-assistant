AI SUPPORT ASSISTANT

Hands-On Full-Stack + AI Project Learning Guide

Designed around the ServiceNow AI Experience Framework (AIUX) interview requirementsJava • JavaScript • Lit/Web Components • REST • MongoDB • AI • RAG • SSR • Testing • Docker • CI/CD • System Design

Purpose: Build one progressively richer application while learning the concepts behind every component.

# 1. Executive Summary

This guide defines a single practical project that can be built in stages and used as a structured preparation vehicle for a ServiceNow AIUX-style full-stack engineering interview. The application is an AI Support Assistant: a component-based web application where users ask technical questions, receive AI-generated answers, retain conversation history, and eventually receive answers grounded in a small technical knowledge base.

| Core learning principleUse ChatGPT/Copilot to generate boilerplate and implementation code, but do not treat generated code as a black box. For every file, class, function, component, API, dependency, and configuration item, understand what it does, why it exists, what alternatives exist, what can fail, and how you would test it. |
| --- |

## What this project is designed to teach

| Job Requirement | Project Area | Expected Practical Outcome |
| --- | --- | --- |
| Java / OO language | Spring Boot backend | Controllers, services, repositories, OOP, exceptions, collections |
| JavaScript / Web | Lit frontend | ES6+, async/await, events, modules, browser APIs |
| Componentization | Lit Web Components | Reusable components, properties, events, Shadow DOM |
| Modern UI framework | Lit + optional React comparison | Component state, rendering, API integration |
| AI integration | LLM service | Prompting, API integration, streaming, errors, cost/latency |
| Data structures / algorithms | Backend + coding practice | HashMap, lists, queues, search, complexity |
| Design patterns | Backend architecture | Strategy, Factory, Repository, dependency injection |
| Performance / scale | Caching, DB indexes, async work | Measure bottlenecks and explain trade-offs |
| SSR | Lit SSR module | CSR vs SSR, rendering and hydration concepts |
| Testing | JUnit + frontend tests | Unit/integration testing and testable design |
| Tools / Unix | Git, Linux, npm, Maven, debugger | Real development workflow |
| CI/CD | GitHub Actions | Automated build, test and packaging |

# 2. Final Application — What You Are Building

The final application is a technical support assistant. It has a Lit-based frontend, a Java/Spring Boot backend, MongoDB persistence, an AI integration layer, conversation history, a small retrieval-augmented knowledge base, automated tests, Docker support, CI/CD, and a later SSR capability.

User  │  ▼Lit Web Components  │ HTTPS / REST / JSON  ▼Spring Boot REST API  │  ├──────────────► MongoDB  ├──────────────► AI Service / LLM  └──────────────► Knowledge Retrieval                         │                         ▼                    Documents / Vector StoreCI/CD:Git → GitHub Actions → Tests → Build → Docker → Deployment

## 2.1 Core user journeys

User opens the application and sees the chat interface.

User enters a technical question.

Frontend validates the input and calls the backend REST API.

Backend validates the request and sends it through a chat service.

The AI integration layer prepares the request and calls the configured AI provider.

The response is returned to the backend and displayed as a reusable chat-message component.

The conversation and messages are persisted.

User can reopen a previous conversation.

In the advanced version, the system retrieves relevant documents and supplies them to the AI before generating an answer.

## 2.2 Suggested application screens

Login screen (later).

Conversation list/sidebar.

Chat window.

Question input box.

AI response message.

Loading/streaming indicator.

Error message component.

Knowledge-base test screen (optional).

System health/status endpoint.

# Stage 0: Project Setup and Engineering Mindset

Purpose: Create the workspace and define rules that keep AI-generated code understandable and maintainable.

Definition of done: A minimal frontend and backend run locally, the project is committed to Git, and you can explain the structure.

## You will learn

Git repository structure and branching.

Frontend/backend separation.

Maven and npm package management.

Environment variables and configuration.

Basic HTTP request/response flow.

Responsible AI-assisted coding.

## Initial repository structure

ai-support-assistant/├── backend/│   ├── src/main/java/...│   ├── src/main/resources/│   ├── pom.xml│   └── README.md├── frontend/│   ├── src/│   ├── package.json│   └── README.md├── docs/├── docker/├── docker-compose.yml├── .gitignore└── README.md

## AI-assisted coding rule

Ask the AI to explain the design before asking it for a full implementation.

Generate one module at a time.

Read every import, class, method, condition, loop, API call and configuration value.

Ask what happens when each dependency fails and what each function returns.

Make a small manual change after understanding the generated code.

Never paste secrets, API keys, passwords, production credentials or private company code into a public AI tool.

# Stage 1: Java Fundamentals + Spring Boot Backend Skeleton

Purpose: Build the backend first so you understand how a production-style Java application is layered.

Definition of done: Spring Boot starts successfully and exposes a health endpoint.

## Build

Create a Spring Boot project using Maven.

Create the main application class.

Create a REST controller.

Create GET /api/health.

Create GET /api/questions.

Add configuration through properties/YAML.

## Concepts to study

Classes and objects.

Interfaces and implementations.

Encapsulation, abstraction, inheritance and polymorphism.

Dependency injection.

Spring annotations.

Controller vs service vs repository.

HTTP methods, status codes and JSON.

Client  ↓Controller  → HTTP handling  ↓Service     → business logic  ↓Repository  → data access  ↓Database

## Interview checkpoints

Why should business logic not be placed directly in the controller?

What is dependency injection?

Interface vs class?

Difference between 200, 201, 400, 404 and 500?

# Stage 2: Data Model + MongoDB

Purpose: Persist questions and conversations so the application behaves like a real product.

Definition of done: Questions, conversations and messages can be created and retrieved from MongoDB.

## Initial database model

users collection: { _id, email, createdAt }

conversations collection: { _id, userId, title, createdAt, updatedAt }

messages collection: { _id, conversationId, role, content, createdAt }

## Learn

Document modeling, embedding vs. referencing, and ObjectId references.

MongoDB CRUD operations and query filters.

Aggregation pipelines and `$lookup` for joining referenced data.

Indexes on frequently queried fields.

Document-level atomicity and multi-document transactions.

Spring Data MongoDB documents and repositories.

Pagination.

## Practical tasks

Create the MongoDB database.

Create Conversation and Message entities.

Create repositories.

Implement conversation endpoints.

Implement message persistence.

Add an index to a frequently queried field.

Test endpoints with Postman or curl.

| Learning checkpointYou should be able to explain how POST /api/conversations becomes a Java object, how it is validated, how the repository saves it as a MongoDB document, what database operation is performed, and how the response becomes JSON. |
| --- |

# Stage 3: JavaScript Fundamentals + Lit Frontend

Purpose: Learn modern JavaScript and build the first reusable UI instead of a monolithic page.

Definition of done: A Lit application runs in the browser and can call the backend.

## JavaScript topics

let/const/var.

Functions and arrow functions.

Objects and arrays.

Destructuring and spread/rest.

Modules.

Promises.

async/await.

try/catch.

Closures.

Event loop.

fetch and HTTP requests.

## Web Components topics

Custom Elements.

Shadow DOM.

HTML templates.

Properties and attributes.

Custom events.

Component lifecycle.

Composition.

## Lit component plan

frontend/src/├── components/│   ├── ai-chat-app.js│   ├── conversation-list.js│   ├── chat-window.js│   ├── chat-message.js│   ├── question-input.js│   ├── loading-indicator.js│   └── error-message.js├── services/│   └── api-client.js└── main.js

| Component | Responsibility | Concepts |
| --- | --- | --- |
| ai-chat-app | Coordinates the application | Composition, state |
| conversation-list | Shows conversations | Rendering lists, events |
| chat-window | Displays current conversation | Reactive rendering |
| chat-message | Displays one message | Reusable component |
| question-input | Captures input | Events, validation |
| loading-indicator | Shows pending work | UI state |
| error-message | Shows failures | Error handling |
| api-client | Calls backend | fetch, async/await |

# Stage 4: Connect Lit Frontend to Java REST API

Purpose: Connect both halves of the system and learn the complete request lifecycle.

Definition of done: A user can create and retrieve conversations through the UI.

User clicks Send      ↓question-input dispatches event      ↓ai-chat-app receives event      ↓api-client sends POST request      ↓Spring Controller receives JSON      ↓Service validates/processes      ↓Repository persists      ↓JSON response      ↓Lit state updates      ↓chat-message renders

## Learn

REST API design.

Request/response DTOs.

Input validation.

CORS.

HTTP status codes.

Frontend loading/error/success states.

Browser network debugging.

# Stage 5: AI Integration Layer

Purpose: Introduce AI as a backend capability while keeping the provider isolated.

Definition of done: A user can submit a question and receive an AI-generated answer through your backend.

ChatController      ↓ChatService      ↓AIService interface      ↓AIService implementation      ↓External AI API

## Why an interface?

The application should not be tightly coupled to one AI vendor. Define an AIService interface so the rest of the application depends on your own contract. This makes provider replacement and unit testing easier.

## AI concepts

LLMs.

Prompts and system/user messages.

Tokens and context windows.

Generation controls at a conceptual level.

Latency and cost.

Hallucinations and grounding.

Structured outputs.

Tool/function calling.

Streaming.

Prompt injection.

## Implementation

Create an AIService interface.

Create a provider implementation.

Store configuration in environment variables.

Never hard-code API keys.

Create POST /api/chat.

Keep provider-specific structures isolated.

Add timeout/error handling.

Persist user and assistant messages.

# Stage 6: Conversation History + State Management

Purpose: Turn the basic chat into a real conversational application.

Definition of done: Users can create, select, rename and reopen conversations.

Create/list/open/rename/delete conversations.

Store user/assistant/system message roles.

Show timestamps.

Loading and failure states.

Understand frontend state vs persisted data.

Pagination for long histories.

Consistent API error contracts.

# Stage 7: Data Structures, Algorithms + Coding Interview Practice

Purpose: Apply DSA concepts to the project while separately preparing for coding questions.

Definition of done: You can solve and explain common easy-to-medium coding problems.

## Priority topics

Arrays and strings.

HashMap/HashSet.

Stack/Queue.

LinkedList.

Two pointers.

Sliding window.

Binary search.

Trees/BFS/DFS.

Heap/PriorityQueue.

Sorting/searching.

Recursion basics.

## Complexity checklist

Time complexity?

Space complexity?

Can a HashMap reduce repeated searching?

What happens as data grows from 100 to 1,000,000 records?

# Stage 8: Design Patterns + Clean Architecture

Purpose: Make the code reusable and extensible rather than merely functional.

Definition of done: You can explain why the application uses abstractions, dependency injection and selected patterns.

| Pattern / Principle | Where to practice | What to understand |
| --- | --- | --- |
| Repository | Database access | Separate persistence from business logic |
| Strategy | AI provider/model selection | Swap behavior behind a common interface |
| Factory | Provider creation | Centralize object creation |
| Adapter | External AI API | Convert external contract to internal contract |
| Observer / events | Frontend custom events | Decouple components |
| Dependency Injection | Spring services | Depend on abstractions |
| Single Responsibility | Classes/components | One clear reason to change |

## Design exercise

Draw your class/component diagram before implementing a feature. Ask an AI tool to review coupling, cohesion, testability and unnecessary complexity. Compare its suggestions with your own reasoning.

# Stage 9: RAG — Retrieval-Augmented Generation

Purpose: Build a knowledge-grounded assistant so AI answers can use your own technical documentation.

Definition of done: The assistant retrieves relevant content and supplies it as context before generating an answer.

knowledge/├── java.md├── docker.md├── github-actions.md├── linux.md├── spring-boot.md└── troubleshooting.md

Question   ↓Retrieve relevant chunks   ↓Build grounded prompt   ↓LLM   ↓Answer + optional sources

## Concepts

Embeddings.

Vector similarity.

Chunking.

Metadata.

Vector databases.

Retrieval quality.

Context injection.

Grounded answers.

Limitations of RAG.

## Beginner-friendly path

Start with keyword search so retrieval is understood first.

Add chunking and metadata.

Introduce embeddings and a vector store.

Compare keyword vs semantic retrieval.

Display retrieved sources.

# Stage 10: Authentication and Security

Purpose: Protect conversations and practice real application security fundamentals.

Definition of done: Users authenticate and can access only their own conversations.

Authentication vs authorization.

Password hashing.

JWT/session concepts.

Authorization checks.

CORS.

Input validation.

NoSQL injection and safe query construction.

XSS basics.

CSRF concept.

Secret management.

Prompt injection risks.

| Security ruleUse fake/local credentials during learning. Do not put real employer credentials, tokens, customer information, private repositories or confidential documentation into the project or an external AI coding assistant. |
| --- |

# Stage 11: Testing and Quality Engineering

Purpose: Make the application testable and learn the engineering practices explicitly called out in the JD.

Definition of done: Core backend services and frontend components have automated tests.

| Test Type | Example | Purpose |
| --- | --- | --- |
| Unit | ChatService with mocked AIService | Test one unit in isolation |
| Repository/integration | Persist conversation | Verify database integration |
| API | POST /api/chat | Verify HTTP contract |
| Component | question-input | Verify UI behavior |
| End-to-end | Ask a question in browser | Verify critical user journey |

## Java testing topics

JUnit

Mockito

Assertions

Mocking

Test fixtures

Coverage

Test naming

## Failure cases to test

Empty/invalid input.

AI failure.

Database failure.

Unauthorized access.

Long histories.

Concurrent requests conceptually.

# Stage 12: Docker and Local Orchestration

Purpose: Package the stack so it can be started consistently.

Definition of done: The application stack starts with documented Docker Compose commands.

docker compose├── frontend├── backend└── mongodb

Dockerfile.

Images vs containers.

Ports.

Volumes.

Networks.

Environment variables.

Docker Compose.

Health checks.

Logs.

Run MongoDB in a container.

Run frontend/backend.

Create a conversation.

Restart and verify persistence using volumes.

Break an environment variable and diagnose the logs.

# Stage 13: CI/CD with GitHub Actions

Purpose: Automate engineering validation and connect your existing DevOps knowledge to software engineering.

Definition of done: Pull requests automatically run tests and build validation.

Pull Request    ↓Checkout    ↓Java build + unit tests    ↓Frontend install + tests/build    ↓Docker build    ↓Optional scan    ↓Artifact/package

Workflow YAML.

Jobs and steps.

Dependency caching.

Secrets.

Artifacts.

Pull request checks.

Failure diagnostics.

Your GitHub Actions, Bamboo, Bitbucket and release-engineering experience is directly useful here. Focus on explaining how code moves from commit to validated artifact and what controls prevent broken code from reaching users.

# Stage 14: Performance and Scalability

Purpose: Measure and improve the system rather than discussing performance only theoretically.

Definition of done: You can identify bottlenecks and explain the trade-offs behind fixes.

API response time.

Database query duration.

Number of DB calls.

Frontend initial load.

AI latency.

Payload size.

Database indexes.

Pagination.

Connection pooling.

Caching.

Asynchronous processing.

Streaming.

Reducing unnecessary rendering.

Reducing repeated AI requests.

Rate limiting.

## Scale thought experiment

Assume the application grows from 10 to 100,000 users. Explain what breaks first, how you would measure it, which components can scale horizontally, and how you would handle database, cache, rate-limit, AI-provider and observability concerns.

# Stage 15: SSR with Lit

Purpose: Study the ServiceNow-specific SSR requirement after understanding normal client-side rendering.

Definition of done: You can explain CSR vs SSR, hydration and the trade-offs of server rendering.

CSR.

SSR.

Hydration.

Initial page load.

Server HTML generation.

Rendering performance trade-offs.

Caching rendered output.

Server-side execution concerns.

CSR:Browser → JavaScript → Render UISSR:Browser → Server → Render HTML → Browser                         ↓                      Hydrate                         ↓                  Interactive UI

Interview exercise: explain why an AI-first web platform might use SSR. Discuss initial rendering, server resource use, caching, data fetching, error handling and hydration. Avoid claiming SSR is always faster; discuss trade-offs.

# Stage 16: ServiceNow / Glide Integration Awareness

Purpose: Connect your general full-stack knowledge to the platform described in the JD.

Definition of done: You can discuss how a web experience integrates with a platform layer.

ServiceNow instance concept.

Glide platform at a high level.

Tables and records.

REST APIs.

Authentication/authorization.

Server-side vs client-side scripting concepts.

Business rules and Script Includes at a high level.

How web applications consume platform data.

| Scope controlDo not try to learn the entire ServiceNow platform before the interview. Learn enough to understand the architecture described in the JD and explain how your application could consume platform services. |
| --- |

# Stage 17: Observability, Debugging and Unix

Purpose: Practice the tools explicitly mentioned in the JD and learn to diagnose failures.

Definition of done: You can trace a request from browser to backend to database/AI and identify where it failed.

Browser developer tools.

Java debugger.

Application logs.

curl.

grep/find/tail/less.

ps/top.

Ports and network inspection.

Environment variables.

Git history and diff.

Break the MongoDB connection URI and diagnose startup failure.

Use an invalid AI key and trace the error.

Break the frontend API URL and inspect Network tools.

Cause a CI test failure and diagnose logs.

Create a slow query and explain how you would identify it.

# Stage 18: Final Architecture and System Design Interview

Purpose: Turn the project into a system-design case study you can explain confidently.

Definition of done: You can whiteboard the architecture, defend design choices and discuss trade-offs.

┌─────────────────────┐                         │     Browser          │                         │  Lit Web Components   │                         └──────────┬──────────┘                                    │ HTTPS / REST                         ┌──────────▼──────────┐                         │   API / SSR Layer   │                         └──────────┬──────────┘                                    │                    ┌───────────────┼────────────────┐                    │               │                │             ┌──────▼──────┐ ┌────▼─────┐    ┌────▼─────┐             │ Chat Service│ │ Retrieval │    │ Auth     │             └──────┬──────┘ └────┬─────┘    └──────────┘                    │              │             ┌──────▼──────┐ ┌────▼────────┐             │ MongoDB  │ │ Knowledge   │             └─────────────┘ │ / Vector DB │                              └────┬────────┘                                   │                              ┌────▼────┐                              │   LLM   │                              └─────────┘

## Questions you should answer

Why Lit?

Why separate frontend/backend?

Why an AIService interface?

How would you replace the AI provider?

How would you scale the backend?

How would you reduce AI latency?

How would you secure conversations?

How would you handle AI-provider outage?

How would you address prompt injection?

Where would you add caching?

How would you test it?

Why might SSR help?

What changes for 100x traffic?

# 19. How to Use ChatGPT and Copilot Without Losing the Learning

Using AI to generate code is compatible with this learning plan if understanding the code is part of the definition of done. The goal is not to prove that you can type every line from memory; it is to prove that you can design, review, debug, modify, test and explain software.

## The 7-step AI coding loop

Understand: define the feature in plain English.

Design: draw request flow and identify files/classes/components.

Ask: generate one small module.

Explain: get a line/function-level explanation.

Verify: run it and test failures.

Modify: change part of it yourself.

Teach back: explain it without looking at the AI explanation.

## Example prompt

I am learning Spring Boot and building an AI Support Assistant.Do not generate the whole application.First:1. Explain the design for a ChatService.2. Explain its responsibilities.3. Explain its dependencies.4. Show a small implementation.5. Explain every method and important line.6. Give me three failure cases.7. Give me unit-test cases.8. Ask me five interview questions about this code.

## Do not

Ask for the entire project in one prompt and copy it blindly.

Memorize generated code without understanding architecture.

Claim implementation ownership if you cannot explain the request flow.

Rely on AI explanations without running the code.

Paste confidential employer information into AI tools.

# 20. Suggested Learning Roadmap

| Phase | Main Focus | Build Target | Interview Outcome |
| --- | --- | --- | --- |
| 1 | Java + Spring Boot | Backend skeleton + REST | Explain OOP, DI, REST |
| 2 | MongoDB | Persistence + conversations | Explain document modeling, queries, repositories and indexes |
| 3 | JavaScript + Lit | Reusable UI | Explain JS async + components |
| 4 | API integration | End-to-end chat | Trace a request end-to-end |
| 5 | AI | LLM-backed chat | Explain AI application architecture |
| 6 | Testing | Automated tests | Discuss test strategy |
| 7 | RAG | Knowledge-grounded answers | Explain embeddings/RAG |
| 8 | Docker + CI/CD | Containerized pipeline | Discuss delivery/tooling |
| 9 | Performance | Measure + optimize | Discuss scale/trade-offs |
| 10 | SSR + ServiceNow | Architecture extension | Connect project to JD |
| 11 | Mock interviews | Design + coding drills | Interview readiness |

# 21. Interview Study Checklist

## Java

OOP

Collections

HashMap internals

equals/hashCode

Exceptions

Streams

Generics

Threads/executors

JVM basics

## JavaScript

ES6+

Closures

Promises

async/await

Event loop

DOM/events

Modules

fetch

Error handling

## Web Components / Lit

Custom elements

Shadow DOM

Reactive properties

Templates

Lifecycle

Events

Composition

SSR awareness

## DSA

Arrays

Strings

HashMap

HashSet

Stack

Queue

LinkedList

Binary search

Sliding window

Trees

BFS/DFS

Heap

## System Design

Load balancing

Caching

Database indexing

Horizontal scaling

Queues

Rate limiting

Observability

Fault tolerance

API design

## AI

LLMs

Tokens

Prompting

RAG

Embeddings

Vector search

Tool calling

Streaming

Hallucinations

Prompt injection

Evaluation

# 22. What You Will Gain at the End

If you complete the stages rather than merely copying the code, you will have a practical full-stack project and a stronger mental model of the technologies mentioned in the ServiceNow JD.

| Skill | Evidence you will have |
| --- | --- |
| Java | Layered Spring Boot backend with services, repositories and tests |
| JavaScript | Modular frontend using modern asynchronous JavaScript |
| Lit / Web Components | Reusable custom components with events and state |
| REST | Production-style API endpoints |
| Database | MongoDB schema, persistence and indexes |
| AI | Real AI integration with error handling |
| RAG | Grounded knowledge retrieval workflow |
| Architecture | Documented end-to-end system design |
| Testing | Unit/integration/component tests |
| Docker | Repeatable local environment |
| CI/CD | Automated build and test pipeline |
| Performance | Measured bottlenecks and optimization examples |
| SSR | Practical understanding of server rendering |
| Debugging | Hands-on failure diagnosis |
| Interview communication | Ability to explain your own design and trade-offs |

# 23. How to Describe This Project in an Interview

| Suggested interview storyI built an AI Support Assistant as a progressive full-stack project. The frontend uses reusable Lit Web Components, the backend uses Java and Spring Boot with a layered architecture, and MongoDB stores conversations. I integrated an AI service behind an internal interface so the provider can be changed and mocked for tests. I then added retrieval-based grounding, automated tests, Docker and GitHub Actions. I also studied SSR, performance and scaling so I could reason about the architecture beyond the initial prototype. |
| --- |

Only describe features you actually completed and can explain. If a module is still theoretical, say so.

# 24. Final Completion Checklist

☐ I can explain every backend layer.

☐ I can explain the frontend component tree.

☐ I can trace a request from browser to database/AI and back.

☐ I can explain OOP decisions in my own code.

☐ I can solve common HashMap/array/string coding problems.

☐ I understand JavaScript promises and the event loop.

☐ I can explain Web Components and Shadow DOM.

☐ I can explain why Lit is useful for reusable components.

☐ I can explain CSR vs SSR and hydration.

☐ I can explain how the AI integration works.

☐ I can explain RAG at a practical level.

☐ I can explain at least five design patterns/principles.

☐ I have unit tests for core services.

☐ I can run the application with Docker.

☐ I can explain my GitHub Actions pipeline.

☐ I can identify performance bottlenecks and propose fixes.

☐ I can explain authentication and authorization.

☐ I can diagnose a broken API or container using logs/tools.

☐ I can whiteboard the final architecture.

☐ I can explain trade-offs without relying on AI-generated wording.

# Appendix A — Useful AI Prompts

## Architecture review

Review this module as a senior Java/JavaScript engineer. Identify coupling, cohesion, unnecessary complexity, error-handling gaps and testability issues. Do not rewrite it until you explain the problems.

## Line-by-line learning

Explain this code line by line. For each line tell me what it does, why it is needed, what would happen if it were removed, and what common alternative exists.

## Debugging

Here is the error and relevant code. First explain the likely root cause, then give me a debugging plan. Do not immediately give me a replacement implementation.

## Interview practice

Ask me five interview questions about this module, one at a time. Wait for my answer, evaluate it, correct gaps, and then ask the next question.

## Testing

Create a test plan for this module. Include happy paths, invalid inputs, dependency failures, edge cases and security-related cases. Explain why each test matters.

## System design

Review this architecture for 100x growth. Identify likely bottlenecks and propose changes. For each change explain the trade-off, not just the solution.

# Appendix B — Recommended First Milestone

Create the Git repository.

Create the Spring Boot backend.

Create GET /api/health.

Create the first Lit frontend component.

Display the frontend in the browser.

Call the health endpoint from the frontend.

Commit the working baseline.

Write a short README explaining what you built and learned.

| Stop condition before Stage 2You should be able to open the code and explain the frontend entry point, Lit component, backend entry point, REST controller, HTTP request, response, and local development commands without asking an AI tool. |
| --- |
