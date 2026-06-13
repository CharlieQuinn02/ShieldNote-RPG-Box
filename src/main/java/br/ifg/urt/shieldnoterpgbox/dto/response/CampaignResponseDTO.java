package br.ifg.urt.shieldnoterpgbox.dto.response;

import br.ifg.urt.shieldnoterpgbox.enums.StatusEnum;
import java.time.LocalDateTime;
import java.util.UUID;

public record CampaignResponseDTO(
        UUID id,
        UUID mestreId,
        String titulo,
        String descricao,
        String sistema,
        StatusEnum status,
        Integer minJogadores,
        Integer maxJogadores,
        String capacidadeDescricao, // VO
        LocalDateTime criadaEm
) {}