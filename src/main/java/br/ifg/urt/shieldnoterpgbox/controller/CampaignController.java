package br.ifg.urt.shieldnoterpgbox.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ifg.urt.shieldnoterpgbox.dto.request.CampaignRequestDTO;
import br.ifg.urt.shieldnoterpgbox.dto.response.CampaignResponseDTO;
import br.ifg.urt.shieldnoterpgbox.service.CampaignService;
import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("/campaigns") // Rota base para campanhas
public class CampaignController {

    private final CampaignService service;

    public CampaignController(CampaignService service) {
        this.service = service;
    }

    // GET: Buscar todas retornando DTO e 200 OK
    @GetMapping
    public ResponseEntity<List<CampaignResponseDTO>> buscarTodos() {
        return ResponseEntity.ok(service.findAll());
    }

    // GET: Buscar por ID retornando DTO e 200 OK
    @GetMapping("/{id}")
    public ResponseEntity<CampaignResponseDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    // POST: Criar nova campanha retornando DTO e 201 Created
    // O @Valid invoca as anotações do DTO (@NotBlank, @Min, etc)
    @PostMapping
    public ResponseEntity<CampaignResponseDTO> criar(@Valid @RequestBody CampaignRequestDTO dto) {
        CampaignResponseDTO novo = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    // PUT: Atualizar campanha retornando DTO e 200 OK
    @PutMapping("/{id}")
    public ResponseEntity<CampaignResponseDTO> atualizar(
            @PathVariable UUID id, 
            @Valid @RequestBody CampaignRequestDTO dto) {
        
        // Passamos o ID da URL e o DTO do corpo para o serviço
        return ResponseEntity.ok(service.update(id, dto));
    }

    // DELETE: Remover campanha retornando 204 No Content
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}