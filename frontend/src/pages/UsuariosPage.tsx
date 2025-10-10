import { useEffect, useState } from 'react';
import type { Usuario } from '../types/usuario';
import { Table, TableHead, TableBody, TableRow, TableHeader, TableCell } from '../components/Table/Table';
import { PageContainer, PageTitle } from '../components/Layout/Layout';
import { FaUserPlus, FaTrash, FaBook } from 'react-icons/fa';
import UserDialog from '../components/UserDialog';
import RecomendacoesDialog from '../components/RecomendacoesDialog';
import { useUsuarios } from '../hooks/useUsuarios';

const UsuariosPage = () => {
    const { usuarios, loading, error, fetchUsuarios, createUsuario, updateUsuario, deleteUsuario } = useUsuarios();
    const [isDialogOpen, setIsDialogOpen] = useState(false);
    const [isRecomendacoesDialogOpen, setIsRecomendacoesDialogOpen] = useState(false);
    const [selectedUser, setSelectedUser] = useState<Usuario | null>(null);
    const [selectedUserForRecomendacoes, setSelectedUserForRecomendacoes] = useState<Usuario | null>(null);

    useEffect(() => {
        fetchUsuarios();
    }, [fetchUsuarios]);

    const formatDate = (dateString: string) => {
        return new Date(dateString).toLocaleDateString('pt-BR');
    };

    const handleCreateUser = () => {
        setSelectedUser(null);
        setIsDialogOpen(true);
    };

    const closeDialog = () => {
        setSelectedUser(null);
        setIsDialogOpen(false);
    };

    const handleDeleteUser = async (id: number) => {
        if (window.confirm("Tem certeza que deseja excluir este usuário?")) {
            const result = await deleteUsuario(id);
            if (!result.success) {
                alert(result.error);
            } else {
                alert('Usuário excluído com sucesso!');
            }
        }
    };

    const handleRecomendacao = async (usuario: Usuario) => {
        setSelectedUserForRecomendacoes(usuario);
        setIsRecomendacoesDialogOpen(true);
    }

    const handleDialogSubmit = async (user: { nome: string; email: string; telefone: string; dataCadastro: string }) => {
        const result = await (selectedUser
            ? updateUsuario(selectedUser.id, user)
            : createUsuario(user));

        if (result.success) {
            closeDialog();
            alert('Operação realizada com sucesso!');
        } else {
            alert(result.error);
        }
    };

    const handleRowClick = (usuario: Usuario) => {
        setSelectedUser(usuario);
        setIsDialogOpen(true);
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
                            {loading ? (
                                <div className="flex justify-center items-center p-8">
                                    <div className="animate-spin rounded-full h-8 w-8 border-t-2 border-b-2 border-indigo-600"></div>
                                </div>
                            ) : (
                                <Table>
                                    <TableHead>
                                        <TableRow>
                                            <TableHeader>ID</TableHeader>
                                            <TableHeader>Nome</TableHeader>
                                            <TableHeader>Email</TableHeader>
                                            <TableHeader>Telefone</TableHeader>
                                            <TableHeader>Data de Cadastro</TableHeader>
                                            <TableHeader>Ações</TableHeader>
                                        </TableRow>
                                    </TableHead>
                                    <TableBody>
                                        {usuarios.map((usuario) => (
                                            <TableRow key={usuario.id} onClick={() => handleRowClick(usuario)} className="cursor-pointer hover:bg-gray-100">
                                                <TableCell>{usuario.id}</TableCell>
                                                <TableCell>{usuario.nome}</TableCell>
                                                <TableCell>{usuario.email}</TableCell>
                                                <TableCell>{usuario.telefone || '-'}</TableCell>
                                                <TableCell>{formatDate(usuario.dataCadastro)}</TableCell>
                                                <TableCell>
                                                    <button
                                                        onClick={(e) => {
                                                            e.stopPropagation();
                                                            handleRecomendacao(usuario);
                                                        }}
                                                        className="text-blue-600 hover:text-blue-800 transition"
                                                    >
                                                        <FaBook />
                                                    </button>
                                                    <button
                                                        onClick={(e) => {
                                                            e.stopPropagation();
                                                            handleDeleteUser(usuario.id);
                                                        }}
                                                        className="text-red-600 hover:text-red-800 transition"
                                                    >
                                                        <FaTrash />
                                                    </button>
                                                </TableCell>
                                            </TableRow>
                                        ))}
                                    </TableBody>
                                </Table>
                            )}
                        </div>
                    </div>
                </div>
            </div>
            <UserDialog
                isOpen={isDialogOpen}
                onClose={closeDialog}
                onSubmit={handleDialogSubmit}
                initialData={selectedUser || undefined}
            />
            <RecomendacoesDialog
                isOpen={isRecomendacoesDialogOpen}
                onClose={() => setIsRecomendacoesDialogOpen(false)}
                usuario={selectedUserForRecomendacoes}
            />
        </PageContainer>
    );
};

export default UsuariosPage;
