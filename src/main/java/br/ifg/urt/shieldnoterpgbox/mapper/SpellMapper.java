package br.ifg.urt.shieldnoterpgbox.mapper;

import br.ifg.urt.shieldnoterpgbox.dto.request.SpellRequest;
import br.ifg.urt.shieldnoterpgbox.dto.response.SpellComplexidadeResponse;
import br.ifg.urt.shieldnoterpgbox.dto.response.SpellResponse;
import br.ifg.urt.shieldnoterpgbox.dto.response.SpellResumoResponse;
import br.ifg.urt.shieldnoterpgbox.model.Spell;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpellMapper {

    SpellResponse toResponseDTO(Spell spell);

    SpellResumoResponse toResumoDTO(Spell spell);

    
    @Mapping(target = "indiceComplexidade", expression = "java(spell.getIndiceComplexidade())")
    SpellComplexidadeResponse toComplexidadeDTO(Spell spell);

    List<SpellResponse> toResponseDTOList(List<Spell> spells);

    List<SpellResumoResponse> toResumoDTOList(List<Spell> spells);

    @Mapping(target = "id", ignore = true)
    Spell toEntity(SpellRequest dto);
}
