package br.ifg.urt.shieldnoterpgbox.dto.request;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AtributoRequestDTO(
	    @NotNull(message = "Força é obrigatória")
	    @Min(value = 1, message = "Força deve ser no mínimo 1")
	    Integer forca,

	    @NotNull(message = "Destreza é obrigatória")
	    @Min(value = 1, message = "Destreza deve ser no mínimo 1")
	    Integer destreza,

	    @NotNull(message = "Constituição é obrigatória")
	    @Min(value = 1, message = "Constituição deve ser no mínimo 1")
	    Integer constituicao,

	    @NotNull(message = "Inteligência é obrigatória")
	    @Min(value = 1, message = "Inteligência deve ser no mínimo 1")
	    Integer inteligencia,

	    @NotNull(message = "Sabedoria é obrigatória")
	    @Min(value = 1, message = "Sabedoria deve ser no mínimo 1")
	    Integer sabedoria,

	    @NotNull(message = "Carisma é obrigatório")
	    @Min(value = 1, message = "Carisma deve ser no mínimo 1")
	    Integer carisma
	) {}
