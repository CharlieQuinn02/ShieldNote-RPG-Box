package br.ifg.urt.shieldnoterpgbox.controller;

import java.util.List;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.RestController;

import br.ifg.urt.shieldnoterpgbox.dto.request.CampaignRequestDTO;
import br.ifg.urt.shieldnoterpgbox.dto.response.CampaignResponseDTO;
import br.ifg.urt.shieldnoterpgbox.service.CampaignService;
import io.swagger.v3.oas.annotations.Operation;
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

    public CampaignController(CampaignService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todas as campanhas", description = "Retorna uma lista contendo todas as campanhas cadastradas no banco de dados.")
    @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso")
    @GetMapping
    public ResponseEntity<List<CampaignResponseDTO>> buscarTodos() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Buscar campanha por ID", description = "Retorna os detalhes de uma campanha específica através do seu identificador único (UUID).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Campanha encontrada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Campanha não encontrada no banco de dados"),
        @ApiResponse(responseCode = "400", description = "Formato de UUID inválido na URL")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CampaignResponseDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Criar nova campanha", description = "Cadastra uma nova campanha validando os limites de capacidade mínima e máxima de jogadores.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Campanha criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados (ex: mínimo maior que máximo)")
    })
    @PostMapping
    public ResponseEntity<CampaignResponseDTO> criar(@Valid @RequestBody CampaignRequestDTO dto) {
        CampaignResponseDTO novo = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    @Operation(summary = "Atualizar campanha existente", description = "Atualiza os dados de uma campanha através do seu UUID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Campanha atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Campanha não encontrada no banco de dados"),
        @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CampaignResponseDTO> atualizar(
            @PathVariable UUID id, 
            @Valid @RequestBody CampaignRequestDTO dto) {
        
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Remover campanha", description = "Exclui permanentemente uma campanha do banco de dados pelo seu UUID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Campanha excluída com sucesso (sem conteúdo de retorno)"),
        @ApiResponse(responseCode = "404", description = "Campanha não encontrada no banco de dados")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/pausar")
    public ResponseEntity<Void> pausar(@PathVariable UUID id) {
        service.pausar(id); 
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/encerrar")
    public ResponseEntity<Void> encerrar(@PathVariable UUID id) {
        service.encerrar(id); 
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{id}/iniciar-sessao")
    public ResponseEntity<UUID> iniciarSessao(@PathVariable UUID id) {
        UUID sessionId = service.iniciarSessao(id);
        return ResponseEntity.ok(sessionId);
    }
}