import { useEffect, useState, useCallback } from 'react';
import { FaBookOpen, FaUndo } from 'react-icons/fa';
import { Table, TableHead, TableBody, TableRow, TableHeader, TableCell } from '../components/Table/Table';
import { StatusBadge } from '../components/StatusBadge/StatusBadge';
import { PageContainer, PageTitle } from '../components/Layout/Layout';
import EmprestimoDialog from '../components/EmprestimoDialog';
import { useEmprestimos } from '../hooks/useEmprestimos';

const EmprestimosPage = () => {
    const { 
        emprestimos, 
        loading, 
        error, 
        fetchEmprestimos, 
        createEmprestimo, 
        devolverLivro 
    } = useEmprestimos();
    const [isDialogOpen, setIsDialogOpen] = useState(false);

    useEffect(() => {
        fetchEmprestimos();
    }, []);

    const handleCreateEmprestimo = async (data: { usuarioId: number; livroId: number; dataEmprestimo: string }) => {
        const success = await createEmprestimo(data);
        if (success) {
            setIsDialogOpen(false);
            alert('Empréstimo criado com sucesso!');
        }
    };

    const handleDevolucao = async (emprestimoId: number) => {
        if (window.confirm('Deseja confirmar a devolução deste livro?')) {
            await devolverLivro(emprestimoId);
        }
    };

    const formatDate = useCallback((dateString: string) => {
        return new Date(dateString).toLocaleDateString('pt-BR');
    }, []);

    return (
        <PageContainer>
            <div>
                <div className="bg-white shadow-xl h-[calc(100vh-6rem)]">
                    <div className="px-6 py-4 border-b border-gray-200">
                        <PageTitle>Empréstimos</PageTitle>
                        {error && <p className="text-red-500 mb-4 p-4 bg-red-100 rounded">{error}</p>}
                    </div>

                    <div className="flex mt-2 mb-2 px-6">
                        <button
                            onClick={() => setIsDialogOpen(true)}
                            className="flex items-center gap-2 border border-indigo-600 text-indigo-600 px-4 py-2 rounded hover:bg-indigo-50 transition"
                        >
                            <FaBookOpen />
                            Criar Empréstimo
                        </button>
                    </div>
                    
                    {loading ? (
                        <div className="flex justify-center items-center p-8">
                            <div className="animate-spin rounded-full h-8 w-8 border-t-2 border-b-2 border-indigo-600"></div>
                        </div>
                    ) : (
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
                                            <TableHeader>Ações</TableHeader>
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
                                                <TableCell>
                                                    {emprestimo.status === 'ATIVO' && (
                                                        <button
                                                            onClick={() => handleDevolucao(emprestimo.id)}
                                                            className="text-indigo-600 hover:text-indigo-900"
                                                            title="Devolver livro"
                                                        >
                                                            <FaUndo size={16} />
                                                        </button>
                                                    )}
                                                </TableCell>
                                            </TableRow>
                                        ))}
                                    </TableBody>
                                </Table>
                            </div>
                        </div>
                    )}
                </div>
            </div>
            <EmprestimoDialog
                isOpen={isDialogOpen}
                onClose={() => setIsDialogOpen(false)}
                onSubmit={handleCreateEmprestimo}
            />
        </PageContainer>
    );
};

export default EmprestimosPage;