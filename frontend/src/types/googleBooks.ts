// Tipos para a resposta da API do Google Books
export interface GoogleBookResult {
    id: string;
    volumeInfo: {
        title: string;
        authors?: string[];
        categories?: string[];
        publishedDate?: string;
        industryIdentifiers?: Array<{
            type: string;
            identifier: string;
        }>;
    };
}
