package br.ifg.urt.shieldnoterpgbox.service;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import br.ifg.urt.shieldnoterpgbox.dto.request.EncounterRequestDTO;
import br.ifg.urt.shieldnoterpgbox.dto.response.EncounterResponseDTO;
import br.ifg.urt.shieldnoterpgbox.enums.Dificuldade;

@Service
public class EncounterCalculator {
	private static final Logger logger = Logger.getLogger(EncounterCalculator.class.getName());

	// NOVO MÉTODO PRINCIPAL PARA A API 
    public EncounterResponseDTO calcularDificuldade(EncounterRequestDTO dto) {
        logger.info("Iniciando cálculo de encontro via API...");
        
        //  extraímos os dados do RequestDTO (em records, não usamos 'get')
        List<Integer> niveis = dto.niveisJogadores();
        List<Integer> xpMonstros = dto.xpMonstros();

        
        Dificuldade dificuldade = calcularDificuldadeInterno(niveis, xpMonstros);
        int xpAjustado = calcularXpAjustado(xpMonstros);
        
        //  somar o XP Total real para já ficar certinho
        int xpTotal = xpMonstros.stream().mapToInt(Integer::intValue).sum();

        //  Montamos o ResponseDTO que será devolvido ao Controller e depois à tela
        return new EncounterResponseDTO(dificuldade, xpTotal, xpAjustado);
    }

    // metodos originais

    private Dificuldade calcularDificuldadeInterno(List<Integer> nivel, List<Integer> xpBase) {
        return Dificuldade.MEDIO; // trivial por enquanto
    }

    public Map<String, Integer> calcularLimitesXpGrupo(List<Integer> nivel) {
        return Map.of("FACIL", 100); // trivial por enquanto
    }

    public int calcularXpAjustado(List<Integer> xpBase) {
        return 0; // trivial por enquanto
    }
}