import { LitElement, html } from 'lit';
import './chat-message.js';

export class ChatWindow extends LitElement {
  static properties = {
    messages: { attribute: false },
  };

  constructor() {
    super();
    this.messages = [];
  }

  render() {
    return html`
      <section aria-label="Demo messages" aria-live="polite">
        ${this.messages.length
          ? this.messages.map((message) => html`<chat-message .message=${message}></chat-message>`)
          : html`<p>No demo messages yet.</p>`}
      </section>
    `;
  }
}

customElements.define('chat-window', ChatWindow);
