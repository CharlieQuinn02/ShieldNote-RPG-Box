package br.ifg.urt.shieldnoterpgbox.dto.request;

import jakarta.validation.constraints.*;
public record UserRequest(
        @NotBlank(message = "Nome é obrigatório") String nome,
        @NotBlank(message = "Email é obrigatório") @Email(message = "Email inválido") String email,
        @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres") String senha
) {}