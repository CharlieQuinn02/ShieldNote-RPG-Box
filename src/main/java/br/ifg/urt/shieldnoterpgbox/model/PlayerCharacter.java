package br.ifg.urt.shieldnoterpgbox.model;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PLAYER")
public class PlayerCharacter extends Character {
	private String race;

    @Column(name = "char_class")
    private String charClass;

    private String subclass;

    private int level;

    private double money;

    public PlayerCharacter() {}

    public String getRace() { return race; }
    public void setRace(String race) { this.race = race; }

    public String getCharClass() { return charClass; }
    public void setCharClass(String charClass) { this.charClass = charClass; }

    public String getSubclass() { return subclass; }
    public void setSubclass(String subclass) { this.subclass = subclass; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public double getMoney() { return money; }
    public void setMoney(double money) { this.money = money; }
}

	


