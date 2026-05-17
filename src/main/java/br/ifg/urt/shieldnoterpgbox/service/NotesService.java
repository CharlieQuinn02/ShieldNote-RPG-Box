package br.ifg.urt.shieldnoterpgbox.service;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ifg.urt.shieldnoterpgbox.dto.request.NotesRequestDTO;
import br.ifg.urt.shieldnoterpgbox.dto.response.NotesResponseDTO;
import br.ifg.urt.shieldnoterpgbox.mapper.NotesMapper;
import br.ifg.urt.shieldnoterpgbox.model.Notes;
import br.ifg.urt.shieldnoterpgbox.repository.NotesRepository;

@Service
public class NotesService {

    private static final Logger logger = Logger.getLogger(NotesService.class.getName());

    private final NotesRepository repository;
    private final NotesMapper mapper; // Injetando o Mapper

    public NotesService(NotesRepository repository, NotesMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<NotesResponseDTO> findAll() {
        logger.info("Buscando todas as notas no banco.");
        List<Notes> notes = repository.findAll();
        return mapper.toResponseDTOList(notes); // Converte a lista toda via Mapper
    }

    public NotesResponseDTO findById(UUID id) {
        logger.info("Buscando nota com ID: " + id);
        // Supondo que você use o .findById().orElseThrow() padrão do JPA
        Notes note = repository.findById(id).orElseThrow(() -> new RuntimeException("Nota não encontrada"));
        return mapper.toResponseDTO(note);
    }

    @Transactional
    public NotesResponseDTO create(NotesRequestDTO dto) {
        logger.info("Salvando nova nota: " + dto.titulo());
        
        //  Converte o DTO para a Entidade através do Mapper
        Notes novaNota = mapper.toEntity(dto);
        
        //  Define a data/hora atual antes de enviar para o banco de dados
        novaNota.setCriadoEm(java.time.LocalDateTime.now());
        
        // Salva a entidade com a data preenchida
        Notes salva = repository.save(novaNota);
        
        //  Retorna o ResponseDTO mapeado
        return mapper.toResponseDTO(salva);
    }

    @Transactional
    public NotesResponseDTO update(UUID id, NotesRequestDTO dto) {
        logger.info("Atualizando nota ID: " + id);
        
        Notes existing = repository.findById(id).orElseThrow(() -> new RuntimeException("Nota não encontrada"));
        
        // Atualiza usando os métodos do Record (sem o "get")
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