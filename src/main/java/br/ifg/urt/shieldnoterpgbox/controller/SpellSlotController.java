package br.ifg.urt.shieldnoterpgbox.controller;

import br.ifg.urt.shieldnoterpgbox.assembler.SpellSlotModelAssembler;
import br.ifg.urt.shieldnoterpgbox.dto.request.SpellSlotRequest;
import br.ifg.urt.shieldnoterpgbox.exception.ExceptionResponse;
import br.ifg.urt.shieldnoterpgbox.dto.response.SpellSlotResponse;
import br.ifg.urt.shieldnoterpgbox.service.SpellSlotService;
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
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/spellslots")
@Validated
@Tag(name = "Slots de Magia", description = "Endpoints para gerenciamento de slots de conjuração")
public class SpellSlotController {

    private final SpellSlotService spellSlotService;
    private final SpellSlotModelAssembler spellSlotModelAssembler;

    // Construtor manual 
    public SpellSlotController(SpellSlotService spellSlotService, SpellSlotModelAssembler spellSlotModelAssembler) {
        this.spellSlotService = spellSlotService;
        this.spellSlotModelAssembler = spellSlotModelAssembler;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Criar slot de magia", responses = {
            @ApiResponse(responseCode = "201", description = "Criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<EntityModel<SpellSlotResponse>> criar(
            @Valid @RequestBody SpellSlotRequest request) {
        SpellSlotResponse response = spellSlotService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(spellSlotModelAssembler.toModel(response));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Buscar slot por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "404", description = "Slot não encontrado",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<EntityModel<SpellSlotResponse>> buscarPorId(@PathVariable UUID id) {
        SpellSlotResponse response = spellSlotService.buscarPorId(id);
        return ResponseEntity.ok(spellSlotModelAssembler.toModel(response));
    }

    @GetMapping(value = "/spellcasting/{spellCastingId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Listar slots por ficha de conjuração", responses = {
            @ApiResponse(responseCode = "200", description = "Sucesso")
    })
    public ResponseEntity<List<EntityModel<SpellSlotResponse>>> buscarPorSpellCasting(
            @PathVariable UUID spellCastingId) {
        List<EntityModel<SpellSlotResponse>> models = spellSlotService.buscarPorSpellCasting(spellCastingId).stream()
                .map(spellSlotModelAssembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(models);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Atualizar slot", responses = {
            @ApiResponse(responseCode = "200", description = "Atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Slot não encontrado",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<EntityModel<SpellSlotResponse>> atualizar(
            @PathVariable UUID id,
            @Valid @RequestBody SpellSlotRequest request) {
        SpellSlotResponse response = spellSlotService.atualizar(id, request);
        return ResponseEntity.ok(spellSlotModelAssembler.toModel(response));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir slot", responses = {
            @ApiResponse(responseCode = "204", description = "Excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Slot não encontrado",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        spellSlotService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}