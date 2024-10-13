package com.example.weekly.controller;

import com.example.weekly.model.film.dto.FilmDTO;
import com.example.weekly.model.film.entity.Film;
import com.example.weekly.repository.FilmRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Optional;

@RestController
@RequestMapping("/api/film")
public class FilmController {
    private final FilmRepository filmRepository;

    public FilmController(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @GetMapping("/{filmId}")
    public Film getFilm(@PathVariable Short filmId) {
        Optional<Film> film = filmRepository.findById(filmId);
        return film.orElse(null);
    }

    @PostMapping
    public ResponseEntity<Film> createFilm(@Valid @RequestBody FilmDTO filmDTO) {
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
