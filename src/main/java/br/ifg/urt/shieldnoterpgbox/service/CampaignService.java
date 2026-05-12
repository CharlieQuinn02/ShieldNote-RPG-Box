package br.ifg.urt.shieldnoterpgbox.service;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ifg.urt.shieldnoterpgbox.model.Campaign;
import br.ifg.urt.shieldnoterpgbox.repository.CampaignRepository;

@Service
public class CampaignService {

    private static final Logger logger = Logger.getLogger(CampaignService.class.getName());

    // Injeção de dependência moderna via construtor com 'final'
    private final CampaignRepository repository;

    public CampaignService(CampaignRepository repository) {
        this.repository = repository;
    }

    // Leitura não precisa de @Transactional
    public List<Campaign> findAll() {
        logger.info("Buscando todas as campanhas no banco.");
        return repository.findAll();
    }

    public Campaign findById(UUID id) {
        logger.info("Buscando campanha no banco com ID: " + id);
        return repository.findByIdOrThrow(id); // Usa o método inteligente do Repository
    }

    @Transactional
    public Campaign create(Campaign campaign) {
        logger.info("Salvando nova campanha no banco: " + campaign.getTitulo());
        return repository.save(campaign); // O JPA gera o ID e o SQL Insert automaticamente
    }

    @Transactional
    public Campaign update(Campaign campaign) {
        logger.info("Atualizando campanha ID: " + campaign.getId());
        
        // Verifica se existe antes de atualizar
        Campaign existing = repository.findByIdOrThrow(campaign.getId());
        
        // Atualiza os dados
        existing.setTitulo(campaign.getTitulo());
        existing.setDescricao(campaign.getDescricao());
        existing.setSistema(campaign.getSistema());
        existing.setMaxJogadores(campaign.getMaxJogadores());
        // (Adicione aqui os outros setters se necessário)
        
        return repository.save(existing);
    }

    @Transactional
    public void delete(UUID id) {
        logger.info("Removendo campanha ID: " + id);
        Campaign existing = repository.findByIdOrThrow(id);
        repository.delete(existing);
    }
}