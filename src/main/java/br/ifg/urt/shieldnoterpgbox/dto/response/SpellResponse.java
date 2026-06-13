package br.ifg.urt.shieldnoterpgbox.dto.response;

import java.util.UUID;
public record SpellResponse(UUID id, String nome, String descricao, int level,
        String castTime, String range, String duration, String school, String components) {}
