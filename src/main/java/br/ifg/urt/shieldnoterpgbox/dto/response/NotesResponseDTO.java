package br.ifg.urt.shieldnoterpgbox.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import br.ifg.urt.shieldnoterpgbox.enums.PostItCat;
import br.ifg.urt.shieldnoterpgbox.enums.RoleEnum;

public record NotesResponseDTO(
    UUID id,
    UUID sessionId,
    UUID authorId,
    String titulo,
    PostItCat categoria,
    RoleEnum visibilidade,
    boolean isFixado,
    String conteudo,
    LocalDateTime criadoEm
) {}