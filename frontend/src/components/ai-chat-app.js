import { LitElement, html, css } from 'lit';
import { getHealth } from '../services/api-client.js';
import './conversation-list.js';
import './chat-window.js';
import './question-input.js';
import './loading-indicator.js';
import './error-message.js';

export class AiChatApp extends LitElement {
  static properties = {
    conversations: { state: true },
    selectedId: { state: true },
    loading: { state: true },
    healthStatus: { state: true },
    error: { state: true },
  };

  static styles = css`
    :host { display: block; max-width: 1100px; margin: auto; padding: 1.5rem; }
    .layout { display: grid; grid-template-columns: 240px minmax(0, 1fr); gap: 1.5rem; }
    .panel { background: white; padding: 1rem; border-radius: 0.5rem; }
    button { padding: 0.5rem; cursor: pointer; }
    @media (max-width: 700px) { .layout { grid-template-columns: 1fr; } }
  `;

  constructor() {
    super();
    this.conversations = [
      {
        id: 'demo-1',
        title: 'Learning Lit',
        messages: [{ role: 'SYSTEM', content: 'Sample data: Lit components render from reactive properties.' }],
      },
      {
        id: 'demo-2',
        title: 'Learning REST',
        messages: [{ role: 'SYSTEM', content: 'Sample data: only the health check calls the backend in Stage 3.' }],
      },
    ];
    this.selectedId = 'demo-1';
    this.loading = false;
    this.healthStatus = '';
    this.error = '';
  }

  firstUpdated() {
    this.checkHealth();
  }

  async checkHealth() {
    if (this.loading) return;
    this.loading = true;
    this.error = '';
    this.healthStatus = '';
    try {
      const health = await getHealth();
      this.healthStatus = health.status;
    } catch (error) {
      const detail = error instanceof Error ? error.message : 'Unknown connection error.';
      this.error = `Could not check the backend. ${detail} Start the backend on port 8080 and retry.`;
    } finally {
      this.loading = false;
    }
  }

  selectConversation(event) {
    this.selectedId = event.detail.id;
  }

  addQuestion(event) {
    this.conversations = this.conversations.map((conversation) =>
      conversation.id === this.selectedId
        ? { ...conversation, messages: [...conversation.messages, { role: 'USER', content: event.detail.content }] }
        : conversation);
  }

  render() {
    const selected = this.conversations.find((conversation) => conversation.id === this.selectedId);
    return html`
      <header>
        <h1>AI Support Assistant</h1>
        <p>Stage 3 demo: sample conversations and local-only messages. No AI replies or persistence. Reloading resets the demo.</p>
        <button type="button" ?disabled=${this.loading} @click=${this.checkHealth}>Check backend</button>
        ${this.loading ? html`<loading-indicator></loading-indicator>` : html``}
        ${this.healthStatus ? html`<p role="status">Backend status: ${this.healthStatus}</p>` : html``}
        <error-message .message=${this.error}></error-message>
      </header>
      <main class="layout">
        <div class="panel">
          <conversation-list .conversations=${this.conversations} .selectedId=${this.selectedId}
            @conversation-selected=${this.selectConversation}></conversation-list>
        </div>
        <section class="panel" aria-label="Selected demo conversation">
          <h2>${selected?.title}</h2>
          <chat-window .messages=${selected?.messages ?? []}></chat-window>
          <question-input @question-submitted=${this.addQuestion}></question-input>
        </section>
      </main>
    `;
  }
}

customElements.define('ai-chat-app', AiChatApp);
