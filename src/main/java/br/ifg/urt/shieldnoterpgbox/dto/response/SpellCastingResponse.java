package br.ifg.urt.shieldnoterpgbox.dto.response;

import java.util.List;
import java.util.UUID;
public record SpellCastingResponse(UUID id, UUID characterId, String atributoConj,
        int cdMagias, int bonusAtaqueMag, UUID idMagias,
        List<String> magiasConhecidas, List<SpellSlotResponse> slots) {}

