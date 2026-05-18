package br.ifg.urt.shieldnoterpgbox.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.ifg.urt.shieldnoterpgbox.dto.request.EncounterRequestDTO;
import br.ifg.urt.shieldnoterpgbox.dto.response.EncounterResponseDTO;

@Service
public class EncounterCalculator {

    // Tabela oficial do D&D 5e: Níveis 1 a 5 (Facil, Medio, Dificil, Mortal)
    // Se precisar, expandimos depois
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

        // Calcula limites do grupo
        Map<String, Integer> limitesGrupo = calcularLimitesXpGrupo(niveis);

        // Calcula XP Ajustado dos monstros
        int xpAjustado = calcularXpAjustado(xpMonstros);

        // Define a dificuldade final
        String dificuldadeFinal = "FÁCIL";
        if (xpAjustado >= limitesGrupo.get("MORTAL")) {
            dificuldadeFinal = "MORTAL";
        } else if (xpAjustado >= limitesGrupo.get("DIFICIL")) {
            dificuldadeFinal = "DIFÍCIL";
        } else if (xpAjustado >= limitesGrupo.get("MEDIO")) {
            dificuldadeFinal = "MÉDIO";
        }

        // Retorna o DTO pronto para o controller
        return new EncounterResponseDTO(dificuldadeFinal, xpAjustado, limitesGrupo);
    }

    public Map<String, Integer> calcularLimitesXpGrupo(List<Integer> niveis) {
        int facil = 0, medio = 0, dificil = 0, mortal = 0;

        for (int nivel : niveis) {
            // Prevenção de erro caso o nível venha fora do padrão
            if (nivel < 1) nivel = 1;
            if (nivel > 5) nivel = 5; 

            facil += XP_THRESHOLDS[nivel][0];
            medio += XP_THRESHOLDS[nivel][1];
            dificil += XP_THRESHOLDS[nivel][2];
            mortal += XP_THRESHOLDS[nivel][3];
        }

        Map<String, Integer> limites = new HashMap<>();
        limites.put("FACIL", facil);
        limites.put("MEDIO", medio);
        limites.put("DIFICIL", dificil);
        limites.put("MORTAL", mortal);

        return limites;
    }

    public int calcularXpAjustado(List<Integer> xpMonstros) {
        int somaXp = 0;
        for (int xp : xpMonstros) {
            somaXp += xp;
        }

        int quantidadeMonstros = xpMonstros.size();
        double multiplicador = 1.0;

        // Regra do D&D: Quanto mais monstros, maior o multiplicador de dificuldade
        if (quantidadeMonstros == 2) multiplicador = 1.5;
        else if (quantidadeMonstros >= 3 && quantidadeMonstros <= 6) multiplicador = 2.0;
        else if (quantidadeMonstros >= 7 && quantidadeMonstros <= 10) multiplicador = 2.5;
        else if (quantidadeMonstros >= 11) multiplicador = 3.0;

        return (int) (somaXp * multiplicador);
    }
}