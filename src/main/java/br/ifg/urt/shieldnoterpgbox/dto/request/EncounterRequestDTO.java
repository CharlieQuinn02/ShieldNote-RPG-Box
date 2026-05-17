package br.ifg.urt.shieldnoterpgbox.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;

public record EncounterRequestDTO(
    @NotEmpty List<Integer> niveisJogadores,
    @NotEmpty List<Integer> xpMonstros
) {}