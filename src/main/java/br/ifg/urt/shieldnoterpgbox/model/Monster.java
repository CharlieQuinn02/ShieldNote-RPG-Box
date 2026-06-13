package br.ifg.urt.shieldnoterpgbox.model;
import br.ifg.urt.shieldnoterpgbox.model.vo.Size;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import java.util.List;

@Entity
@DiscriminatorValue("MONSTER")

public class Monster extends Character {
	@Enumerated(EnumType.STRING)
    private Size size;

    @Column(name = "monster_type")
    private String monsterType;

    private String subtype;

    @Column(name = "challenge_rating")
    private float challengeRating;

    @Column(name = "xp_base")
    private int xpBase;

    @ElementCollection
    @CollectionTable(name = "monster_actions", joinColumns = @JoinColumn(name = "monster_id"))
    @Column(name = "action")
    private List<String> actions;

    public Monster() {}

    public Size getSize() { return size; }
    public void setSize(Size size) { this.size = size; }

    public String getMonsterType() { return monsterType; }
    public void setMonsterType(String monsterType) { this.monsterType = monsterType; }

    public String getSubtype() { return subtype; }
    public void setSubtype(String subtype) { this.subtype = subtype; }

    public float getChallengeRating() { return challengeRating; }
    public void setChallengeRating(float challengeRating) { this.challengeRating = challengeRating; }

    public int getXpBase() { return xpBase; }
    public void setXpBase(int xpBase) { this.xpBase = xpBase; }

    public List<String> getActions() { return actions; }
    public void setActions(List<String> actions) { this.actions = actions; }
}



