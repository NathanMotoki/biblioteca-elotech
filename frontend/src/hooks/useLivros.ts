import { useState, useCallback } from 'react';
import axios from 'axios';
import type { Livro, LivroInput } from '../types/livro';
import { ENDPOINTS } from '../constants/api';

export const useLivros = () => {
    const [livros, setLivros] = useState<Livro[]>([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const fetchLivros = useCallback(async () => {
        setLoading(true);
        setError(null);
        try {
            const response = await axios.get<Livro[]>(ENDPOINTS.LIVROS);
            setLivros(response.data);
        } catch (err) {
            setError("Não foi possível buscar os livros.");
            console.error(err);
        } finally {
            setLoading(false);
        }
    }, []);

    const createLivro = async (livro: LivroInput) => {
        setLoading(true);
        setError(null);
        try {
            const response = await axios.post<Livro>(ENDPOINTS.LIVROS, livro);
            setLivros(prev => [...prev, response.data]);
            return { success: true };
        } catch (err: any) {
            const errorMessage = handleApiError(err);
            setError(errorMessage);
            return { success: false, error: errorMessage };
        } finally {
            setLoading(false);
        }
    };

    const updateLivro = async (id: number, livro: LivroInput) => {
        setLoading(true);
        setError(null);
        try {
            const response = await axios.put<Livro>(`${ENDPOINTS.LIVROS}/${id}`, livro);
            setLivros(prev => prev.map(l => l.id === id ? response.data : l));
            return { success: true };
        } catch (err: any) {
            

            const errorMessage = handleApiError(err);
            setError(errorMessage);
            return { success: false, error: errorMessage };
        } finally {
            setLoading(false);
        }
    };

    const deleteLivro = async (id: number) => {
        setLoading(true);
        setError(null);
        try {
            await axios.delete(`${ENDPOINTS.LIVROS}/${id}`);
            setLivros(prev => prev.filter(livro => livro.id !== id));
            return { success: true };
        } catch (err: any) {
            setError("Não foi possível excluir o livro.");
            console.error(err);
            return { success: false, error: "Não foi possível excluir o livro." };
        } finally {
            setLoading(false);
        }
    };

    const handleApiError = (err: any): string => {
        console.error(err);
        if (err.response?.data) {
            const errors = err.response.data;
            return errors;
        }
        return err.response?.data?.message || "Erro ao salvar livro.";
    };

    return {
        livros,
        loading,
        error,
        fetchLivros,
        createLivro,
        updateLivro,
        deleteLivro
    };
};
