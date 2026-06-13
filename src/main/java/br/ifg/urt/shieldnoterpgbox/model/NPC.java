package br.ifg.urt.shieldnoterpgbox.model;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
public class NPC extends Character {
	private String occupation;

    private String description;

    @Column(name = "is_hostile")
    private Boolean isHostile;

    private String reward;

    public NPC() {}

    public String getOccupation() { return occupation; }
    public void setOccupation(String occupation) { this.occupation = occupation; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Boolean getIsHostile() { return isHostile; }
    public void setIsHostile(Boolean isHostile) { this.isHostile = isHostile; }

    public String getReward() { return reward; }
    public void setReward(String reward) { this.reward = reward; }
}

	


