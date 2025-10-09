import type { Usuario } from './usuario';
import type { Livro } from './livro';

export type Emprestimo = {
    id: number;
    usuario: Usuario;
    livro: Livro;
    dataEmprestimo: string;
    dataDevolucao?: string;
    status: string;
};

export type CreateEmprestimoData = {
    usuarioId: number;
    livroId: number;
    dataEmprestimo: string;
};
