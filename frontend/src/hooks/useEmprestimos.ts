import { useState, useCallback } from 'react';
import axios from 'axios';
import type { Emprestimo, CreateEmprestimoData } from '../types/statusEmprestimo';
import { ENDPOINTS } from '../constants/api';

export const useEmprestimos = () => {
    const [emprestimos, setEmprestimos] = useState<Emprestimo[]>([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const fetchEmprestimos = useCallback(async () => {
        setLoading(true);
        try {
            const response = await axios.get<Emprestimo[]>(ENDPOINTS.EMPRESTIMOS);
            setEmprestimos(response.data);
            setError(null);
        } catch (err) {
            setError('Erro ao buscar empréstimos.');
            console.error(err);
        } finally {
            setLoading(false);
        }
    }, []); // Dependências vazias pois não depende de nenhum estado

    const createEmprestimo = async (data: CreateEmprestimoData) => {
        setLoading(true);
        try {
            
            const payload = {
                livro: { id: data.livroId },
                usuario: { id: data.usuarioId },
                dataEmprestimo: data.dataEmprestimo
            }
            
            console.log("payload: ", payload)
            
            await axios.post(ENDPOINTS.EMPRESTIMOS, payload);
            await fetchEmprestimos();
            setError(null);
            return true;
        } catch (err: any) {
            const errorMessage = err.response?.data || 'Erro ao criar empréstimo.';
            setError(errorMessage);
            console.error(err);
            return false;
        } finally {
            setLoading(false);
        }
    };

    const devolverLivro = async (emprestimoId: number) => {
        setLoading(true);
        try {
            await axios.put(`${ENDPOINTS.EMPRESTIMOS}/${emprestimoId}/devolucao`);
            await fetchEmprestimos(); 
            setError(null);
            return true;
        } catch (err: any) {
            const errorMessage = err.response?.data?.message || 'Erro ao devolver livro.';
            setError(errorMessage);
            console.error(err);
            return false;
        } finally {
            setLoading(false);
        }
    };

    return {
        emprestimos,
        loading,
        error,
        fetchEmprestimos,
        createEmprestimo,
        devolverLivro
    };
};
