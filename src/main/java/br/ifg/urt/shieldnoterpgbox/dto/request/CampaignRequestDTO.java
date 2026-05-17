package br.ifg.urt.shieldnoterpgbox.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CampaignRequestDTO(
        @NotNull(message = "O ID do mestre é obrigatório")
        UUID mestreId,

        @NotBlank(message = "O título é obrigatório")
        String titulo,

        String descricao,

        @NotBlank(message = "O sistema é obrigatório")
        String sistema,

        // agora recebemos os dois valores separados para formar o VO
        @NotNull(message = "O mínimo de jogadores é obrigatório")
        @Min(value = 1, message = "O mínimo de jogadores deve ser pelo menos 1")
        Integer minJogadores,

        @NotNull(message = "O máximo de jogadores é obrigatório")
        @Min(value = 1, message = "O máximo de jogadores deve ser pelo menos 1")
        Integer maxJogadores
) {}