package br.ifg.urt.shieldnoterpgbox.dto.response;
import java.util.UUID;

public record CharacterSummaryResponse(
	    UUID id,
	    String name,
	    int hitPoints,
	    int armorClass,
	    String type
	) {}