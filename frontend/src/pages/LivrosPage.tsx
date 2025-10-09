import { useEffect, useState } from 'react';
import axios from 'axios';
import { Table, TableHead, TableBody, TableRow, TableHeader, TableCell } from '../components/Table/Table';
import { PageContainer, PageTitle } from '../components/Layout/Layout';

type Livro = {
    id: number;
    titulo: string;
    autor: string;
    categoria: string;
    dataPublicacao: string;
    isbn: string;
};

const LivrosPage = () => {
    const [livros, setLivros] = useState<Livro[]>([]);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const fetchLivros = async () => {
            try {
                const response = await axios.get<Livro[]>('http://localhost:8080/api/livros');
                console.log("Response data:", response.data);
                setLivros(response.data);
            } catch (err) {
                setError('Erro ao buscar livros.');
                console.error(err);
            }
        };

        fetchLivros();
    }, []);

    const formatDate = (dateString: string) => {
        return new Date(dateString).toLocaleDateString('pt-BR');
    };

    return (
        <PageContainer>
            <div>
                <div className="bg-white shadow-xl h-[calc(100vh-6rem)]">
                    <div className="px-6 py-4 border-b border-gray-200">
                        <PageTitle>Livros</PageTitle>
                        {error && <p className="text-red-500 mb-4 p-4 bg-red-100 rounded">{error}</p>}
                    </div>
                    
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
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {livros.map((livro) => (
                                        <TableRow key={livro.id}>
                                            <TableCell>{livro.id}</TableCell>
                                            <TableCell>{livro.titulo}</TableCell>
                                            <TableCell>{livro.autor}</TableCell>
                                            <TableCell>{livro.categoria}</TableCell>
                                            <TableCell>{formatDate(livro.dataPublicacao)}</TableCell>
                                            <TableCell>{livro.isbn}</TableCell>
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

export default LivrosPage;
