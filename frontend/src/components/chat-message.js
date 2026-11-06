import { LitElement, html, css } from 'lit';

export class ChatMessage extends LitElement {
  static properties = {
    message: { attribute: false },
  };

  static styles = css`
    article { padding: 1rem; margin: 0.75rem 0; background: #edf2fa; border-radius: 0.5rem; }
    p { white-space: pre-wrap; overflow-wrap: anywhere; margin-bottom: 0; }
  `;

  constructor() {
    super();
    this.message = null;
  }

  render() {
    if (!this.message) return html``;
    return html`
      <article>
        <strong>${this.message.role}</strong>
        <p>${this.message.content}</p>
      </article>
    `;
  }
}

customElements.define('chat-message', ChatMessage);
