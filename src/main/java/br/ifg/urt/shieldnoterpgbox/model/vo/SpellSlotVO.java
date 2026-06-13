package br.ifg.urt.shieldnoterpgbox.model.vo;

import br.ifg.urt.shieldnoterpgbox.model.SpellSlot;

public record SpellSlotVO(int nivel, int quantidadeAtual, int quantidadeMaxima, boolean disponivel) {

    
    public static SpellSlotVO from(SpellSlot slot) {
        return new SpellSlotVO(
                slot.getNivel(),
                slot.getQuantidadeAtual(),
                slot.getQuantidadeMaxima(),
                slot.isDisponivel()
        );
    }
}
