package br.ifg.urt.shieldnoterpgbox.dto.request;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record EncounterRequestDTO(
    @NotEmpty(message = "A lista de níveis dos jogadores não pode estar vazia.")
    List<@NotNull(message = "O nível do jogador não pode ser nulo.") 
         @Min(value = 1, message = "O nível de um personagem deve ser no mínimo 1.") 
         Integer> niveisJogadores,

    @NotEmpty(message = "A lista de XP dos monstros não pode estar vazia.")
    List<@NotNull(message = "O valor de XP do monstro não pode ser nulo.") 
         @Min(value = 0, message = "O XP de um monstro não pode ser negativo.") 
         Integer> xpMonstros
) {}