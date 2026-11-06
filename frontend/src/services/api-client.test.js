import { afterEach, describe, expect, it, vi } from 'vitest';
import { getHealth } from './api-client.js';

afterEach(() => vi.unstubAllGlobals());

describe('getHealth', () => {
  it('calls the health endpoint and returns its JSON', async () => {
    const fetchMock = vi.fn().mockResolvedValue(new Response('{"status":"UP"}'));
    vi.stubGlobal('fetch', fetchMock);
    await expect(getHealth()).resolves.toEqual({ status: 'UP' });
    expect(fetchMock).toHaveBeenCalledWith('/api/health', expect.objectContaining({
      headers: { Accept: 'application/json' },
    }));
  });

  it('rejects non-success HTTP responses', async () => {
    vi.stubGlobal('fetch', vi.fn().mockResolvedValue(new Response('', { status: 503 })));
    await expect(getHealth()).rejects.toThrow('HTTP 503');
  });

  it('rejects an invalid response contract', async () => {
    vi.stubGlobal('fetch', vi.fn().mockResolvedValue(new Response('{}')));
    await expect(getHealth()).rejects.toThrow('invalid response');
  });
});
