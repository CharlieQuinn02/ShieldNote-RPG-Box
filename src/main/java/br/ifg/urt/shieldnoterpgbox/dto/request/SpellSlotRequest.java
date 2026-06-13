package br.ifg.urt.shieldnoterpgbox.dto.request;

import jakarta.validation.constraints.*;
import java.util.UUID;
public record SpellSlotRequest(
        @NotNull(message = "ID do SpellCasting é obrigatório") UUID spellCastingId,
        @Min(1) @Max(9) int nivel,
        @Min(0) int quantidadeAtual,
        @Min(1) int quantidadeMaxima
) {}

