import { useEffect, useState } from 'react';
import type { Livro } from '../types/livro';
import { Table, TableHead, TableBody, TableRow, TableHeader, TableCell } from '../components/Table/Table';
import { PageContainer, PageTitle } from '../components/Layout/Layout';
import { useLivros } from '../hooks/useLivros';
import { FaBook, FaTrash, FaPen } from 'react-icons/fa';
import LivroDialog from '../components/LivroDialog';

const LivrosPage = () => {
    const { livros, loading, error, fetchLivros, createLivro, updateLivro, deleteLivro } = useLivros();
    const [selectedLivro, setSelectedLivro] = useState<Livro | null>(null);
    const [isDialogOpen, setIsDialogOpen] = useState(false);

    useEffect(() => {
        fetchLivros();
    }, [fetchLivros]);

    const formatDate = (dateString: string) => {
        return new Date(dateString).toLocaleDateString('pt-BR');
    };

    const handleCreateLivro = () => {
        setSelectedLivro(null);
        setIsDialogOpen(true);
    };

    const handleRowClick = (livro: Livro) => {
        setSelectedLivro(livro);
        setIsDialogOpen(true);
    };

    const handleDeleteLivro = async (id: number) => {
        if (window.confirm("Deseja Realmente excluir este livro?")) {
            const result = await deleteLivro(id);
            if (!result.success) {
                alert(result.error);
            } else {
                alert("Livro excluído com sucesso!");
            }
        }
    };

    const handleDialogSubmit = async (livro: { titulo: string; autor: string; categoria: string; dataPublicacao: string; isbn: string }) => {
        const result = await (selectedLivro
            ? updateLivro(selectedLivro.id, livro)
            : createLivro(livro));

        if (result.success) {
            setIsDialogOpen(false);
            setSelectedLivro(null);
            alert(`Livro ${selectedLivro ? 'atualizado' : 'criado'} com sucesso!`);
            fetchLivros();
        } else {
            alert(result.error);
        }
    };

    return (
        <PageContainer>
            <div>
                <div className="bg-white shadow-xl h-[calc(100vh-6rem)]">
                    <div className="px-6 py-4 border-b border-gray-200">
                        <PageTitle>Livros</PageTitle>
                        {error && <p className="text-red-500 mb-4 p-4 bg-red-100 rounded">{error}</p>}
                    </div>

                    <div className="flex mt-2 mb-2 px-6">
                        <button
                            onClick={handleCreateLivro}
                            className="flex items-center gap-2 border border-indigo-600 text-indigo-600 px-4 py-2 rounded hover:bg-indigo-50 transition"
                        >
                            <FaBook />
                            Criar Livro
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
                                            <TableHeader>Título</TableHeader>
                                            <TableHeader>Autor</TableHeader>
                                            <TableHeader>Categoria</TableHeader>
                                            <TableHeader>Data de Publicação</TableHeader>
                                            <TableHeader>ISBN</TableHeader>
                                            <TableHeader>Ações</TableHeader>
                                        </TableRow>
                                    </TableHead>
                                    <TableBody>
                                        {livros.map((livro) => (
                                            <TableRow key={livro.id} onClick={() => handleRowClick(livro)} className="cursor-pointer hover:bg-gray-100">
                                                <TableCell>{livro.id}</TableCell>
                                                <TableCell>{livro.titulo}</TableCell>
                                                <TableCell>{livro.autor}</TableCell>
                                                <TableCell>{livro.categoria}</TableCell>
                                                <TableCell>{formatDate(livro.dataPublicacao)}</TableCell>
                                                <TableCell>{livro.isbn}</TableCell>
                                                <TableCell>
                                                    <div className="flex gap-2">
                                                        <button
                                                            onClick={(e) => {
                                                                e.stopPropagation();
                                                                handleDeleteLivro(livro.id);
                                                            }}
                                                            className="text-red-600 hover:text-red-800 transition"
                                                        >
                                                            <FaTrash />
                                                        </button>
                                                    </div>
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
            <LivroDialog
                isOpen={isDialogOpen}
                onClose={() => setIsDialogOpen(false)}
                onSubmit={handleDialogSubmit}
                initialData={selectedLivro || undefined}
            />
        </PageContainer>
    );
};

export default LivrosPage;
