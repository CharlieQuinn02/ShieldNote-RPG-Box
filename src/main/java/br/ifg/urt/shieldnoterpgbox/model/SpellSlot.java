package br.ifg.urt.shieldnoterpgbox.model;

import jakarta.persistence.*;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "spell_slots")
public class SpellSlot {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.UUID) 
    private UUID id;
    
    @Column(nullable = false) private int nivel;
    @Column(nullable = false) private int quantidadeAtual;
    @Column(nullable = false) private int quantidadeMaxima;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spellcasting_id", nullable = false)
    @JsonIgnore 
    private SpellCasting spellCasting;

    // Construtor vazio para o JPA
    public SpellSlot() {
    }

    // para usar no service
    public SpellSlot(int nivel, int quantidadeAtual, int quantidadeMaxima, SpellCasting spellCasting) {
        this.nivel = nivel;
        this.quantidadeAtual = quantidadeAtual;
        this.quantidadeMaxima = quantidadeMaxima;
        this.spellCasting = spellCasting;
    }

    
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public int getNivel() { return nivel; }
    public void setNivel(int nivel) { this.nivel = nivel; }
    
    public int getQuantidadeAtual() { return quantidadeAtual; }
    public void setQuantidadeAtual(int quantidadeAtual) { this.quantidadeAtual = quantidadeAtual; }
    
    public int getQuantidadeMaxima() { return quantidadeMaxima; }
    public void setQuantidadeMaxima(int quantidadeMaxima) { this.quantidadeMaxima = quantidadeMaxima; }
    
    public SpellCasting getSpellCasting() { return spellCasting; }
    public void setSpellCasting(SpellCasting spellCasting) { this.spellCasting = spellCasting; }

    
    public boolean isDisponivel() { return quantidadeAtual > 0; }
    
    public boolean gastarSlot() { 
        if (!isDisponivel()) return false; 
        this.quantidadeAtual--; 
        return true; 
    }
    
    public void recuperar() { this.quantidadeAtual = this.quantidadeMaxima; }
}