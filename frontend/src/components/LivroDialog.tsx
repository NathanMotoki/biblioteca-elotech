import React, { useState, useEffect } from 'react';
import type { Livro } from '../types/livro';

interface LivroDialogProps {
    isOpen: boolean;
    onClose: () => void;
    onSubmit: (livro: { titulo: string; autor: string; categoria: string; dataPublicacao: string; isbn: string }) => void;
    initialData?: Livro;
}

const LivroDialog: React.FC<LivroDialogProps> = ({ isOpen, onClose, onSubmit, initialData }) => {
    const [titulo, setTitulo] = useState('');
    const [autor, setAutor] = useState('');
    const [categoria, setCategoria] = useState('');
    const [dataPublicacao, setDataPublicacao] = useState('');
    const [isbn, setIsbn] = useState('');

    useEffect(() => {
        if (isOpen && initialData) {
            setTitulo(initialData.titulo);
            setAutor(initialData.autor);
            setCategoria(initialData.categoria);
            setDataPublicacao(initialData.dataPublicacao?.split('T')[0] || '');
            setIsbn(initialData.isbn);
        } else if (!isOpen) {
            setTitulo('');
            setAutor('');
            setCategoria('');
            setDataPublicacao('');
            setIsbn('');
        }
    }, [isOpen, initialData]);

    const handleSubmit = () => {
        if (!titulo || !autor || !categoria || !dataPublicacao || !isbn) {
            alert('Por favor, preencha todos os campos.');
            return;
        }

        onSubmit({ titulo, autor, categoria, dataPublicacao, isbn });
        onClose();
    };

    if (!isOpen) return null;

    return (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4">
            <div className="bg-white rounded-lg shadow-xl w-96 h-[70vh] flex flex-col">
                <div className="sticky top-0 bg-white px-6 py-4 border-b">
                    <h2 className="text-xl font-semibold">{initialData ? 'Editar Livro' : 'Criar Livro'}</h2>
                </div>
                
                <div className="p-6 space-y-6 overflow-y-auto">
                    <div>
                        <label className="block text-sm font-medium mb-2">Título*</label>
                        <input
                            type="text"
                            value={titulo}
                            onChange={(e) => setTitulo(e.target.value)}
                            className="w-full border border-gray-300 rounded-md px-4 py-2 focus:border-indigo-500 focus:ring-1 focus:ring-indigo-500"
                        />
                    </div>
                    <div>
                        <label className="block text-sm font-medium mb-2">Autor*</label>
                        <input
                            type="text"
                            value={autor}
                            onChange={(e) => setAutor(e.target.value)}
                            className="w-full border border-gray-300 rounded-md px-4 py-2 focus:border-indigo-500 focus:ring-1 focus:ring-indigo-500"
                        />
                    </div>
                    <div>
                        <label className="block text-sm font-medium mb-2">Categoria*</label>
                        <input
                            type="text"
                            value={categoria}
                            onChange={(e) => setCategoria(e.target.value)}
                            className="w-full border border-gray-300 rounded-md px-4 py-2 focus:border-indigo-500 focus:ring-1 focus:ring-indigo-500"
                        />
                    </div>
                    <div>
                        <label className="block text-sm font-medium mb-2">Data de Publicação*</label>
                        <input
                            type="date"
                            value={dataPublicacao}
                            onChange={(e) => setDataPublicacao(e.target.value)}
                            className="w-full border border-gray-300 rounded-md px-4 py-2 focus:border-indigo-500 focus:ring-1 focus:ring-indigo-500"
                        />
                    </div>
                    <div>
                        <label className="block text-sm font-medium mb-2">ISBN*</label>
                        <input
                            type="text"
                            value={isbn}
                            onChange={(e) => setIsbn(e.target.value)}
                            className="w-full border border-gray-300 rounded-md px-4 py-2 focus:border-indigo-500 focus:ring-1 focus:ring-indigo-500"
                        />
                    </div>
                </div>

                <div className="sticky bottom-0 bg-gray-50 px-6 py-4 border-t flex justify-end gap-2">
                    <button
                        onClick={onClose}
                        className="px-4 py-2 border border-gray-300 rounded-md hover:bg-gray-100 transition-colors"
                    >
                        Cancelar
                    </button>
                    <button
                        onClick={handleSubmit}
                        className="px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700 transition-colors"
                    >
                        Salvar
                    </button>
                </div>
            </div>
        </div>
    );
};

export default LivroDialog;
