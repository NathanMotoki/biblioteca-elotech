import { useEffect, useState } from 'react';
import axios from 'axios';
import { Table, TableHead, TableBody, TableRow, TableHeader, TableCell } from '../components/Table/Table';
import { StatusBadge } from '../components/StatusBadge/StatusBadge';
import { PageContainer, PageTitle } from '../components/Layout/Layout';

type Usuario = {
    id: number;
    nome: string;
};

type Livro = {
    id: number;
    titulo: string;
};

type Emprestimo = {
    id: number;
    usuario: Usuario;
    livro: Livro;
    dataEmprestimo: string;
    dataDevolucao?: string;
    status: string;
};

const EmprestimosPage = () => {
    const [emprestimos, setEmprestimos] = useState<Emprestimo[]>([]);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const fetchEmprestimos = async () => {
            try {
                const response = await axios.get<Emprestimo[]>('http://localhost:8080/api/emprestimos');
                console.log("Response data:", response.data);
                setEmprestimos(response.data);
            } catch (err) {
                setError('Erro ao buscar empréstimos.');
                console.error(err);
            }
        };

        fetchEmprestimos();
    }, []);

    const getStatusColor = (status: string) => {
        switch (status) {
            case 'ATIVO':
                return 'bg-blue-100 text-blue-800 px-2 py-1 rounded-full font-medium';
            case 'DEVOLVIDO':
                return 'bg-green-100 text-green-800 px-2 py-1 rounded-full font-medium';
            default:
                return 'bg-gray-100 text-gray-800 px-2 py-1 rounded-full font-medium';
        }
    };

    const formatDate = (dateString: string) => {
        return new Date(dateString).toLocaleDateString('pt-BR');
    };

    return (
        <PageContainer>
            <div>
                <div className="bg-white shadow-xl h-[calc(100vh-6rem)]">
                    <div className="px-6 py-4 border-b border-gray-200">
                        <PageTitle>Empréstimos</PageTitle>
                        {error && <p className="text-red-500 mb-4 p-4 bg-red-100 rounded">{error}</p>}
                    </div>
                    
                    <div className="w-full overflow-x-auto">
                        <div className="w-full align-middle">
                            <Table>
                                <TableHead>
                                    <TableRow>
                                        <TableHeader>ID</TableHeader>
                                        <TableHeader>Usuário</TableHeader>
                                        <TableHeader>Livro</TableHeader>
                                        <TableHeader>Data Empréstimo</TableHeader>
                                        <TableHeader>Data Devolução</TableHeader>
                                        <TableHeader>Status</TableHeader>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {emprestimos.map((emprestimo) => (
                                        <TableRow key={emprestimo.id}>
                                            <TableCell>{emprestimo.id}</TableCell>
                                            <TableCell>{emprestimo.usuario.nome}</TableCell>
                                            <TableCell>{emprestimo.livro.titulo}</TableCell>
                                            <TableCell>{formatDate(emprestimo.dataEmprestimo)}</TableCell>
                                            <TableCell>
                                                {emprestimo.dataDevolucao ? formatDate(emprestimo.dataDevolucao) : '-'}
                                            </TableCell>
                                            <TableCell>
                                                <StatusBadge status={emprestimo.status} />
                                            </TableCell>
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

export default EmprestimosPage;