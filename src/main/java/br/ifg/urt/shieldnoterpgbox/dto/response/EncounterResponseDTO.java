package br.ifg.urt.shieldnoterpgbox.dto.response;

import br.ifg.urt.shieldnoterpgbox.enums.Dificuldade;

public record EncounterResponseDTO(
    Dificuldade dificuldade,
    int xpTotal,
    int xpAjustado
) {}