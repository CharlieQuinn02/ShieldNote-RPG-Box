package br.ifg.urt.shieldnoterpgbox.controller;

import br.ifg.urt.shieldnoterpgbox.assembler.SpellModelAssembler;
import br.ifg.urt.shieldnoterpgbox.dto.request.SpellRequest;
import br.ifg.urt.shieldnoterpgbox.exception.ExceptionResponse;
import br.ifg.urt.shieldnoterpgbox.dto.response.SpellComplexidadeResponse;
import br.ifg.urt.shieldnoterpgbox.dto.response.SpellResponse;
import br.ifg.urt.shieldnoterpgbox.dto.response.SpellResumoResponse;
import br.ifg.urt.shieldnoterpgbox.service.SpellService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/spells")
@Validated
@Tag(name = "Magias", description = "Endpoints para gerenciamento do catálogo de magias")
public class SpellController {

    private final SpellService spellService;
    private final SpellModelAssembler spellModelAssembler;

    // Construtor manual 
    public SpellController(SpellService spellService, SpellModelAssembler spellModelAssembler) {
        this.spellService = spellService;
        this.spellModelAssembler = spellModelAssembler;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Criar nova magia",
            description = "Cadastra uma nova magia no sistema e retorna o objeto criado com links HATEOAS.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de validação",
                            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public ResponseEntity<EntityModel<SpellResponse>> criar(@Valid @RequestBody SpellRequest request) {
        SpellResponse response = spellService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .cacheControl(CacheControl.noStore())
                .body(spellModelAssembler.toModel(response));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Listar magias",
            description = "Retorna resumo das magias (id, nome, nível). Suporta filtros opcionais.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sucesso",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = SpellResumoResponse.class)))),
                    @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content)
            }
    )
    public ResponseEntity<CollectionModel<EntityModel<SpellResumoResponse>>> listar(
            @RequestParam(required = false) String school,
            @RequestParam(required = false) Integer level,
            @RequestParam(required = false) String nome,
            WebRequest request) {

        Instant lastModified = Instant.now().minus(Duration.ofHours(2));
        if (request.checkNotModified(lastModified.toEpochMilli())) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }

        List<SpellResumoResponse> spells;
        if (school != null) {
            spells = spellService.buscarPorEscola(school);
        } else if (level != null) {
            spells = spellService.buscarPorNivel(level);
        } else if (nome != null) {
            spells = spellService.buscarPorNome(nome);
        } else {
            spells = spellService.listarTodos();
        }

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(5, TimeUnit.MINUTES))
                .lastModified(lastModified.toEpochMilli())
                .body(spellModelAssembler.toResumoCollection(spells));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Buscar magia por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sucesso"),
                    @ApiResponse(responseCode = "404", description = "Magia não encontrada",
                            content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(responseCode = "400", description = "ID inválido",
                            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public ResponseEntity<EntityModel<SpellResponse>> buscarPorId(
            @PathVariable UUID id,
            @RequestHeader(value = "If-None-Match", required = false) String ifNoneMatch,
            WebRequest request) {

        SpellResponse spell = spellService.buscarPorId(id);
        String eTag = Integer.toHexString(spell.hashCode());

        if (eTag.equals(ifNoneMatch)) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).eTag(eTag).build();
        }

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES))
                .eTag(eTag)
                .body(spellModelAssembler.toModel(spell));
    }

    @GetMapping(value = "/{id}/complexidade", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Relatório de complexidade",
            description = "Retorna índice de complexidade calculado na entidade (nível × 10).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sucesso",
                            content = @Content(schema = @Schema(implementation = SpellComplexidadeResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Magia não encontrada",
                            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public ResponseEntity<SpellComplexidadeResponse> buscarComplexidade(@PathVariable UUID id) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES))
                .body(spellService.getRelatorioComplexidade(id));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Atualizar magia",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Atualizado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Magia não encontrada",
                            content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos",
                            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public ResponseEntity<EntityModel<SpellResponse>> atualizar(
            @PathVariable UUID id,
            @Valid @RequestBody SpellRequest request) {
        SpellResponse response = spellService.atualizar(id, request);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noStore())
                .body(spellModelAssembler.toModel(response));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir magia",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Excluído com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Magia não encontrada",
                            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        spellService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}