package br.ifg.urt.shieldnoterpgbox.dto.request;

import java.util.UUID;

import br.ifg.urt.shieldnoterpgbox.enums.PostItCat;
import br.ifg.urt.shieldnoterpgbox.enums.RoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NotesRequestDTO(
    @NotNull UUID sessionId,
    @NotNull UUID authorId,
    @NotBlank String titulo,
    @NotNull PostItCat categoria,
    @NotNull RoleEnum visibilidade,
    @NotNull boolean isFixado,
    String conteudo
) {}