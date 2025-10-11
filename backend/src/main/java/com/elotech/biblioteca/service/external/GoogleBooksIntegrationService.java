package com.elotech.biblioteca.service.external;

import com.elotech.biblioteca.dto.external.GoogleBooksApiResponse;
import com.elotech.biblioteca.dto.external.GoogleBookResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class GoogleBooksIntegrationService {

    private final RestTemplate restTemplate;
    private static final String GOOGLE_BOOKS_API_URL = "https://www.googleapis.com/books/v1/volumes";

    public GoogleBooksIntegrationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public List<GoogleBookResponse> buscarLivros(String query) {
        try {
            String url = GOOGLE_BOOKS_API_URL + "?q=" + query;
            
            GoogleBooksApiResponse response = restTemplate.getForObject(url, GoogleBooksApiResponse.class);
            
            if (response != null && response.getItems() != null) {
                return response.getItems();
            }

            return Collections.emptyList();
            
        } catch (RestClientException e) {
            throw new RuntimeException("Erro ao comunicar com o serviço de busca de livros", e);
        } catch (Exception e) {
            throw new RuntimeException("Erro interno do servidor", e);
        }
    }
}