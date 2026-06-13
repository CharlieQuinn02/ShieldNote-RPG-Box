package br.ifg.urt.shieldnoterpgbox.controller;

import br.ifg.urt.shieldnoterpgbox.assembler.SpellCastingModelAssembler;
import br.ifg.urt.shieldnoterpgbox.dto.request.ConjurarRequest;
import br.ifg.urt.shieldnoterpgbox.dto.request.SpellCastingRequest;
import br.ifg.urt.shieldnoterpgbox.exception.ExceptionResponse;
import br.ifg.urt.shieldnoterpgbox.dto.response.SpellCastingResponse;
import br.ifg.urt.shieldnoterpgbox.service.SpellCastingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/spellcastings")
@Validated
@Tag(name = "Conjuração", description = "Endpoints para fichas de conjuração e ações de magia")
public class SpellCastingController {

    private final SpellCastingService spellCastingService;
    private final SpellCastingModelAssembler spellCastingModelAssembler;

    // Construtor manual (Substitui o Lombok)
    public SpellCastingController(SpellCastingService spellCastingService, SpellCastingModelAssembler spellCastingModelAssembler) {
        this.spellCastingService = spellCastingService;
        this.spellCastingModelAssembler = spellCastingModelAssembler;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Criar ficha de conjuração", responses = {
            @ApiResponse(responseCode = "201", description = "Criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<EntityModel<SpellCastingResponse>> criar(
            @Valid @RequestBody SpellCastingRequest request) {
        SpellCastingResponse response = spellCastingService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(spellCastingModelAssembler.toModel(response));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Listar fichas de conjuração", responses = {
            @ApiResponse(responseCode = "200", description = "Sucesso")
    })
    public ResponseEntity<List<EntityModel<SpellCastingResponse>>> listarTodos() {
        List<EntityModel<SpellCastingResponse>> models = spellCastingService.listarTodos().stream()
                .map(spellCastingModelAssembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(models);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Buscar ficha por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "404", description = "Não encontrado",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<EntityModel<SpellCastingResponse>> buscarPorId(@PathVariable UUID id) {
        SpellCastingResponse response = spellCastingService.buscarPorId(id);
        return ResponseEntity.ok(spellCastingModelAssembler.toModel(response));
    }

    @GetMapping(value = "/character/{characterId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Buscar fichas por personagem", responses = {
            @ApiResponse(responseCode = "200", description = "Sucesso")
    })
    public ResponseEntity<List<EntityModel<SpellCastingResponse>>> buscarPorCharacter(
            @PathVariable UUID characterId) {
        List<EntityModel<SpellCastingResponse>> models = spellCastingService.buscarPorCharacter(characterId).stream()
                .map(spellCastingModelAssembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(models);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Atualizar ficha de conjuração", responses = {
            @ApiResponse(responseCode = "200", description = "Atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não encontrado",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<EntityModel<SpellCastingResponse>> atualizar(
            @PathVariable UUID id,
            @Valid @RequestBody SpellCastingRequest request) {
        SpellCastingResponse response = spellCastingService.atualizar(id, request);
        return ResponseEntity.ok(spellCastingModelAssembler.toModel(response));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir ficha de conjuração", responses = {
            @ApiResponse(responseCode = "204", description = "Excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não encontrado",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        spellCastingService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/conjurar", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Conjurar magia", responses = {
            @ApiResponse(responseCode = "200", description = "Conjuração realizada"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "422", description = "Regra de negócio violada",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<Map<String, Object>> conjurar(
            @PathVariable UUID id,
            @Valid @RequestBody ConjurarRequest request) {

        boolean sucesso = spellCastingService.conjurar(id, request);
        return ResponseEntity.ok(Map.of(
                "sucesso", sucesso,
                "spellId", request.spellId(),
                "nivelSlot", request.nivelSlot(),
                "mensagem", "Magia conjurada com sucesso!"
        ));
    }

    @PostMapping(value = "/{id}/recuperar-slots", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Recuperar slots (descanso longo)", responses = {
            @ApiResponse(responseCode = "200", description = "Slots recuperados")
    })
    public ResponseEntity<EntityModel<SpellCastingResponse>> recuperarSlots(@PathVariable UUID id) {
        SpellCastingResponse response = spellCastingService.recuperarSlots(id);
        return ResponseEntity.ok(spellCastingModelAssembler.toModel(response));
    }

    @PostMapping(value = "/{id}/magias/{spellId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Adicionar magia conhecida", responses = {
            @ApiResponse(responseCode = "200", description = "Magia adicionada")
    })
    public ResponseEntity<EntityModel<SpellCastingResponse>> adicionarMagia(
            @PathVariable UUID id,
            @PathVariable UUID spellId) {
        SpellCastingResponse response = spellCastingService.adicionarMagia(id, spellId);
        return ResponseEntity.ok(spellCastingModelAssembler.toModel(response));
    }

    @DeleteMapping(value = "/{id}/magias/{spellId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Remover magia conhecida", responses = {
            @ApiResponse(responseCode = "200", description = "Magia removida")
    })
    public ResponseEntity<EntityModel<SpellCastingResponse>> removerMagia(
            @PathVariable UUID id,
            @PathVariable UUID spellId) {
        SpellCastingResponse response = spellCastingService.removerMagia(id, spellId);
        return ResponseEntity.ok(spellCastingModelAssembler.toModel(response));
    }
}