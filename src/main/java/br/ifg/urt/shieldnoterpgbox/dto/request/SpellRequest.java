package br.ifg.urt.shieldnoterpgbox.dto.request;

import jakarta.validation.constraints.*;
public record SpellRequest(
        @NotBlank(message = "Nome é obrigatório") String nome,
        String descricao,
        @Min(0) @Max(9) int level,
        @NotBlank(message = "Tempo de conjuração é obrigatório") String castTime,
        @NotBlank(message = "Alcance é obrigatório") String range,
        @NotBlank(message = "Duração é obrigatória") String duration,
        @NotBlank(message = "Escola é obrigatória") String school,
        String components
) {}