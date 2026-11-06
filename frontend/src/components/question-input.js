import { LitElement, html, css } from 'lit';

export class QuestionInput extends LitElement {
  static properties = {
    error: { state: true },
  };

  static styles = css`
    textarea { display: block; box-sizing: border-box; width: 100%; margin: 0.5rem 0; font: inherit; }
    button { padding: 0.75rem; cursor: pointer; }
    [role="alert"] { color: #a31919; }
  `;

  constructor() {
    super();
    this.error = '';
  }

  submitQuestion(event) {
    event.preventDefault();
    const input = this.renderRoot.querySelector('textarea');
    const content = input.value.trim();
    if (!content) {
      this.error = 'Enter a question before adding it to the demo.';
      return;
    }
    this.error = '';
    this.dispatchEvent(new CustomEvent('question-submitted', {
      detail: { content },
      bubbles: true,
      composed: true,
    }));
    input.value = '';
  }

  render() {
    return html`
      <form @submit=${this.submitQuestion}>
        <label for="question">Question (local demo only)</label>
        <textarea id="question" rows="3" maxlength="10000"
          aria-invalid=${this.error ? 'true' : 'false'} aria-describedby="input-error"></textarea>
        <p id="input-error" role="alert">${this.error}</p>
        <button type="submit">Add to demo</button>
      </form>
    `;
  }
}

customElements.define('question-input', QuestionInput);
