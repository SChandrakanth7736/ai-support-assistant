# Frontend

Stage 3 frontend built with JavaScript, Lit Web Components, and Vite. Requires Node.js 20.19+ and npm.

## Current status

The UI displays clearly labelled sample conversations. Selecting a conversation updates the message view; submitting a nonblank question adds a local-only user message. Reloading resets all demo data. There are no AI replies or conversation/message persistence calls.

## Run locally

From this directory:

```sh
npm ci
npm run dev
```

Open the URL printed by Vite (normally `http://localhost:5173`). In another terminal, start MongoDB and the existing backend as described in [backend/README.md](../backend/README.md).

The UI automatically checks `GET /api/health` and offers a retry button. Vite proxies `/api` to `http://127.0.0.1:8080`, avoiding a backend CORS change. If you use a different backend port, update the proxy target in `vite.config.js`.

The proxy applies to the development server. A production deployment must route `/api` to the backend separately; production routing/deployment is outside Stage 3.

## Verify

```sh
npm test
npm run build
npm run preview
```

Tests cover the health API contract, input validation, custom events crossing Shadow DOM, conversation selection, safe text rendering, and health-check loading/error/retry behavior. They use simulated DOM rendering and mocked HTTP responses, not a real browser or database.

Manually check both sample conversations, add a question, and reload to confirm the demo resets. With the backend running, the health status should show `UP`. With it stopped, check that an explicit error appears and that retry works after restarting it.

## Structure and request flow

- `index.html` loads `src/main.js`, which registers `ai-chat-app`.
- `src/components/` contains the app coordinator, conversation list, chat window/message, question input, loading indicator, and error display.
- Lit reactive properties carry arrays/objects between components using `.property` bindings; custom events notify the parent about selection and question submission.
- `src/services/api-client.js` uses `fetch` with a ten-second timeout, HTTP status checks, and JSON contract validation.
- `vite.config.js` configures the development proxy and Vitest's simulated DOM environment.

Health flow: app lifecycle (`firstUpdated`) or retry button -> API client -> Vite proxy -> existing backend `/api/health` -> reactive loading/status/error update.

Question flow: form validation -> `question-submitted` event -> immutable app state update -> chat window -> chat message. This flow never calls the backend in Stage 3.

See the [master project guide](../AI_Support_Assistant_ServiceNow_Interview_Project_Guide.md) for the staged frontend plan.
