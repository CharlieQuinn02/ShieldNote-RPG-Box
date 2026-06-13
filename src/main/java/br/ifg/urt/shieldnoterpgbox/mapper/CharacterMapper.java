package br.ifg.urt.shieldnoterpgbox.mapper;

import br.ifg.urt.shieldnoterpgbox.dto.request.AtributoRequestDTO;
import br.ifg.urt.shieldnoterpgbox.dto.request.MonsterRequest;
import br.ifg.urt.shieldnoterpgbox.dto.request.NPCRequest;
import br.ifg.urt.shieldnoterpgbox.dto.request.PlayerCharacterRequest;
import br.ifg.urt.shieldnoterpgbox.dto.response.CharacterSummaryResponse;
import br.ifg.urt.shieldnoterpgbox.dto.response.MonsterDetailResponse;
import br.ifg.urt.shieldnoterpgbox.dto.response.NPCDetailResponse;
import br.ifg.urt.shieldnoterpgbox.dto.response.PlayerCharacterDetailResponse;
import br.ifg.urt.shieldnoterpgbox.model.Character;
import br.ifg.urt.shieldnoterpgbox.model.Monster;
import br.ifg.urt.shieldnoterpgbox.model.NPC;
import br.ifg.urt.shieldnoterpgbox.model.PlayerCharacter;
import br.ifg.urt.shieldnoterpgbox.model.vo.Atributo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface CharacterMapper {

    @Mapping(target = "type", source = "character", qualifiedByName = "mapType")
    CharacterSummaryResponse toSummary(Character character);

    @Mapping(target = "skills", ignore = true)
    @Mapping(target = "savingThrows", ignore = true)
    MonsterDetailResponse toMonsterDetail(Monster monster);

    @Mapping(target = "skills", ignore = true)
    @Mapping(target = "savingThrows", ignore = true)
    NPCDetailResponse toNpcDetail(NPC npc);

    @Mapping(target = "skills", ignore = true)
    @Mapping(target = "savingThrows", ignore = true)
    PlayerCharacterDetailResponse toPlayerCharacterDetail(PlayerCharacter playerCharacter);

    Atributo toAtributo(AtributoRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "currentHitPoints", ignore = true)
    @Mapping(target = "proficiencies", ignore = true)
    Monster toMonsterEntity(MonsterRequest dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "currentHitPoints", ignore = true)
    @Mapping(target = "proficiencies", ignore = true)
    NPC toNpcEntity(NPCRequest dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "currentHitPoints", ignore = true)
    @Mapping(target = "proficiencies", ignore = true)
    PlayerCharacter toPlayerCharacterEntity(PlayerCharacterRequest dto);

    @Named("mapType")
    default String mapType(Character character) {
        if (character instanceof Monster) return "MONSTER";
        if (character instanceof NPC) return "NPC";
        if (character instanceof PlayerCharacter) return "PLAYER";
        return "UNKNOWN";
    }
}

