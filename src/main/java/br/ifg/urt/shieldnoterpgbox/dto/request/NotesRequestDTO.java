package br.ifg.urt.shieldnoterpgbox.dto.request;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.ifg.urt.shieldnoterpgbox.enums.PostItCat;
import br.ifg.urt.shieldnoterpgbox.enums.RoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NotesRequestDTO(
    @NotNull(message = "O ID da campanha é obrigatório") 
    UUID campaignId,
    
    @NotNull(message = "O ID da sessão é obrigatório") 
    UUID sessionId,
    
    @NotNull(message = "O ID do autor é obrigatório") 
    UUID authorId,
    
    @NotBlank(message = "O título é obrigatório") 
    @Size(max = 100, message = "O título deve ter no máximo 100 caracteres")
    String titulo,
    
    @NotNull(message = "A categoria é obrigatória") 
    PostItCat categoria,
    
    @NotNull(message = "A visibilidade é obrigatória") 
    RoleEnum visibilidade,
    
    boolean fixado, // boolean não precisa de @NotNull, ele assume false
    
    @Size(max = 2000, message = "O conteúdo deve ter no máximo 2000 caracteres")
    String conteudo
) {}