import { LitElement, html } from 'lit';

export class LoadingIndicator extends LitElement {
  render() {
    return html`<p role="status">Checking backend connection...</p>`;
  }
}

customElements.define('loading-indicator', LoadingIndicator);
