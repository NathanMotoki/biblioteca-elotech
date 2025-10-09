export interface Livro {
    id: number;
    titulo: string;
    autor: string;
    categoria: string;
    dataPublicacao: string;
    isbn: string;
}

export interface LivroInput {
    titulo: string;
    autor: string;
    categoria: string;
    dataPublicacao: string;
    isbn: string;
}
