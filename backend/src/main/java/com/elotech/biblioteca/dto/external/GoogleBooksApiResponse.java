package com.elotech.biblioteca.dto.external;

import lombok.Data;
import java.util.List;

@Data
public class GoogleBooksApiResponse {
    private List<GoogleBookResponse> items;
}