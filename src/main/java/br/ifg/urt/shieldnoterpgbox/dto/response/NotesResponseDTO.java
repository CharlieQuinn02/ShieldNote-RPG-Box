package br.ifg.urt.shieldnoterpgbox.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.ifg.urt.shieldnoterpgbox.enums.PostItCat;
import br.ifg.urt.shieldnoterpgbox.enums.RoleEnum;

public record NotesResponseDTO(
    UUID id,
    UUID sessionId,
    UUID authorId,
    String titulo,
    PostItCat categoria,
    RoleEnum visibilidade,
    boolean fixado,
    String conteudo,
    LocalDateTime criadoEm
) {}