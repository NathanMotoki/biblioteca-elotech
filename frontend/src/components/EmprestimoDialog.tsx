import React, { useState, useEffect } from 'react';
import axios from 'axios';

interface Usuario {
    id: number;
    nome: string;
}

interface Livro {
    id: number;
    titulo: string;
}

interface EmprestimoDialogProps {
    isOpen: boolean;
    onClose: () => void;
    onSubmit: (emprestimo: { usuarioId: number; livroId: number; dataEmprestimo: string }) => void;
}

const EmprestimoDialog: React.FC<EmprestimoDialogProps> = ({ isOpen, onClose, onSubmit }) => {
    const [usuarios, setUsuarios] = useState<Usuario[]>([]);
    const [livros, setLivros] = useState<Livro[]>([]);
    const [selectedUsuario, setSelectedUsuario] = useState<number>(0);
    const [selectedLivro, setSelectedLivro] = useState<number>(0);
    const [dataEmprestimo, setDataEmprestimo] = useState<string>('');
    const [error, setError] = useState<string>('');

    useEffect(() => {
        if (isOpen) {
            fetchUsuarios();
            fetchLivros();
            // Set default date to today
            const today = new Date().toISOString().split('T')[0];
            setDataEmprestimo(today);
        } else {
            // Reset form
            setSelectedUsuario(0);
            setSelectedLivro(0);
            setDataEmprestimo('');
            setError('');
        }
    }, [isOpen]);

    const fetchUsuarios = async () => {
        try {
            const response = await axios.get<Usuario[]>('http://localhost:8080/api/usuarios');
            setUsuarios(response.data);
        } catch (err) {
            setError('Erro ao carregar usuários');
        }
    };

    const fetchLivros = async () => {
        try {
            const response = await axios.get<Livro[]>('http://localhost:8080/api/livros');
            // Filter only books that are available (not currently borrowed)
            const availableBooks = response.data;
            setLivros(availableBooks);
        } catch (err) {
            setError('Erro ao carregar livros');
        }
    };

    const handleSubmit = () => {
        setError('');

        // Validations
        if (!selectedUsuario) {
            setError('Selecione um usuário');
            return;
        }
        if (!selectedLivro) {
            setError('Selecione um livro');
            return;
        }
        if (!dataEmprestimo) {
            setError('Selecione a data do empréstimo');
            return;
        }

        // Validate that loan date is not in the future
        const loanDate = new Date(dataEmprestimo);
        const today = new Date();
        today.setHours(0, 0, 0, 0);
        if (loanDate > today) {
            setError('A data de empréstimo não pode ser maior que o dia atual');
            return;
        }

        onSubmit({
            usuarioId: selectedUsuario,
            livroId: selectedLivro,
            dataEmprestimo
        });
        onClose();
    };

    if (!isOpen) return null;

    return (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4">
            <div className="bg-white rounded-lg shadow-xl w-full max-w-2xl h-[80vh] flex flex-col">
                <div className="sticky top-0 bg-white px-6 py-4 border-b">
                    <h2 className="text-xl font-semibold">Novo Empréstimo</h2>
                </div>
                
                <div className="p-6 space-y-6 overflow-y-auto">
                    {error && (
                        <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded">
                            {error}
                        </div>
                    )}
                    
                    <div>
                        <label className="block text-sm font-medium mb-2">Usuário*</label>
                        <select
                            value={selectedUsuario}
                            onChange={(e) => setSelectedUsuario(Number(e.target.value))}
                            className="w-full border border-gray-300 rounded-md px-4 py-2 focus:border-indigo-500 focus:ring-1 focus:ring-indigo-500"
                        >
                            <option value={0}>Selecione um usuário</option>
                            {usuarios.map((usuario) => (
                                <option key={usuario.id} value={usuario.id}>
                                    {usuario.nome}
                                </option>
                            ))}
                        </select>
                    </div>

                    <div>
                        <label className="block text-sm font-medium mb-2">Livro*</label>
                        <select
                            value={selectedLivro}
                            onChange={(e) => setSelectedLivro(Number(e.target.value))}
                            className="w-full border border-gray-300 rounded-md px-4 py-2 focus:border-indigo-500 focus:ring-1 focus:ring-indigo-500"
                        >
                            <option value={0}>Selecione um livro</option>
                            {livros.map((livro) => (
                                <option key={livro.id} value={livro.id}>
                                    {livro.titulo}
                                </option>
                            ))}
                        </select>
                    </div>

                    <div>
                        <label className="block text-sm font-medium mb-2">Data do Empréstimo*</label>
                        <input
                            type="date"
                            value={dataEmprestimo}
                            onChange={(e) => setDataEmprestimo(e.target.value)}
                            max={new Date().toISOString().split('T')[0]}
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

export default EmprestimoDialog;
