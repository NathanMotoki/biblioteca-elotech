import React, { useState, useEffect } from 'react';

interface UserDialogProps {
    isOpen: boolean;
    onClose: () => void;
    onSubmit: (user: { nome: string; email: string; telefone: string; dataCadastro: string }) => void;
    initialData?: { nome: string; email: string; telefone: string; dataCadastro: string };
}

const UserDialog: React.FC<UserDialogProps> = ({ isOpen, onClose, onSubmit, initialData }) => {
    const [nome, setNome] = useState(initialData?.nome || '');
    const [email, setEmail] = useState(initialData?.email || '');
    const [telefone, setTelefone] = useState(initialData?.telefone || '');
    const [dataCadastro, setDataCadastro] = useState(initialData?.dataCadastro || '');

    useEffect(() => {
        if (isOpen && initialData) {
            setNome(initialData.nome);
            setEmail(initialData.email);
            setTelefone(initialData.telefone);
            setDataCadastro(initialData.dataCadastro?.split('T')[0] || '');
        } else if (!isOpen) {
            setNome('');
            setEmail('');
            setTelefone('');
            setDataCadastro('');
        }
    }, [isOpen, initialData]);

    const handleSubmit = () => {
        if (!nome || !email || !telefone || !dataCadastro) {
            alert('Por favor, preencha todos os campos.');
            return;
        }

        if(dataCadastro > new Date().toISOString().split('T')[0]) {
            alert('A data de cadastro não pode ser futura.');
            return;
        }

        onSubmit({ nome, email, telefone, dataCadastro });
        onClose();
    };

    if (!isOpen) return null;

    return (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
            <div className="bg-white p-6 rounded shadow-lg w-96">
                <h2 className="text-xl font-semibold mb-4">{initialData ? 'Editar Usuário' : 'Criar Usuário'}</h2>
                <div className="mb-4">
                    <label className="block text-sm font-medium mb-1">Nome*</label>
                    <input
                        type="text"
                        value={nome}
                        onChange={(e) => setNome(e.target.value)}
                        className="w-full border border-gray-300 rounded px-3 py-2"
                    />
                </div>
                <div className="mb-4">
                    <label className="block text-sm font-medium mb-1">Email*</label>
                    <input
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        className="w-full border border-gray-300 rounded px-3 py-2"
                    />
                </div>
                <div className="mb-4">
                    <label className="block text-sm font-medium mb-1">Telefone*</label>
                    <input
                        type="text"
                        value={telefone}
                        onChange={(e) => setTelefone(e.target.value)}
                        className="w-full border border-gray-300 rounded px-3 py-2"
                    />
                </div>
                <div className="mb-4">
                    <label className="block text-sm font-medium mb-1">Data de Cadastro*</label>
                    <input
                        type="date"
                        value={dataCadastro}
                        onChange={(e) => setDataCadastro(e.target.value)}
                        className="w-full border border-gray-300 rounded px-3 py-2"
                    />
                </div>
                <div className="flex justify-end gap-2">
                    <button
                        onClick={onClose}
                        className="px-4 py-2 border border-gray-300 rounded hover:bg-gray-100"
                    >
                        Cancelar
                    </button>
                    <button
                        onClick={handleSubmit}
                        className="px-4 py-2 bg-indigo-600 text-white rounded hover:bg-indigo-700"
                    >
                        Salvar
                    </button>
                </div>
            </div>
        </div>
    );
};

export default UserDialog;
