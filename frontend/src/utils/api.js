// src/utils/api.js
const BASE_URL = 'http://localhost:8080/api/v1';

export const api = {
  get: async (route) => {
    const res = await fetch(`${BASE_URL}/${route}`);
    if (!res.ok) throw new Error(`Erro HTTP: ${res.status}`);
    return res.json();
  },
  post: async (route, body) => {
    return fetch(`${BASE_URL}/${route}`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body),
    });
  },
  // Substitua ou adicione o PUT aqui embaixo:
  put: async (route, body) => {
    const res = await fetch(`${BASE_URL}/${route}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body),
    });
    if (!res.ok) throw new Error(`Erro HTTP: ${res.status}`);
    return res.json();
  },
  delete: async (route) => {
    return fetch(`${BASE_URL}/${route}`, { method: 'DELETE' });
  }
};