package br.ifg.urt.shieldnoterpgbox.model.vo;

import br.ifg.urt.shieldnoterpgbox.model.SpellCasting;

import java.util.List;
import java.util.UUID;

public record SpellCastingVO(
        UUID id,
        UUID characterId,
        String atributoConj,
        int cdMagias,
        int bonusAtaqueMag,
        List<SpellSlotVO> slots
) {
    public static SpellCastingVO from(SpellCasting sc) {
        List<SpellSlotVO> slotsVO = sc.getSlots().stream()
                .map(SpellSlotVO::from)
                .toList();
        return new SpellCastingVO(
                sc.getId(), sc.getCharacterId(), sc.getAtributoConj(),
                sc.getCdMagias(), sc.getBonusAtaqueMag(), slotsVO
        );
    }
}
