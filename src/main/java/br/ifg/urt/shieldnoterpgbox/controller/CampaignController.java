package br.ifg.urt.shieldnoterpgbox.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ifg.urt.shieldnoterpgbox.model.Campaign;
import br.ifg.urt.shieldnoterpgbox.service.CampaignService;

@RestController
@RequestMapping("/campaigns") // Rota base para campanhas
public class CampaignController {

    // Injeção do serviço moderna (via construtor com 'final')
    private final CampaignService service;

    public CampaignController(CampaignService service) {
        this.service = service;
    }

    // GET: Buscar todas (Slide 17)
    @GetMapping
    public List<Campaign> buscarTodos() {
        return service.findAll();
    }

    // GET: Buscar por ID (Slide 14)
    @GetMapping("/{id}")
    public Campaign buscarPorId(@PathVariable UUID id) {
        return service.findById(id);
    }

    // POST: Criar nova campanha (Slide 51)
    @PostMapping
    public Campaign criar(@RequestBody Campaign campaign) {
        return service.create(campaign);
    }

    // PUT: Atualizar campanha existente (Slide 57)
    @PutMapping("/{id}")
    public Campaign atualizar(@PathVariable UUID id, @RequestBody Campaign campaign) {
        campaign.setId(id);
        return service.update(campaign);
    }

    // DELETE: Remover campanha (Slide 60)
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable UUID id) {
        service.delete(id);
    }
}