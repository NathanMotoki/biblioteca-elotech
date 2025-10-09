import { useEffect, useState } from 'react';
import axios from 'axios';
import { Table, TableHead, TableBody, TableRow, TableHeader, TableCell } from '../components/Table/Table';
import { PageContainer, PageTitle } from '../components/Layout/Layout';
import { FaUserPlus } from 'react-icons/fa';

type Usuario = {
    id: number;
    nome: string;
    email: string;
    telefone: string;
    dataCadastro: string;
};

const UsuariosPage = () => {
    const [usuarios, setUsuarios] = useState<Usuario[]>([]);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const fetchUsuarios = async () => {
            try {
                const response = await axios.get<Usuario[]>('http://localhost:8080/api/usuarios');
                setUsuarios(response.data);
            } catch (err) {
                setError('Erro ao buscar usuários.');
                console.error(err);
            }
        };

        fetchUsuarios();
    }, []);

    const formatDate = (dateString: string) => {
        return new Date(dateString).toLocaleDateString('pt-BR');
    };

    const handleCreateUser = () => {
        // Placeholder for creating a user
        console.log('Criar Usuário button clicked');
    };

    return (
        <PageContainer>
            <div>
                <div className="bg-white shadow-xl h-[calc(100vh-6rem)]">
                    <div className="px-6 py-4 border-b border-gray-200">
                        <PageTitle>Usuários</PageTitle>
                    </div>
                    {error && <p className="text-red-500 mb-4 p-4 bg-red-100 rounded">{error}</p>}
                    
                    <div className="flex mt-2 mb-2 px-6">
                        <button
                            onClick={handleCreateUser}
                            className="flex items-center gap-2 border border-indigo-600 text-indigo-600 px-4 py-2 rounded hover:bg-indigo-50 transition"
                        >
                            <FaUserPlus />
                            Criar Usuário
                        </button>
                    </div>
                    <div className="w-full overflow-x-auto">
                        <div className="w-full align-middle">
                            <Table>
                                <TableHead>
                                    <TableRow>
                                        <TableHeader>ID</TableHeader>
                                        <TableHeader>Nome</TableHeader>
                                        <TableHeader>Email</TableHeader>
                                        <TableHeader>Telefone</TableHeader>
                                        <TableHeader>Data de Cadastro</TableHeader>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {usuarios.map((usuario) => (
                                        <TableRow key={usuario.id}>
                                            <TableCell>{usuario.id}</TableCell>
                                            <TableCell>{usuario.nome}</TableCell>
                                            <TableCell>{usuario.email}</TableCell>
                                            <TableCell>{usuario.telefone || '-'}</TableCell>
                                            <TableCell>{formatDate(usuario.dataCadastro)}</TableCell>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </div>
                    </div>
                </div>
            </div>
        </PageContainer>
    );
};

export default UsuariosPage;
