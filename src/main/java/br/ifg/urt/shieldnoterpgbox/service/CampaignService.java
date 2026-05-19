package br.ifg.urt.shieldnoterpgbox.service;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ifg.urt.shieldnoterpgbox.dto.request.CampaignRequestDTO;
import br.ifg.urt.shieldnoterpgbox.dto.response.CampaignResponseDTO;
import br.ifg.urt.shieldnoterpgbox.exception.ResourceNotFoundException;
import br.ifg.urt.shieldnoterpgbox.mapper.CampaignMapper;
import br.ifg.urt.shieldnoterpgbox.model.Campaign;
import br.ifg.urt.shieldnoterpgbox.repository.CampaignRepository;

@Service
public class CampaignService {

    private static final Logger logger = Logger.getLogger(CampaignService.class.getName());

    private final CampaignRepository repository;
    private final CampaignMapper mapper; // injetando o Mapper

    public CampaignService(CampaignRepository repository, CampaignMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    
    // Retorna uma lista de DTOs mapeada automaticamente
    public List<CampaignResponseDTO> findAll() {
        logger.info("Buscando todas as campanhas no banco.");
        List<Campaign> campaigns = repository.findAll();
        return mapper.toResponseDTOList(campaigns); // Uso do Mapper
    }

    // Busca no banco e já converte para ResponseDTO
    public CampaignResponseDTO findById(UUID id) {
        logger.info("Buscando campanha no banco com ID: " + id);
        Campaign campaign = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Campanha não encontrada com o ID: " + id));
        return mapper.toResponseDTO(campaign); // Uso do Mapper
    }

    @Transactional
    public CampaignResponseDTO create(CampaignRequestDTO dto) {
        logger.info("Salvando nova campanha no banco: " + dto.titulo());
        
        //  Converte RequestDTO -> Entity via Mapper
        Campaign novaCampaign = mapper.toEntity(dto); 
        
        //  preenche os campos obrigatórios do banco que o DTO não traz
        novaCampaign.setCriadaEm(java.time.LocalDateTime.now()); // Define a data e hora atual
        novaCampaign.setStatus(br.ifg.urt.shieldnoterpgbox.enums.StatusEnum.ATIVA); // inicia como ativa por padrão
        
        //  salva a entidade totalmente preenchida
        Campaign salva = repository.save(novaCampaign);
        
        //  devolve o ResponseDTO com os metadados inclusos
        return mapper.toResponseDTO(salva); 
    }

    @Transactional
    public CampaignResponseDTO update(UUID id, CampaignRequestDTO dto) {
        logger.info("Atualizando campanha ID: " + id);
        
        // verifica se existe antes de atualizar
        Campaign existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Campanha não encontrada com o ID: " + id));
        
        // atualiza os dados pegando do Record (lembrar que: records usam .campo() em vez de getCampo())
        existing.setTitulo(dto.titulo());
        existing.setDescricao(dto.descricao());
        existing.setSistema(dto.sistema());
        existing.setCapacidade(new br.ifg.urt.shieldnoterpgbox.model.vo.CapacidadeJogadores(dto.maxJogadores(), dto.minJogadores()));
        
        Campaign atualizado = repository.save(existing);
        
        // Retorna a entidade atualizada como DTO
        return mapper.toResponseDTO(atualizado);
    }

    @Transactional
    public void delete(UUID id) {
        logger.info("Removendo campanha ID: " + id);
        Campaign existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Campanha não encontrada com o ID: " + id));
        repository.delete(existing);
    }
    
    @Transactional
    public void pausar(UUID id) {
        Campaign campaign = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Campanha não encontrada com o ID: " + id));
        campaign.pausar();
        repository.save(campaign);
    }

    @Transactional
    public void encerrar(UUID id) {
        Campaign campaign = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Campanha não encontrada com o ID: " + id));
        campaign.encerrar();
        repository.save(campaign);
    }
    
    @Transactional
    public UUID iniciarSessao(UUID id) {
        logger.info("Iniciando uma nova sessão de jogo para a campanha ID: " + id);
        
        Campaign campaign = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Campanha não encontrada com o ID: " + id));
                
        // regra de negócio: impede iniciar sessão se a campanha não estiver ativa
        if (campaign.getStatus() != br.ifg.urt.shieldnoterpgbox.enums.StatusEnum.ATIVA) {
            throw new IllegalArgumentException("Não é possível iniciar uma sessão em uma campanha com status: " + campaign.getStatus());
        }
        
        // Invoca o comportamento da entidade e retorna o UUID gerado
        return campaign.iniciarSessao();
    }
}