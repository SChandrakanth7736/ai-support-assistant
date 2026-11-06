import { LitElement, html, css } from 'lit';

export class ErrorMessage extends LitElement {
  static properties = {
    message: { type: String },
  };

  static styles = css`
    p { color: #a31919; }
  `;

  constructor() {
    super();
    this.message = '';
  }

  render() {
    return this.message ? html`<p role="alert">${this.message}</p>` : html``;
  }
}

customElements.define('error-message', ErrorMessage);
