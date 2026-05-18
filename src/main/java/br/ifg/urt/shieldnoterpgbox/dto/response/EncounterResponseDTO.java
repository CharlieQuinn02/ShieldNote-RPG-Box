package br.ifg.urt.shieldnoterpgbox.dto.response;

import java.util.Map;

public record EncounterResponseDTO(
    String dificuldadeFinal,
    int xpAjustado,
    Map<String, Integer> limitesGrupo
) {
}