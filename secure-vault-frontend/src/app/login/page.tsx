"use client";

import { useState, FormEvent } from 'react';
import { login, ApiError } from '@/services/authService';
import { AxiosError } from 'axios';


export default function LoginPage() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [token, setToken] = useState('');

  async function handleSubmit(event: FormEvent) {
    event.preventDefault();
    setError('');
    setToken('');

    try {
      const response = await login({ email, password });

      console.log('Login Bem Sucedido: ', response);
      setToken(response.token);
    } catch (err) {
      const logError = err as AxiosError<ApiError>;
      setError(logError.response?.data?.message || 'Erro no login, email ou senha inválidos, Tente novamente.');
    }
  }

  return (
    <main className="flex min-h-screen flex-col items-center justify-center bg-amber-50 p-4">
      <div className="w-full max-w-sm">
        <h1 className="text-3xl font-bold mb-6 text-center text-zinc-950">
          Secure Vault
        </h1>
        
        
        <form onSubmit={handleSubmit} className="bg-slate-950 shadow-2xl rounded-xl px-8 pt-6 pb-8">
          <div className="mb-4">
            <label htmlFor="email" className="block text-slate-300 text-sm font-bold mb-2">
              Email
            </label>
            <input 
              id="email"
              type="email" 
              value={email} 
              onChange={(e) => setEmail(e.target.value)}
              className="w-full px-3 py-2 rounded-lg bg-slate-700 text-white border border-slate-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
            />
          </div>

          <div className="mb-6">
            <label htmlFor="password" className="block text-slate-300 text-sm font-bold mb-2">
              Senha
            </label>
            <input 
              id="password"
              type="password" 
              value={password} 
              onChange={(e) => setPassword(e.target.value)}
              className="w-full px-3 py-2 rounded-lg bg-slate-700 text-white border border-slate-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
            />
          </div>

          <div className="flex items-center justify-center">
            <button type="submit" className="w-full px-6 py-2 rounded-lg bg-blue-600 text-white font-bold hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50">
              Login
            </button>
          </div>
        </form>


        {error && (
          <p className="mt-4 text-center text-sm text-red-500">{error}</p>
        )}
        {token && (
          <div className="mt-4 p-4 rounded-lg bg-green-900 border border-green-700">
            <p className="text-center text-sm text-green-300">Login com sucesso! Token:</p>
            <p className="text-center text-xs text-green-400 break-all mt-2">{token}</p>
          </div>
        )}
      </div>
    </main>
  );
}