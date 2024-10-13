package com.example.weekly.model.film.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FilmDTO {

    @NotBlank(message = "Title is required")
    @Size(max=255, message="Title must not exceed 255 characters")
    private String title;

    @NotBlank(message = "Decription is required")
    private String description;

    @NotNull(message = "Release year is required")
    @Min(value = 1900, message = "Release year must be greater than or equal to 1900")
    private Integer releaseYear;

    @NotNull(message = "Rental duration is required")
    @Min(value = 1, message = "Rental duration must be at least 1")
    private Short rentalDuration;

    @NotNull(message = "Rental rate is required")
    @DecimalMin(value = "0.00", message = "Rental rate must be at least 0.00")
    private BigDecimal rentalRate;

    @NotNull(message = "Length is required")
    @Min(value = 1, message = "Length must be at least 1")
    private Integer length;

    @NotNull(message = "Replacement cost is required")
    @DecimalMin(value = "0.00", message = "Replacement cost must be at least 0.00")
    private BigDecimal replacementCost;

    @NotBlank(message = "Rating is required")
    @Size(max = 5, message = "Rating must not exceed 5 characters")
    private String rating;
}
