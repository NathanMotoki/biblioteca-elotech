export interface Usuario {
    id: number;
    nome: string;
    email: string;
    telefone: string;
    dataCadastro: string;
}

export interface UsuarioInput {
    nome: string;
    email: string;
    telefone: string;
    dataCadastro: string;
}
