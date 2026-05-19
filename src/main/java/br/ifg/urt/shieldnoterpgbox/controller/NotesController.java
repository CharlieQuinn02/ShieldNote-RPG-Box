package br.ifg.urt.shieldnoterpgbox.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ifg.urt.shieldnoterpgbox.dto.request.NotesRequestDTO;
import br.ifg.urt.shieldnoterpgbox.dto.response.NotesResponseDTO;
import br.ifg.urt.shieldnoterpgbox.service.NotesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/notes")
@Tag(name = "Anotações", description = "Endpoints para criação e gerenciamento de notas e Post-its das sessões")
public class NotesController {

    private final NotesService service;

    public NotesController(NotesService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todas as notas", description = "Retorna uma lista com todas as anotações cadastradas no sistema.")
    @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso")
    @GetMapping
    public ResponseEntity<List<NotesResponseDTO>> buscarTodos() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Buscar nota por ID", description = "Retorna os detalhes de uma nota específica através do seu identificador único (UUID).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Nota encontrada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Nota não encontrada no banco de dados"),
        @ApiResponse(responseCode = "400", description = "Formato de UUID inválido na URL")
    })
    @GetMapping("/{id}")
    public ResponseEntity<NotesResponseDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Criar nova nota", description = "Cadastra uma nova anotação vinculada a uma sessão ou campanha.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Nota criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados (ex: título ou conteúdo em branco)")
    })
    @PostMapping
    public ResponseEntity<NotesResponseDTO> criar(@Valid @RequestBody NotesRequestDTO dto) {
        NotesResponseDTO nova = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nova);
    }

    @Operation(summary = "Atualizar nota existente", description = "Atualiza o conteúdo, título ou categoria de uma nota através do seu UUID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Nota atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Nota não encontrada no banco de dados"),
        @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados")
    })
    @PutMapping("/{id}")
    public ResponseEntity<NotesResponseDTO> atualizar(
            @PathVariable UUID id, 
            @Valid @RequestBody NotesRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Remover nota", description = "Exclui permanentemente uma anotação do banco de dados pelo seu UUID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Nota excluída com sucesso (sem conteúdo de retorno)"),
        @ApiResponse(responseCode = "404", description = "Nota não encontrada no banco de dados")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/alternar-fixacao")
    public ResponseEntity<NotesResponseDTO> alternarFixacao(@PathVariable UUID id) {
        NotesResponseDTO response = service.alternarFixacao(id);
        return ResponseEntity.ok(response);
    }
}