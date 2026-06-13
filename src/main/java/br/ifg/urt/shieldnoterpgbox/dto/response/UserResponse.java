package br.ifg.urt.shieldnoterpgbox.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;
public record UserResponse(UUID id, String nome, String email, LocalDateTime criadoEm) {}
