package com.emarket.dto;

import java.util.Date;

public record ProductResponseDTO(long id, String title,
                                 String description, Date createdAt,
                                 float rating, float price) {
}
