package br.ifg.urt.shieldnoterpgbox.model.vo;

public enum Skill {
	ATHLETICS(Ability.STRENGTH),
    STEALTH(Ability.DEXTERITY),
    PERCEPTION(Ability.WISDOM),
    ARCANA(Ability.INTELLIGENCE),
    INTIMIDATION(Ability.CHARISMA);

    private final Ability ability;

    Skill(Ability ability) {
        this.ability = ability;
    }

    public Ability getAbility() {
        return ability;
    }
}


