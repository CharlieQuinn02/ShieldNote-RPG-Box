package br.ifg.urt.shieldnoterpgbox.service;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ifg.urt.shieldnoterpgbox.dto.request.NotesRequestDTO;
import br.ifg.urt.shieldnoterpgbox.dto.response.NotesResponseDTO;
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

    public List<NotesResponseDTO> findAll() {
        logger.info("Buscando todas as notas no banco.");
        List<Notes> notes = repository.findAll();
        return mapper.toResponseDTOList(notes); 
    }

    public NotesResponseDTO findById(UUID id) {
        logger.info("Buscando nota com ID: " + id);
        Notes note = repository.findById(id).orElseThrow(() -> new RuntimeException("Nota não encontrada"));
        return mapper.toResponseDTO(note);
    }

    @Transactional
    public NotesResponseDTO create(NotesRequestDTO dto) {
        logger.info("Salvando nova nota: " + dto.titulo());
        
        //  busca a campanha no banco usando o ID que veio do DTO
        Campaign campaign = campaignRepository.findById(dto.campaignId())
                .orElseThrow(() -> new RuntimeException("Campanha não encontrada para vincular à nota. Verifique o ID."));
        
        //  Converte o DTO para a Entidade
        Notes novaNota = mapper.toEntity(dto);
        
        //  relacionamento jpa
        novaNota.setCampaign(campaign);
        
        //  Define a data/hora atual
        novaNota.setCriadoEm(java.time.LocalDateTime.now());
        
        //  Salva a entidade 
        Notes salva = repository.save(novaNota);
        
        return mapper.toResponseDTO(salva);
    }

    @Transactional
    public NotesResponseDTO update(UUID id, NotesRequestDTO dto) {
        logger.info("Atualizando nota ID: " + id);
        
        Notes existing = repository.findById(id).orElseThrow(() -> new RuntimeException("Nota não encontrada"));
        
        
        // como geralmente uma nota não muda de campanha, atualizamos apenas o texto
        existing.setTitulo(dto.titulo());
        existing.setCategoria(dto.categoria());
        existing.setVisibilidade(dto.visibilidade());
        existing.setFixado(dto.isFixado());
        existing.setConteudo(dto.conteudo());
        
        Notes atualizada = repository.save(existing);
        return mapper.toResponseDTO(atualizada);
    }

    @Transactional
    public void delete(UUID id) {
        logger.info("Removendo nota ID: " + id);
        Notes existing = repository.findById(id).orElseThrow(() -> new RuntimeException("Nota não encontrada"));
        repository.delete(existing);
    }
}