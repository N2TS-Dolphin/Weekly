package com.example.weekly.controller;

import com.example.weekly.model.actor.entity.Actor;
import com.example.weekly.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Optional;

@RestController()
@RequestMapping("/api/actor")
public class ActorController {
    private final ActorRepository actorRepository;

    @Autowired
    public ActorController(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @GetMapping("/{actorId}")
    public Actor getActor(@PathVariable short actorId) {
        Optional <Actor> actor = actorRepository.findById(actorId);
        return actor.orElse(null);
    }

    @PostMapping
    public ResponseEntity<Actor> createActor(@RequestBody Actor actor) {
        Actor savedActor = actorRepository.save(actor);
        return new ResponseEntity<>(savedActor, HttpStatus.CREATED);
    }

    @PutMapping("/{actorId}")
    public ResponseEntity<Actor> updateActor(@PathVariable Short actorId, @RequestBody Actor actor) {
        Optional <Actor> existActor = actorRepository.findById(actorId);
        if (existActor.isPresent()) {
            Actor updatedActor = existActor.get();

            updatedActor.setFirstName(actor.getFirstName());
            updatedActor.setLastName(actor.getLastName());
            updatedActor.setLastUpdate(Instant.now());

            Actor savedActor = actorRepository.save(updatedActor);

            return new ResponseEntity<>(savedActor, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{actorId}")
    public ResponseEntity<Actor> deleteActor(@PathVariable Short actorId) {
        Optional <Actor> actor = actorRepository.findById(actorId);

        if (actor.isPresent()) {
            actorRepository.deleteById(actorId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
