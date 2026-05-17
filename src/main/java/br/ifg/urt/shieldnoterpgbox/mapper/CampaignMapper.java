package br.ifg.urt.shieldnoterpgbox.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.ifg.urt.shieldnoterpgbox.dto.request.CampaignRequestDTO;
import br.ifg.urt.shieldnoterpgbox.dto.response.CampaignResponseDTO;
import br.ifg.urt.shieldnoterpgbox.model.Campaign;

@Mapper(componentModel = "spring")
public interface CampaignMapper {

    CampaignResponseDTO toResponseDTO(Campaign campaign);

    @Mapping(target = "id", ignore = true) // Impede ID no POST
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "criadaEm", ignore = true)
    Campaign toEntity(CampaignRequestDTO dto);
    
    List<CampaignResponseDTO> toResponseDTOList(List<Campaign> campaigns);
}