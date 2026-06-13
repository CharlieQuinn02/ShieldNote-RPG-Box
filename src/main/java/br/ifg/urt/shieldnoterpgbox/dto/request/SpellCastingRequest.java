package br.ifg.urt.shieldnoterpgbox.dto.request;

import jakarta.validation.constraints.*;

import java.util.List;
import java.util.UUID;

public record SpellCastingRequest(

        @NotNull(message = "ID do personagem é obrigatório")
        UUID characterId,

        @NotBlank(message = "Atributo de conjuração é obrigatório")
        String atributoConj,

        // 🟠 FIX 6: @Max(30) adicionado — CD máxima possível no D&D 5e
        @Min(value = 8, message = "CD mínima é 8 (8 + bônus de proficiência + mod. do atributo de conjuração)")
        @Max(value = 30, message = "CD máxima é 30")
        int cdMagias,

        @Min(value = 0, message = "Bônus de ataque mágico não pode ser negativo")
        int bonusAtaqueMag,

        @NotNull(message = "ID da magia principal é obrigatório")
        UUID idMagias,

        List<String> magiasConhecidas
) {}

