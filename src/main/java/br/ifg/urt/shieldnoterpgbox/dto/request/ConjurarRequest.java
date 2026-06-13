package br.ifg.urt.shieldnoterpgbox.dto.request;

import jakarta.validation.constraints.*;
import java.util.UUID;
public record ConjurarRequest(
        @NotNull(message = "ID da magia é obrigatório") UUID spellId,
        @Min(1) @Max(9) int nivelSlot
) {}