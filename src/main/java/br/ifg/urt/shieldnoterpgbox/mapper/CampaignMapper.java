package br.ifg.urt.shieldnoterpgbox.mapper;

import br.ifg.urt.shieldnoterpgbox.dto.request.CampaignRequestDTO;
import br.ifg.urt.shieldnoterpgbox.dto.response.CampaignResponseDTO;
import br.ifg.urt.shieldnoterpgbox.model.Campaign;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CampaignMapper {

	// Request DTO para Entidade
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criadaEm", ignore = true)
    @Mapping(target = "status", ignore = true)
    // constrói o VO inteiro de uma vez para contornar a falta de setters
    @Mapping(target = "capacidade", expression = "java(new br.ifg.urt.shieldnoterpgbox.model.vo.CapacidadeJogadores(dto.maxJogadores(), dto.minJogadores()))")
    Campaign toEntity(CampaignRequestDTO dto);

    // Entidade para Response DTO
    @Mapping(source = "capacidade.minimo", target = "minJogadores") // tira do VO
    @Mapping(source = "capacidade.maximo", target = "maxJogadores") // tira do VO
    @Mapping(target = "capacidadeDescricao", expression = "java(campaign.getCapacidade() != null ? campaign.getCapacidade().getDescricaoCapacidade() : null)")
    CampaignResponseDTO toResponseDTO(Campaign campaign);

    List<CampaignResponseDTO> toResponseDTOList(List<Campaign> campaigns);
}