import { useState, useCallback } from 'react';
import axios from 'axios';
import type { Usuario, UsuarioInput } from '../types/usuario';
import { ENDPOINTS } from '../constants/api';

export const useUsuarios = () => {
    const [usuarios, setUsuarios] = useState<Usuario[]>([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const fetchUsuarios = useCallback(async () => {
        setLoading(true);
        setError(null);
        try {
            const response = await axios.get<Usuario[]>(ENDPOINTS.USUARIOS);
            setUsuarios(response.data);
        } catch (err) {
            setError("Erro ao buscar usuários.");
            console.error(err);
        } finally {
            setLoading(false);
        }
    }, []);

    const createUsuario = async (usuario: UsuarioInput) => {
        setLoading(true);
        setError(null);
        try {
            const response = await axios.post<Usuario>(ENDPOINTS.USUARIOS, usuario);
            setUsuarios(prev => [...prev, response.data]);
            return { success: true };
        } catch (err: any) {
            const errorMessage = handleApiError(err);
            setError(errorMessage);
            return { success: false, error: errorMessage };
        } finally {
            setLoading(false);
        }
    };

    const updateUsuario = async (id: number, usuario: UsuarioInput) => {
        setLoading(true);
        setError(null);
        try {
            const response = await axios.put<Usuario>(`${ENDPOINTS.USUARIOS}/${id}`, usuario);
            setUsuarios(prev => prev.map(u => u.id === id ? response.data : u));
            return { success: true };
        } catch (err: any) {
            const errorMessage = handleApiError(err);
            setError(errorMessage);
            return { success: false, error: errorMessage };
        } finally {
            setLoading(false);
        }
    };

    const deleteUsuario = async (id: number) => {
        setLoading(true);
        setError(null);
        try {
            await axios.delete(`${ENDPOINTS.USUARIOS}/${id}`);
            setUsuarios(prev => prev.filter(usuario => usuario.id !== id));
            return { success: true };
        } catch (err: any) {
            setError("Erro ao excluir usuário.");
            console.error(err);
            return { success: false, error: "Erro ao excluir usuário." };
        } finally {
            setLoading(false);
        }
    };

    const handleApiError = (err: any): string => {
        if (err.response?.data?.fieldErrors) {
            const fieldErrors = err.response.data.fieldErrors;
            return Object.values(fieldErrors).join('\n');
        }
        return err.response?.data?.message || "Erro ao salvar usuário.";
    };

    return {
        usuarios,
        loading,
        error,
        fetchUsuarios,
        createUsuario,
        updateUsuario,
        deleteUsuario
    };
};
