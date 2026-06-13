package br.ifg.urt.shieldnoterpgbox.service;

import java.time.LocalDateTime;
import java.util.UUID;


import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.ifg.urt.shieldnoterpgbox.dto.request.CampaignRequestDTO;
import br.ifg.urt.shieldnoterpgbox.dto.response.CampaignResponseDTO;
import br.ifg.urt.shieldnoterpgbox.enums.StatusEnum;
import br.ifg.urt.shieldnoterpgbox.mapper.CampaignMapper;
import br.ifg.urt.shieldnoterpgbox.model.Campaign;
import br.ifg.urt.shieldnoterpgbox.repository.CampaignRepository;

@Service
public class CampaignService {

    private final CampaignRepository repository;
    private final CampaignMapper mapper;

    // Construtor para injeção de dependências
    public CampaignService(CampaignRepository repository, CampaignMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    // Paginação e filtro (Buscas dinâmicas não são cacheáveis por mudarem a todo momento)
    public Page<CampaignResponseDTO> buscarTodas(String titulo, Pageable pageable) {
        Page<Campaign> pagina;

        if (titulo != null && !titulo.isBlank()) {
            pagina = repository.findByTituloContainingIgnoreCase(titulo, pageable);
        } else {
            pagina = repository.findAll(pageable);
        }

        return pagina.map(mapper::toResponseDTO);
    }

    
    // MÉTODOS COM IMPLEMENTAÇÃO DE CACHE (Caffeine)
    

    // @Cacheable: Se a campanha já estiver na memória RAM, devolve direto poupando o banco.
    @Cacheable(value = "campaigns", key = "#id")
    public CampaignResponseDTO findById(UUID id) {
        Campaign campaign = repository.findByIdOrThrow(id);
        return mapper.toResponseDTO(campaign);
    }

    // Criação da campanha (Inicia como ATIVA por padrão)
    public CampaignResponseDTO create(CampaignRequestDTO dto) {
        Campaign campaign = mapper.toEntity(dto);
        
        campaign.setCriadaEm(LocalDateTime.now()); 
        campaign.setStatus(StatusEnum.ATIVA);      
        
        campaign = repository.save(campaign);
        return mapper.toResponseDTO(campaign);
    }

    // @CachePut: Atualiza o banco e sincroniza a memória RAM com os novos dados
    @CachePut(value = "campaigns", key = "#id")
    public CampaignResponseDTO update(UUID id, CampaignRequestDTO dto) {
        Campaign campaign = repository.findByIdOrThrow(id);
        
        Campaign atualizada = mapper.toEntity(dto);
        atualizada.setId(campaign.getId()); 
        atualizada.setCriadaEm(campaign.getCriadaEm()); 
        atualizada.setStatus(campaign.getStatus()); 
        
        atualizada = repository.save(atualizada);
        return mapper.toResponseDTO(atualizada);
    }

    // @CacheEvict: Remove a campanha da memória RAM para evitar dados fantasmas após exclusão
    @CacheEvict(value = "campaigns", key = "#id")
    public void delete(UUID id) {
        Campaign campaign = repository.findByIdOrThrow(id);
        repository.delete(campaign);
    }

    // @CacheEvict: Limpa o cache para que a alteração de status seja processada corretamente
    @CacheEvict(value = "campaigns", key = "#id")
    public void pausar(UUID id) {
        Campaign campaign = repository.findByIdOrThrow(id);
        campaign.pausar();
        repository.save(campaign);
    }

    // @CacheEvict: O status voltou para ATIVA. Limpamos o cache e retornamos o DTO atualizado para o HATEOAS
    @CacheEvict(value = "campaigns", key = "#id")
    public CampaignResponseDTO reativar(UUID id) {
        Campaign campaign = repository.findByIdOrThrow(id);
        campaign.reativar(); // Aciona o método de negócio que colocamos na entidade
        Campaign atualizada = repository.save(campaign);
        return mapper.toResponseDTO(atualizada);
    }

    // @CacheEvict: Finaliza o ciclo de vida da campanha no banco
    @CacheEvict(value = "campaigns", key = "#id")
    public void encerrar(UUID id) {
        Campaign campaign = repository.findByIdOrThrow(id);
        campaign.encerrar();
        repository.save(campaign);
    }

    // @CacheEvict: Uma nova sessão altera o estado interno do agregado. Invalidamos o cache.
    @CacheEvict(value = "campaigns", key = "#id")
    public UUID iniciarSessao(UUID id) {
        Campaign campaign = repository.findByIdOrThrow(id);
        UUID sessionId = campaign.iniciarSessao();
        repository.save(campaign);
        return sessionId;
    }
}