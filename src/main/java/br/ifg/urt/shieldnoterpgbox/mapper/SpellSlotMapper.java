package br.ifg.urt.shieldnoterpgbox.mapper;

import br.ifg.urt.shieldnoterpgbox.dto.response.SpellSlotResponse;
import br.ifg.urt.shieldnoterpgbox.model.SpellSlot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;
@Mapper(componentModel = "spring")
public interface SpellSlotMapper {
    @Mapping(target = "disponivel", expression = "java(slot.isDisponivel())")
    SpellSlotResponse toResponseDTO(SpellSlot slot);
    List<SpellSlotResponse> toResponseDTOList(List<SpellSlot> slots);
}
