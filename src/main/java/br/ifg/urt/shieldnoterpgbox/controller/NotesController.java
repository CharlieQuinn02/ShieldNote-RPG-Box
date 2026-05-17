package br.ifg.urt.shieldnoterpgbox.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ifg.urt.shieldnoterpgbox.dto.request.NotesRequestDTO;
import br.ifg.urt.shieldnoterpgbox.dto.response.NotesResponseDTO;
import br.ifg.urt.shieldnoterpgbox.service.NotesService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/notes")
public class NotesController {

    private final NotesService service;

    public NotesController(NotesService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<NotesResponseDTO>> buscarTodos() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotesResponseDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<NotesResponseDTO> criar(@Valid @RequestBody NotesRequestDTO dto) {
        NotesResponseDTO nova = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nova);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotesResponseDTO> atualizar(
            @PathVariable UUID id, 
            @Valid @RequestBody NotesRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}