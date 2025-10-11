package com.elotech.biblioteca.dto.external;

import lombok.Data;

@Data
public class GoogleBookResponse {
    private String id;
    private VolumeInfo volumeInfo;
}