package br.ifg.urt.shieldnoterpgbox.mapper;

import br.ifg.urt.shieldnoterpgbox.dto.request.SpellCastingRequest;
import br.ifg.urt.shieldnoterpgbox.dto.response.SpellCastingResponse;
import br.ifg.urt.shieldnoterpgbox.model.SpellCasting;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;


@Mapper(
    componentModel = "spring",
    uses = SpellSlotMapper.class,
    imports = {ArrayList.class, List.class}
)
public interface SpellCastingMapper {

    SpellCastingResponse toResponseDTO(SpellCasting sc);

    List<SpellCastingResponse> toResponseDTOList(List<SpellCasting> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "slots", ignore = true)
    @Mapping(target = "magiasConhecidas", expression = "java(dto.magiasConhecidas() != null ? new ArrayList<>(dto.magiasConhecidas()) : new ArrayList<>())")
    SpellCasting toEntity(SpellCastingRequest dto);
}

