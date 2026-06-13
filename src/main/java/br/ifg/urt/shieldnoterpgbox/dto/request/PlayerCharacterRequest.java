package br.ifg.urt.shieldnoterpgbox.dto.request;

import br.ifg.urt.shieldnoterpgbox.model.vo.Alignment;
import br.ifg.urt.shieldnoterpgbox.model.vo.Ability;
import br.ifg.urt.shieldnoterpgbox.model.vo.Skill;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.Set;


public record PlayerCharacterRequest(

	    @NotBlank(message = "O nome é obrigatório")
	    @Size(min = 1, max = 100, message = "O nome deve ter entre 1 e 100 caracteres")
	    String name,

	    @NotNull(message = "Os pontos de vida são obrigatórios")
	    @Min(value = 1, message = "Os pontos de vida devem ser no mínimo 1")
	    Integer hitPoints,

	    @Min(value = 0, message = "Os pontos de vida atuais não podem ser negativos")
	    Integer currentHitPoints,

	    @NotNull(message = "A classe de armadura é obrigatória")
	    @Min(value = 0, message = "A classe de armadura deve ser no mínimo 0")
	    Integer armorClass,

	    @NotBlank(message = "O deslocamento é obrigatório")
	    String speed,

	    @NotNull(message = "O alinhamento é obrigatório")
	    Alignment alignment,

	    @Min(value = 2, message = "O bônus de proficiência deve ser no mínimo 2")
	    @Max(value = 9, message = "O bônus de proficiência deve ser no máximo 9")
	    Integer proficiencyBonus,

	    @Valid
	    @NotNull(message = "Os atributos são obrigatórios")
	    AtributoRequestDTO attributes,

	    Set<Skill> skills,

	    Set<Ability> savingThrows,

	    @NotBlank(message = "A raça é obrigatória")
	    String race,

	    @NotBlank(message = "A classe é obrigatória")
	    String charClass,

	    String subclass,

	    @Min(value = 1, message = "O nível deve ser no mínimo 1")
	    @Max(value = 20, message = "O nível deve ser no máximo 20")
	    Integer level,

	    @Positive(message = "O dinheiro deve ser maior que zero")
	    Double money
	) {}

