package br.ifg.urt.shieldnoterpgbox.model;

import br.ifg.urt.shieldnoterpgbox.model.vo.Ability;
import br.ifg.urt.shieldnoterpgbox.model.vo.Skill;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.HashSet;
import java.util.Set;

@Embeddable
public class Proficiencies {
	@ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Skill> skills = new HashSet<>();

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Ability> savingThrows = new HashSet<>();

    public Proficiencies() {}

    public Set<Skill> getSkills() { return skills; }
    public void setSkills(Set<Skill> skills) { this.skills = skills; }

    public Set<Ability> getSavingThrows() { return savingThrows; }
    public void setSavingThrows(Set<Ability> savingThrows) { this.savingThrows = savingThrows; }

    public boolean isProficientIn(Skill skill) {
        return skills.contains(skill);
    }

    public void addSkillProficiency(Skill skill) {
        skills.add(skill);
    }

    public void removeSkillProficiency(Skill skill) {
        skills.remove(skill);
    }
}



