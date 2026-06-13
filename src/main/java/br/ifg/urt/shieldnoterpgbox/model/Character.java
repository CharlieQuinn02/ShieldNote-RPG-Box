package br.ifg.urt.shieldnoterpgbox.model;

import br.ifg.urt.shieldnoterpgbox.model.vo.Alignment;
import br.ifg.urt.shieldnoterpgbox.model.vo.Atributo;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "tb_character")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)

public class Character {
	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(name = "hit_points")
    private int hitPoints;

    @Column(name = "current_hit_points")
    private int currentHitPoints;

    @Column(name = "armor_class")
    private int armorClass;

    @Column(name = "speed")
    private String speed;

    @Enumerated(EnumType.STRING)
    private Alignment alignment;

    @Column(name = "proficiency_bonus")
    private int proficiencyBonus;

    @Embedded
    private Atributo attributes;

    @Embedded
    private Proficiencies proficiencies;

    public Character() {
        this.attributes = new Atributo();
        this.proficiencies = new Proficiencies();
        this.proficiencyBonus = 2;
    }

    public void adjustHitPoints(int amount) {
        this.currentHitPoints += amount;
        if (this.currentHitPoints < 0) {
            this.currentHitPoints = 0;
        }
        if (this.currentHitPoints > this.hitPoints) {
            this.currentHitPoints = this.hitPoints;
        }
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getHitPoints() { return hitPoints; }
    public void setHitPoints(int hitPoints) { this.hitPoints = hitPoints; }

    public int getCurrentHitPoints() { return currentHitPoints; }
    public void setCurrentHitPoints(int currentHitPoints) { this.currentHitPoints = currentHitPoints; }

    public int getArmorClass() { return armorClass; }
    public void setArmorClass(int armorClass) { this.armorClass = armorClass; }

    public String getSpeed() { return speed; }
    public void setSpeed(String speed) { this.speed = speed; }

    public Alignment getAlignment() { return alignment; }
    public void setAlignment(Alignment alignment) { this.alignment = alignment; }

    public int getProficiencyBonus() { return proficiencyBonus; }
    public void setProficiencyBonus(int proficiencyBonus) { this.proficiencyBonus = proficiencyBonus; }

    public Atributo getAttributes() { return attributes; }
    public void setAttributes(Atributo attributes) { this.attributes = attributes; }

    public Proficiencies getProficiencies() { return proficiencies; }
    public void setProficiencies(Proficiencies proficiencies) { this.proficiencies = proficiencies; }
}

	


