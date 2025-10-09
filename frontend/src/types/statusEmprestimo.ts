import type { Usuario } from './usuario';
import type { Livro } from './livro';

export type StatusEmprestimo = 'ATIVO' | 'DEVOLVIDO';

export type Emprestimo = {
    id: number;
    usuario: Usuario;
    livro: Livro;
    dataEmprestimo: string;
    dataDevolucao?: string;
    status: StatusEmprestimo;
};

export type CreateEmprestimoData = {
    usuarioId: number;
    livroId: number;
    dataEmprestimo: string;
};
