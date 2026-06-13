package br.ifg.urt.shieldnoterpgbox.service;

import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;   
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ifg.urt.shieldnoterpgbox.dto.request.NotesRequestDTO;
import br.ifg.urt.shieldnoterpgbox.dto.response.NotesResponseDTO;
import br.ifg.urt.shieldnoterpgbox.enums.PostItCat;
import br.ifg.urt.shieldnoterpgbox.exception.ResourceNotFoundException;
import br.ifg.urt.shieldnoterpgbox.mapper.NotesMapper;
import br.ifg.urt.shieldnoterpgbox.model.Campaign;
import br.ifg.urt.shieldnoterpgbox.model.Notes;
import br.ifg.urt.shieldnoterpgbox.repository.CampaignRepository;
import br.ifg.urt.shieldnoterpgbox.repository.NotesRepository;

@Service
public class NotesService {

    private static final Logger logger = Logger.getLogger(NotesService.class.getName());

    private final NotesRepository repository;
    private final CampaignRepository campaignRepository; 
    private final NotesMapper mapper;

    public NotesService(NotesRepository repository, CampaignRepository campaignRepository, NotesMapper mapper) {
        this.repository = repository;
        this.campaignRepository = campaignRepository;
        this.mapper = mapper;
    }

    
    // CONSULTA PAGINADA E FILTRADA
    
    public Page<NotesResponseDTO> buscarTodas(String titulo, PostItCat categoria, Pageable pageable) {
        logger.info("Buscando notas paginadas com filtros aplicados.");
        Page<Notes> pagina;

        if (titulo != null && !titulo.isBlank() && categoria != null) {
            pagina = repository.findByTituloContainingIgnoreCaseAndCategoria(titulo, categoria, pageable);
        } else if (titulo != null && !titulo.isBlank()) {
            pagina = repository.findByTituloContainingIgnoreCase(titulo, pageable);
        } else if (categoria != null) {
            pagina = repository.findByCategoria(categoria, pageable);
        } else {
            pagina = repository.findAll(pageable);
        }

        return pagina.map(mapper::toResponseDTO);
    }

    
    // OPERAÇÕES COM CACHE ATIVO (Caffeine Cache)
    

    @Cacheable(value = "notes", key = "#id")
    public NotesResponseDTO findById(UUID id) {
        logger.info("Buscando nota com ID: " + id);
        Notes note = repository.findByIdOrThrow(id);
        return mapper.toResponseDTO(note);
    }

    @Transactional
    public NotesResponseDTO create(NotesRequestDTO dto) {
        logger.info("Salvando nova nota: " + dto.titulo());
        
        // Busca a campanha vinculada ou lança erro 404 mapeado
        Campaign campaign = campaignRepository.findById(dto.campaignId())
                .orElseThrow(() -> new ResourceNotFoundException("Campanha não encontrada para vincular à nota. Verifique o ID."));
        
        Notes novaNota = mapper.toEntity(dto);
        novaNota.setCampaign(campaign);
        novaNota.setCriadoEm(java.time.LocalDateTime.now());
        
        Notes salva = repository.save(novaNota);
        return mapper.toResponseDTO(salva);
    }

    @CachePut(value = "notes", key = "#id")
    @Transactional
    public NotesResponseDTO update(UUID id, NotesRequestDTO dto) {
        logger.info("Atualizando nota ID: " + id);
        
        Notes existing = repository.findByIdOrThrow(id);
        
        existing.atualizarConteudo(
                dto.titulo(), 
                dto.conteudo(), 
                dto.categoria(), 
                dto.visibilidade()
        );
        
        Notes atualizada = repository.save(existing);
        return mapper.toResponseDTO(atualizada);
    }

    @CacheEvict(value = "notes", key = "#id")
    @Transactional
    public void delete(UUID id) {
        logger.info("Removendo nota ID: " + id);
        Notes existing = repository.findByIdOrThrow(id);
        repository.delete(existing);
    }
    
    @CacheEvict(value = "notes", key = "#id")
    @Transactional
    public NotesResponseDTO alternarFixacao(UUID id) {
        logger.info("Alternando estado de fixação da nota ID: " + id);
        Notes existing = repository.findByIdOrThrow(id);
        existing.alternarFixacao();
        
        Notes atualizada = repository.save(existing);
        return mapper.toResponseDTO(atualizada);
    }
}