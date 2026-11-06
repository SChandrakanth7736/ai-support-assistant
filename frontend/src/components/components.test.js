import { afterEach, expect, it, vi } from 'vitest';
import './ai-chat-app.js';

afterEach(() => {
  document.body.replaceChildren();
  vi.unstubAllGlobals();
});

async function mount(tag) {
  const element = document.createElement(tag);
  document.body.append(element);
  await element.updateComplete;
  return element;
}

it('rejects blank input and dispatches a trimmed question across Shadow DOM', async () => {
  const input = await mount('question-input');
  const listener = vi.fn();
  document.body.addEventListener('question-submitted', listener, { once: true });
  const form = input.shadowRoot.querySelector('form');
  form.dispatchEvent(new Event('submit', { cancelable: true }));
  await input.updateComplete;
  expect(input.shadowRoot.querySelector('[role="alert"]').textContent).toContain('Enter a question');
  expect(listener).not.toHaveBeenCalled();
  input.shadowRoot.querySelector('textarea').value = '  What is Lit?  ';
  form.dispatchEvent(new Event('submit', { cancelable: true }));
  await input.updateComplete;
  expect(listener.mock.calls[0][0].detail).toEqual({ content: 'What is Lit?' });
  expect(input.shadowRoot.querySelector('textarea').value).toBe('');
});

it('selects conversations and adds questions only to the selected local demo', async () => {
  vi.stubGlobal('fetch', vi.fn().mockResolvedValue(new Response('{"status":"UP"}')));
  const app = await mount('ai-chat-app');
  const list = app.shadowRoot.querySelector('conversation-list');
  await list.updateComplete;
  list.shadowRoot.querySelectorAll('button')[1].click();
  await app.updateComplete;
  expect(app.selectedId).toBe('demo-2');
  const input = app.shadowRoot.querySelector('question-input');
  await input.updateComplete;
  input.shadowRoot.querySelector('textarea').value = '<script>alert(1)</script>';
  input.shadowRoot.querySelector('form').dispatchEvent(new Event('submit', { cancelable: true }));
  await app.updateComplete;
  expect(app.conversations[0].messages).toHaveLength(1);
  expect(app.conversations[1].messages).toHaveLength(2);
  const window = app.shadowRoot.querySelector('chat-window');
  await window.updateComplete;
  const message = window.shadowRoot.querySelectorAll('chat-message')[1];
  await message.updateComplete;
  expect(message.shadowRoot.querySelector('script')).toBeNull();
  expect(message.shadowRoot.querySelector('p').textContent).toBe('<script>alert(1)</script>');
});

it('shows pending, failure, and successful retry states for the health check', async () => {
  let rejectRequest;
  const fetchMock = vi.fn().mockImplementationOnce(() => new Promise((resolve, reject) => {
    rejectRequest = reject;
  }));
  vi.stubGlobal('fetch', fetchMock);
  const app = await mount('ai-chat-app');
  await app.updateComplete;
  expect(app.shadowRoot.querySelector('loading-indicator')).not.toBeNull();
  rejectRequest(new Error('Network unavailable'));
  await vi.waitFor(() => expect(app.loading).toBe(false));
  await app.updateComplete;
  const error = app.shadowRoot.querySelector('error-message');
  await error.updateComplete;
  expect(error.shadowRoot.textContent).toContain('Network unavailable');
  fetchMock.mockResolvedValueOnce(new Response('{"status":"UP"}'));
  await app.checkHealth();
  await app.updateComplete;
  expect(app.error).toBe('');
  expect(app.shadowRoot.textContent).toContain('Backend status: UP');
});
