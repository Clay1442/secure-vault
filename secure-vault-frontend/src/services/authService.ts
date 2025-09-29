import { api } from '@/services/api';

interface LoginData {
    email: string;
    password: string;
}

interface LoginResponse {
    token: string;
}

interface RegisterData {
    username: string;
    email: string;
    password: string;
}

interface ApiError {
    message: string;
}

export type { ApiError };

export async function login(data: LoginData): Promise<LoginResponse> {
    const response = await api.post<LoginResponse>('/auth/login', data);
    return response.data;
}

export async function register(data: RegisterData): Promise<RegisterData> {
    return api.post<RegisterData>('/auth/register', data).then(res => res.data);
}