import { LitElement, html, css } from 'lit';

export class ConversationList extends LitElement {
  static properties = {
    conversations: { attribute: false },
    selectedId: { type: String },
  };

  static styles = css`
    ul { list-style: none; padding: 0; }
    button { padding: 0.75rem; margin-bottom: 0.5rem; width: 100%; text-align: left; cursor: pointer; }
    button[aria-pressed="true"] { font-weight: bold; border: 2px solid #285bc2; }
  `;

  constructor() {
    super();
    this.conversations = [];
    this.selectedId = '';
  }

  selectConversation(id) {
    this.dispatchEvent(new CustomEvent('conversation-selected', {
      detail: { id },
      bubbles: true,
      composed: true,
    }));
  }

  render() {
    return html`
      <nav aria-label="Demo conversations">
        <h2>Demo conversations</h2>
        <ul>
          ${this.conversations.map((conversation) => html`
            <li><button
              type="button"
              aria-pressed=${conversation.id === this.selectedId ? 'true' : 'false'}
              @click=${() => this.selectConversation(conversation.id)}
            >${conversation.title}</button></li>
          `)}
        </ul>
      </nav>
    `;
  }
}

customElements.define('conversation-list', ConversationList);
