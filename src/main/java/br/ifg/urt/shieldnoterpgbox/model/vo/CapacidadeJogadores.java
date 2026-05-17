package br.ifg.urt.shieldnoterpgbox.model.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable 
public record CapacidadeJogadores(
        
        @Column(name = "max_jogadores", nullable = false)
        Integer maximo,
        
        @Column(name = "min_jogadores", nullable = false)
        Integer minimo
) {
    public CapacidadeJogadores {
        if (minimo == null || minimo < 1) {
            throw new IllegalArgumentException("A campanha deve ter no mínimo 1 jogador.");
        }
        if (maximo == null || maximo < minimo) {
            throw new IllegalArgumentException("O máximo de jogadores não pode ser menor que o mínimo.");
        }
    }

    public String getDescricaoCapacidade() {
        if (minimo.equals(maximo)) {
            return "Exatamente " + minimo + " jogadores";
        }
        return "De " + minimo + " a " + maximo + " jogadores";
    }
}