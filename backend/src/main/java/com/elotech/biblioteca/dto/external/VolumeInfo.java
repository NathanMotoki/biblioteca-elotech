package com.elotech.biblioteca.dto.external;

import lombok.Data;
import java.util.List;

@Data
public class VolumeInfo {
    private String title;
    private List<String> authors;
    private List<String> categories;
    private String publishedDate;
    private List<IndustryIdentifier> industryIdentifiers;
}