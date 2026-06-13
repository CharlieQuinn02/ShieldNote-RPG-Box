package br.ifg.urt.shieldnoterpgbox.controller;
import org.springdoc.core.annotations.ParameterObject;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated; 
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ifg.urt.shieldnoterpgbox.assembler.NotesModelAssembler; 
import br.ifg.urt.shieldnoterpgbox.dto.request.NotesRequestDTO;
import br.ifg.urt.shieldnoterpgbox.dto.response.NotesResponseDTO;
import br.ifg.urt.shieldnoterpgbox.enums.PostItCat;
import br.ifg.urt.shieldnoterpgbox.service.NotesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Validated 
@RequestMapping("/notes")
@Tag(name = "Anotações", description = "Endpoints para criação e gerenciamento de notas e Post-its das sessões")
public class NotesController {

    private final NotesService service;
    private final NotesModelAssembler assembler; // adicionando para o HATEOAS

    public NotesController(NotesService service, NotesModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @Operation(summary = "Listar todas as notas com paginação, filtros e HATEOAS", description = "Retorna uma página de anotações enriquecidas com hiperlinks de navegação.")
    @ApiResponse(responseCode = "200", description = "Página com links recuperada com sucesso")
    @GetMapping
    public ResponseEntity<Page<EntityModel<NotesResponseDTO>>> buscarTodos(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) PostItCat categoria,
            @ParameterObject @PageableDefault(size = 10, sort = "criadoEm", direction = Sort.Direction.DESC) Pageable pageable) {
        
        Page<NotesResponseDTO> dtos = service.buscarTodas(titulo, categoria, pageable);
        
        // Mapeia cada elemento DTO da página para conter os links do assembler
        Page<EntityModel<NotesResponseDTO>> paginaComLinks = dtos.map(assembler::toModel);
        return ResponseEntity.ok(paginaComLinks);
    }

    @Operation(summary = "Buscar nota por ID com HATEOAS", description = "Retorna os detalhes de uma nota específica acompanhada de seus links de ação.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Nota e links retornados com sucesso"),
        @ApiResponse(responseCode = "404", description = "Nota não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<NotesResponseDTO>> buscarPorId(@PathVariable UUID id) {
        NotesResponseDTO response = service.findById(id);
        // Envolve o objeto com os links antes de responder 
        return ResponseEntity.ok(assembler.toModel(response));
    }

    @Operation(summary = "Criar nova nota", description = "Cadastra uma anotação e retorna a sua representação hypermedia inicial.")
    @PostMapping
    public ResponseEntity<EntityModel<NotesResponseDTO>> criar(@Valid @RequestBody NotesRequestDTO dto) {
        NotesResponseDTO nova = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nova));
    }

    @Operation(summary = "Atualizar nota existente", description = "Atualiza o conteúdo da nota e renova os links de navegação.")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<NotesResponseDTO>> atualizar(
            @PathVariable UUID id, 
            @Valid @RequestBody NotesRequestDTO dto) { 
        NotesResponseDTO atualizada = service.update(id, dto);
        return ResponseEntity.ok(assembler.toModel(atualizada));
    }

    @Operation(summary = "Remover nota", description = "Exclui permanentemente uma anotação do banco de dados pelo seu UUID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Nota excluída com sucesso (sem conteúdo)")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(summary = "Alternar estado de fixação", description = "Inverte o estado de fixação do Post-it e retorna o recurso atualizado.")
    @PatchMapping("/{id}/alternar-fixacao")
    public ResponseEntity<EntityModel<NotesResponseDTO>> alternarFixacao(@PathVariable UUID id) {
        NotesResponseDTO response = service.alternarFixacao(id);
        return ResponseEntity.ok(assembler.toModel(response));
    }
}