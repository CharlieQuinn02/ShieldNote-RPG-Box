package br.ifg.urt.shieldnoterpgbox.controller;
import org.springdoc.core.annotations.ParameterObject;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import br.ifg.urt.shieldnoterpgbox.assembler.CampaignModelAssembler;
import br.ifg.urt.shieldnoterpgbox.dto.request.CampaignRequestDTO;
import br.ifg.urt.shieldnoterpgbox.dto.response.CampaignResponseDTO;
import br.ifg.urt.shieldnoterpgbox.service.CampaignService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("/campaigns")
@Tag(name = "Campanhas", description = "Endpoints para criação e gerenciamento de campanhas de RPG")
public class CampaignController {

    private final CampaignService service;
    private final CampaignModelAssembler assembler; // Injeção do componente Assembler

    public CampaignController(CampaignService service, CampaignModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @Operation(
        summary = "Listar campanhas com paginação, filtro e HATEOAS",
        description = "Retorna uma página de campanhas contendo links hipermídia dinâmicos para transição de estados."
    )
    @ApiResponse(responseCode = "200", description = "Página recuperada com sucesso")
    @GetMapping
    public ResponseEntity<Page<EntityModel<CampaignResponseDTO>>> listarCampanhas(
            @Parameter(description = "Filtro por título da campanha (busca parcial)")
            @RequestParam(required = false) String titulo,
            @ParameterObject @PageableDefault(size = 10, sort = "criadaEm") Pageable pageable) {
        
        Page<CampaignResponseDTO> dtos = service.buscarTodas(titulo, pageable);
        // Transforma a página comum em uma coleção inteligente com links individuais 
        Page<EntityModel<CampaignResponseDTO>> paginaComLinks = dtos.map(assembler::toModel);
        return ResponseEntity.ok(paginaComLinks);
    }

    @Operation(summary = "Buscar campanha por ID com HATEOAS", description = "Retorna os detalhes de uma campanha específica acompanhada de seus links de ciclo de vida ativos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Campanha encontrada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Campanha não encontrada no banco de dados"),
        @ApiResponse(responseCode = "400", description = "Formato de UUID inválido na URL")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CampaignResponseDTO>> buscarPorId(@PathVariable UUID id) {
        CampaignResponseDTO dto = service.findById(id);
        return ResponseEntity.ok(assembler.toModel(dto)); // Envelopa com os links do estado atual 
    }

    @Operation(summary = "Criar nova campanha", description = "Cadastra uma nova campanha e retorna a sua representação hypermedia inicial (Status: ATIVA).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Campanha criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados")
    })
    @PostMapping
    public ResponseEntity<EntityModel<CampaignResponseDTO>> criar(@Valid @RequestBody CampaignRequestDTO dto) {
        CampaignResponseDTO novo = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(novo));
    }

    @Operation(summary = "Atualizar campanha existente", description = "Atualiza os dados estruturais de uma campanha através do seu UUID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Campanha atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Campanha não encontrada no banco de dados"),
        @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<CampaignResponseDTO>> atualizar(
            @PathVariable UUID id, 
            @Valid @RequestBody CampaignRequestDTO dto) {
        
        CampaignResponseDTO atualizada = service.update(id, dto);
        return ResponseEntity.ok(assembler.toModel(atualizada));
    }

    @Operation(summary = "Remover campanha", description = "Exclui permanentemente uma campanha do banco de dados pelo seu UUID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Campanha excluída com sucesso"),
        @ApiResponse(responseCode = "404", description = "Campanha não encontrada no banco de dados")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(summary = "Pausar campanha ativa", description = "Muda o status da campanha para PAUSADA e atualiza as ações disponíveis.")
    @PatchMapping("/{id}/pausar")
    public ResponseEntity<EntityModel<CampaignResponseDTO>> pausar(@PathVariable UUID id) {
        service.pausar(id); 
        // Busca a entidade atualizada para que o assembler monte os novos links com o status PAUSADA
        CampaignResponseDTO atualizada = service.findById(id);
        return ResponseEntity.ok(assembler.toModel(atualizada));
    }

    @Operation(summary = "Reativar campanha pausada", description = "Muda o status da campanha de PAUSADA de volta para ATIVA, liberando novas sessões.")
    @PatchMapping("/{id}/reativar")
    public ResponseEntity<EntityModel<CampaignResponseDTO>> reativar(@PathVariable UUID id) {
        // Como o método reativar do service já retorna o DTO atualizado, basta passar direto ao assembler
        CampaignResponseDTO atualizada = service.reativar(id);
        return ResponseEntity.ok(assembler.toModel(atualizada));
    }

    @Operation(summary = "Encerrar campanha", description = "Finaliza permanentemente a mesa de RPG. Uma campanha encerrada perde seus links de transição de estado.")
    @PatchMapping("/{id}/encerrar")
    public ResponseEntity<EntityModel<CampaignResponseDTO>> encerrar(@PathVariable UUID id) {
        service.encerrar(id); 
        // Busca a entidade atualizada para fornecer o estado final "FINALIZADA" sem links de escrita 
        CampaignResponseDTO atualizada = service.findById(id);
        return ResponseEntity.ok(assembler.toModel(atualizada));
    }
    
    @Operation(summary = "Iniciar nova sessão", description = "Gera um UUID único para o controle de uma nova sessão da mesa de RPG ativa.")
    @PostMapping("/{id}/iniciar-sessao")
    public ResponseEntity<UUID> iniciarSessao(@PathVariable UUID id) {
        UUID sessionId = service.iniciarSessao(id);
        return ResponseEntity.ok(sessionId);
    }
}