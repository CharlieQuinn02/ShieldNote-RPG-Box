package br.ifg.urt.shieldnoterpgbox.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity 
@Table(name = "spells")
public class Spell {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.UUID) 
    private UUID id;
    
    @Column(nullable = false) 
    private String nome;
    
    @Column(columnDefinition = "TEXT") 
    private String descricao;
    
    @Column(nullable = false) 
    private int level;
    
    @Column(nullable = false) 
    private String castTime;
    
    //Usamos o name com crases (` `) para o MySQL não confundir com comando reservado
    @Column(name = "`range`", nullable = false) 
    private String range;
    
    @Column(nullable = false) 
    private String duration;
    
    @Column(nullable = false) 
    private String school;
    
    @Column(columnDefinition = "TEXT") 
    private String components;

    // --- CONSTRUTORES ---
    public Spell() {
    }

    public Spell(String nome, String descricao, int level, String castTime, String range, String duration, String school, String components) {
        this.nome = nome;
        this.descricao = descricao;
        this.level = level;
        this.castTime = castTime;
        this.range = range;
        this.duration = duration;
        this.school = school;
        this.components = components;
    }

    // --- GETTERS E SETTERS ---
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public String getCastTime() { return castTime; }
    public void setCastTime(String castTime) { this.castTime = castTime; }

    public String getRange() { return range; }
    public void setRange(String range) { this.range = range; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public String getSchool() { return school; }
    public void setSchool(String school) { this.school = school; }

    public String getComponents() { return components; }
    public void setComponents(String components) { this.components = components; }

    // métodos de negócio
    public Double getIndiceComplexidade() { 
        return (double) (this.level * 10); 
    }
}