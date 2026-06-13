package br.ifg.urt.shieldnoterpgbox.service;

import br.ifg.urt.shieldnoterpgbox.model.vo.Ability;
import br.ifg.urt.shieldnoterpgbox.model.vo.SaveInfo;
import br.ifg.urt.shieldnoterpgbox.model.vo.Skill;
import br.ifg.urt.shieldnoterpgbox.model.vo.SkillInfo;
import br.ifg.urt.shieldnoterpgbox.model.Character;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.stereotype.Component;


@Component
public class CharacterCalculator {

    public int calculateModifier(int score) {
        return (int) Math.floor((score - 10) / 2.0);
    }

    public Map<Ability, SaveInfo> calculateSavingThrows(Character character) {
        Map<Ability, SaveInfo> result = new EnumMap<>(Ability.class);
        for (Ability ability : Ability.values()) {
            int modifier = calculateModifier(character.getAttributes().getScore(ability));
            boolean proficient = character.getProficiencies().getSavingThrows().contains(ability);
            if (proficient) {
                modifier += character.getProficiencyBonus();
            }
            result.put(ability, new SaveInfo(modifier, proficient));
        }
        return result;
    }

    public Map<Skill, SkillInfo> calculateSkills(Character character) {
        Map<Skill, SkillInfo> result = new EnumMap<>(Skill.class);
        for (Skill skill : Skill.values()) {
            int modifier = calculateModifier(character.getAttributes().getScore(skill.getAbility()));
            boolean proficient = character.getProficiencies().getSkills().contains(skill);
            if (proficient) {
                modifier += character.getProficiencyBonus();
            }
            result.put(skill, new SkillInfo(modifier, proficient));
        }
        return result;
    }
}

