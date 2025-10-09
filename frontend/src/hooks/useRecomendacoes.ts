import { useState } from 'react';
import type { Livro } from '../types/livro';
import axios from 'axios';
import { API_BASE_URL } from '../constants/api';

export const useRecomendacoes = () => {
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const getRecomendacoes = async (usuarioId: number): Promise<{ data: Livro[] | string; success: boolean }> => {
        setLoading(true);
        setError(null);
        
        try {
            const response = await axios.get(`${API_BASE_URL}/recomendacoes/usuario/${usuarioId}`);
            setLoading(false);
            return { data: response.data, success: true };
        } catch (err) {
            const errorMessage = axios.isAxiosError(err) 
                ? err.response?.data?.message || err.message 
                : 'Erro ao buscar recomendações';
            setError(errorMessage);
            setLoading(false);
            return { data: [], success: false };
        }
    };

    return {
        getRecomendacoes,
        loading,
        error
    };
};
