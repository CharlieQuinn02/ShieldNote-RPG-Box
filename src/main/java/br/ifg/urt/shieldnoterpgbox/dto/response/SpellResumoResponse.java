package br.ifg.urt.shieldnoterpgbox.dto.response;

import java.util.UUID;
//dto para listagens
public record SpellResumoResponse(UUID id, String nome, int level) {}

