package br.ifg.urt.shieldnoterpgbox.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity 
@Table(name = "spell_castings")
public class SpellCasting {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.UUID) 
    private UUID id;
    
    @Column(nullable = false) 
    private UUID characterId;
    
    @Column(nullable = false) 
    private String atributoConj;
    
    @Column(nullable = false) 
    private int cdMagias;
    
    @Column(nullable = false) 
    private int bonusAtaqueMag;
    
    @Column(nullable = false) 
    private UUID idMagias;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "spellcasting_magias_conhecidas", joinColumns = @JoinColumn(name = "spellcasting_id"))
    @Column(name = "spell_id") 
    private List<String> magiasConhecidas = new ArrayList<>();

    @OneToMany(mappedBy = "spellCasting", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<SpellSlot> slots = new ArrayList<>();

    // construtores
    public SpellCasting() {
    }

    public SpellCasting(UUID characterId, String atributoConj, int cdMagias, int bonusAtaqueMag, UUID idMagias) {
        this.characterId = characterId;
        this.atributoConj = atributoConj;
        this.cdMagias = cdMagias;
        this.bonusAtaqueMag = bonusAtaqueMag;
        this.idMagias = idMagias;
    }

    // get e set
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getCharacterId() { return characterId; }
    public void setCharacterId(UUID characterId) { this.characterId = characterId; }

    public String getAtributoConj() { return atributoConj; }
    public void setAtributoConj(String atributoConj) { this.atributoConj = atributoConj; }

    public int getCdMagias() { return cdMagias; }
    public void setCdMagias(int cdMagias) { this.cdMagias = cdMagias; }

    public int getBonusAtaqueMag() { return bonusAtaqueMag; }
    public void setBonusAtaqueMag(int bonusAtaqueMag) { this.bonusAtaqueMag = bonusAtaqueMag; }

    public UUID getIdMagias() { return idMagias; }
    public void setIdMagias(UUID idMagias) { this.idMagias = idMagias; }

    public List<String> getMagiasConhecidas() { return magiasConhecidas; }
    public void setMagiasConhecidas(List<String> magiasConhecidas) { this.magiasConhecidas = magiasConhecidas; }

    public List<SpellSlot> getSlots() { return slots; }
    public void setSlots(List<SpellSlot> slots) { this.slots = slots; }

    // métodos de negócio
    public boolean conjurar(UUID spellId, int nivelSlot) {
        if (!magiasConhecidas.contains(spellId.toString())) return false;
        return slots.stream().filter(s -> s.getNivel() == nivelSlot).findFirst().map(SpellSlot::gastarSlot).orElse(false);
    }

    public void recuperarSlots() { 
        slots.forEach(SpellSlot::recuperar); 
    }
}