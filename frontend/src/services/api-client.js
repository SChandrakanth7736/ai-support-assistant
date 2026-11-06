export async function getHealth() {
  const response = await fetch('/api/health', {
    headers: { Accept: 'application/json' },
    signal: AbortSignal.timeout(10000),
  });
  if (!response.ok) {
    throw new Error(`Health check failed (HTTP ${response.status}).`);
  }
  const health = await response.json();
  if (typeof health?.status !== 'string') {
    throw new Error('Health endpoint returned an invalid response.');
  }
  return health;
}
