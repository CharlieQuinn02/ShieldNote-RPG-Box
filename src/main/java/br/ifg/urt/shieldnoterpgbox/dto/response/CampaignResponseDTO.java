package br.ifg.urt.shieldnoterpgbox.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import br.ifg.urt.shieldnoterpgbox.enums.StatusEnum;

public record CampaignResponseDTO(
    UUID id,
    UUID mestreId,
    String titulo,
    String descricao,
    String sistema,
    StatusEnum status,
    int maxJogadores,
    LocalDateTime criadaEm
) {}