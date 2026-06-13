package br.ifg.urt.shieldnoterpgbox.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.ifg.urt.shieldnoterpgbox.dto.request.EncounterRequestDTO;
import br.ifg.urt.shieldnoterpgbox.dto.response.EncounterResponseDTO;
import br.ifg.urt.shieldnoterpgbox.enums.Dificuldade;

@Service
public class EncounterCalculator {

    // Tabela oficial do D&D 5e: Níveis 1 a 5 (Fácil, Médio, Difícil, Mortal)
    // As colunas seguem rigorosamente a ordem das constantes do enum Dificuldade (.ordinal())
    private static final int[][] XP_THRESHOLDS = {
        {0, 0, 0, 0},             // Posição 0 (ignorado, pois nível começa no 1)
        {25, 50, 75, 100},        // Nível 1
        {50, 100, 150, 200},      // Nível 2
        {75, 150, 225, 400},      // Nível 3
        {125, 250, 375, 500},     // Nível 4
        {250, 500, 750, 1100}     // Nível 5
    };

    public EncounterResponseDTO calcularDificuldade(EncounterRequestDTO dto) {
        List<Integer> niveis = dto.niveisJogadores();
        List<Integer> xpMonstros = dto.xpMonstros();

        // Calcula limites de corte de XP do grupo completo
        Map<String, Integer> limitesGrupo = calcularLimitesXpGrupo(niveis);

        // Calcula o peso real do XP dos monstros com base nos multiplicadores
        int xpAjustado = calcularXpAjustado(xpMonstros);

        // Define o veredito da dificuldade comparando o XP ajustado com os tetos do grupo
        String dFinal = Dificuldade.FACIL.name();
        
        if (xpAjustado >= limitesGrupo.get(Dificuldade.MORTAL.name())) {
            dFinal = Dificuldade.MORTAL.name();
        } else if (xpAjustado >= limitesGrupo.get(Dificuldade.DIFICIL.name())) {
            dFinal = Dificuldade.DIFICIL.name();
        } else if (xpAjustado >= limitesGrupo.get(Dificuldade.MEDIO.name())) {
            dFinal = Dificuldade.MEDIO.name();
        }

        // Retorna o DTO de resposta para o controller
        return new EncounterResponseDTO(dFinal, xpAjustado, limitesGrupo);
    }

    public Map<String, Integer> calcularLimitesXpGrupo(List<Integer> niveis) {
        int facil = 0, medio = 0, dificil = 0, mortal = 0;

        for (int nivel : niveis) {
            // Defesa de segurança para manter os índices dentro do array
            if (nivel < 1) nivel = 1;
            if (nivel > 5) nivel = 5; 

            // Vincula as colunas do array usando o método .ordinal() do Enum para evitar números soltos
            facil += XP_THRESHOLDS[nivel][Dificuldade.FACIL.ordinal()];
            medio += XP_THRESHOLDS[nivel][Dificuldade.MEDIO.ordinal()];
            dificil += XP_THRESHOLDS[nivel][Dificuldade.DIFICIL.ordinal()];
            mortal += XP_THRESHOLDS[nivel][Dificuldade.MORTAL.ordinal()];
        }

        // o map vai se basear no enum
        Map<String, Integer> limites = new HashMap<>();
        limites.put(Dificuldade.FACIL.name(), facil);
        limites.put(Dificuldade.MEDIO.name(), medio);
        limites.put(Dificuldade.DIFICIL.name(), dificil);
        limites.put(Dificuldade.MORTAL.name(), mortal);

        return limites;
    }

    public int calcularXpAjustado(List<Integer> xpMonstros) {
        int somaXp = 0;
        for (int xp : xpMonstros) {
            somaXp += xp;
        }

        int quantidadeMonstros = xpMonstros.size();
        double multiplicador = 1.0;

        // multiplicadores de acordo com D&D 5e
        if (quantidadeMonstros == 2) {
            multiplicador = 1.5;
        } else if (quantidadeMonstros >= 3 && quantidadeMonstros <= 6) {
            multiplicador = 2.0;
        } else if (quantidadeMonstros >= 7 && quantidadeMonstros <= 10) {
            multiplicador = 2.5;
        } else if (quantidadeMonstros >= 11) {
            multiplicador = 3.0;
        }

        return (int) (somaXp * multiplicador);
    }
}