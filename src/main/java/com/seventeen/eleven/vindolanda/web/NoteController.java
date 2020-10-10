package com.seventeen.eleven.vindolanda.web;

import com.seventeen.eleven.vindolanda.model.Note;
import com.seventeen.eleven.vindolanda.model.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class NoteController {

    private final Logger log = LoggerFactory.getLogger(NoteController.class);
    private NoteRepository noteRepository;

    public NoteController(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @GetMapping("/notes")
    Collection<Note> notes() {
        return noteRepository.findAll();
    }

    @GetMapping("/note/{id}")
    ResponseEntity<?> getNote(@PathVariable long id) {
        Optional<Note> note = noteRepository.findById(id);
        return note.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/note")
    ResponseEntity<Note> createNote(@Valid @RequestBody Note note) throws URISyntaxException {
        Note result = noteRepository.save(note);
        log.info("Request to create note: {}", note);

        return ResponseEntity.created(new URI("/api/note/" + result.getId()))
                .body(result);
    }

    @PutMapping("/note/{id}")
    ResponseEntity<Note> updateNote(@Valid @RequestBody Note note, @PathVariable Long id) {
        log.info("Request to update note: {}", id);

        return ResponseEntity.ok().body(noteRepository.findById(id)
                .map(result -> {
                    result.setName(note.getName());
                    result.setText(note.getText());
                    return noteRepository.save(result);
                }).orElseGet(() -> noteRepository.save(note)));
    }

    @DeleteMapping("/note/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable Long id) {
        log.info("Request to delete note: {}", id);
        noteRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
