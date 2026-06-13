package br.ifg.urt.shieldnoterpgbox.dto.response;

import java.util.UUID;
public record SpellSlotResponse(UUID id, int nivel, int quantidadeAtual, int quantidadeMaxima, boolean disponivel) {}

