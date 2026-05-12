package br.ifg.urt.shieldnoterpgbox.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.ifg.urt.shieldnoterpgbox.enums.Dificuldade;
import java.util.logging.Logger;

@Service
public class EncounterCalculator {
	private static final Logger logger = Logger.getLogger(EncounterCalculator.class.getName());

    public Dificuldade calcularDificuldade(List<Integer> nivel, List<Integer> xpBase) {
        return Dificuldade.MEDIO; // trivial por enquanto
    }

    public Map<String, Integer> calcularLimitesXpGrupo(List<Integer> nivel) {
        return Map.of("FACIL", 100); // trivial por enquanto
    }

    public int calcularXpAjustado(List<Integer> xpBase) {
        return 0; // trivial por enquanto
    }
}