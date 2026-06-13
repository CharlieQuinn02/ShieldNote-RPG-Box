package br.ifg.urt.shieldnoterpgbox.dto.response;
import br.ifg.urt.shieldnoterpgbox.model.vo.Ability;
import br.ifg.urt.shieldnoterpgbox.model.vo.Alignment;
import br.ifg.urt.shieldnoterpgbox.model.vo.Atributo;
import br.ifg.urt.shieldnoterpgbox.model.vo.SaveInfo;
import br.ifg.urt.shieldnoterpgbox.model.vo.Skill;
import br.ifg.urt.shieldnoterpgbox.model.vo.SkillInfo;
import java.util.Map;
import java.util.UUID;

public record PlayerCharacterDetailResponse(
	    UUID id,
	    String name,
	    int hitPoints,
	    int currentHitPoints,
	    int armorClass,
	    String speed,
	    Alignment alignment,
	    int proficiencyBonus,
	    Atributo attributes,
	    Map<Skill, SkillInfo> skills,
	    Map<Ability, SaveInfo> savingThrows,
	    String race,
	    String charClass,
	    String subclass,
	    int level,
	    double money
	) {}
