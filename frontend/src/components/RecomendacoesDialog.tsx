import React, { useEffect, useState } from 'react';
import type { Usuario } from '../types/usuario';
import type { Livro } from '../types/livro';
import { useRecomendacoes } from '../hooks/useRecomendacoes';

interface RecomendacoesDialogProps {
    isOpen: boolean;
    onClose: () => void;
    usuario: Usuario | null;
}

const RecomendacoesDialog: React.FC<RecomendacoesDialogProps> = ({ isOpen, onClose, usuario }) => {
    const { getRecomendacoes, loading, error } = useRecomendacoes();
    const [recomendacoes, setRecomendacoes] = useState<Livro[]>([]);
    const [mensagem, setMensagem] = useState<string>('');

    useEffect(() => {
        const fetchRecomendacoes = async () => {
            if (isOpen && usuario) {
                const result = await getRecomendacoes(usuario.id);
                if (result.success) {
                    if (Array.isArray(result.data)) {
                        setRecomendacoes(result.data);
                        setMensagem('');
                    } else {
                        setRecomendacoes([]);
                        setMensagem(result.data as string);
                    }
                }
            }
        };

        fetchRecomendacoes();
    }, [isOpen, usuario]);

    if (!isOpen) return null;

    return (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
            <div className="bg-white p-6 rounded shadow-lg w-[32rem] max-h-[80vh] overflow-y-auto">
                <h2 className="text-xl font-semibold mb-4">Recomendações para {usuario?.nome}</h2>
                
                {loading && (
                    <div className="flex justify-center items-center p-4">
                        <div className="animate-spin rounded-full h-8 w-8 border-t-2 border-b-2 border-indigo-600"></div>
                    </div>
                )}

                {error && (
                    <div className="bg-red-50 text-red-600 p-4 rounded mb-4">
                        {error}
                    </div>
                )}

                {!loading && !error && (
                    <div className="mt-2">
                        {mensagem ? (
                            <p className="text-sm text-gray-500">{mensagem}</p>
                        ) : (
                            <div className="space-y-4">
                                {recomendacoes.map((livro) => (
                                    <div key={livro.id} className="border rounded p-4">
                                        <h3 className="font-medium">{livro.titulo}</h3>
                                        <p className="text-sm text-gray-600">Autor: {livro.autor}</p>
                                        <p className="text-sm text-gray-600">Categoria: {livro.categoria}</p>
                                        {livro.isbn && <p className="text-sm text-gray-600">ISBN: {livro.isbn}</p>}
                                    </div>
                                ))}
                            </div>
                        )}
                    </div>
                )}

                <div className="flex justify-end gap-2 mt-4">
                    <button
                        onClick={onClose}
                        className="px-4 py-2 border border-gray-300 rounded hover:bg-gray-100"
                    >
                        Fechar
                    </button>
                </div>
            </div>
        </div>
    );
};

export default RecomendacoesDialog;