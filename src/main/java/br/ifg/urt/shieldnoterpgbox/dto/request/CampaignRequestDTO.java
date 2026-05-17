package br.ifg.urt.shieldnoterpgbox.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CampaignRequestDTO(
    @NotNull UUID mestreId,
    @NotBlank @Size(min = 3, max = 100) String titulo,
    String descricao,
    @NotBlank String sistema,
    @Min(1) @Max(20) int maxJogadores
) {}