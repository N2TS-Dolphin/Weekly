package com.example.weekly.controller;

import com.example.weekly.model.film.dto.FilmDTO;
import com.example.weekly.model.film.entity.Film;
import com.example.weekly.repository.FilmRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;

@RestController
@RequestMapping("/api/film")
@Validated
@Slf4j
@Tag(name = "Film Controller")
public class FilmController {
    private final FilmRepository filmRepository;

    public FilmController(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Operation(summary = "Get Film Infomation")
    @GetMapping("/{filmId}")
    public ResponseEntity<Film> getFilm(
            @PathVariable Short filmId) {
        Optional<Film> film = filmRepository.findById(filmId);
        return film.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Film> addFilm(
            @Valid @RequestBody FilmDTO filmDTO) {
        Film film = Film.builder()
                .title(filmDTO.getTitle())
                .description(filmDTO.getDescription())
                .releaseYear(filmDTO.getReleaseYear())
                .rentalDuration(filmDTO.getRentalDuration())
                .rentalRate(filmDTO.getRentalRate())
                .length(filmDTO.getLength())
                .replacementCost(filmDTO.getReplacementCost())
                .rating(filmDTO.getRating())
                .lastUpdate(Instant.now())
                .languageId(1)
                .originalLanguageId(null)
                .build();

        Film savedFilm = filmRepository.save(film);
        return new ResponseEntity<>(savedFilm, HttpStatus.CREATED);
    }
}
