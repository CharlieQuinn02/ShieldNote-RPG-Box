package br.ifg.urt.shieldnoterpgbox.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.ifg.urt.shieldnoterpgbox.enums.StatusEnum;
import br.ifg.urt.shieldnoterpgbox.model.vo.CapacidadeJogadores;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Campaign implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false)
    private UUID mestreId;
    
    @Column(nullable = false, length = 100)
    private String titulo;
    
    @Column(columnDefinition = "TEXT")
    private String descricao;
    
    @Column(nullable = false, length = 50)
    private String sistema;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEnum status;
    
    // colocamos o VO
    @Embedded
    private CapacidadeJogadores capacidade;
    
    @Column(nullable = false)
    private LocalDateTime criadaEm;

    
    // RELACIONAMENTO JPA: 1 Campanha para N Notas
    
    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notes> notes = new ArrayList<>();

    // Métodos de negócio
    public void pausar() { this.status = StatusEnum.PAUSADA; }
    public UUID iniciarSessao() { return UUID.randomUUID(); }
    public void encerrar() { this.status = StatusEnum.FINALIZADA; }
    
    // Getters e Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public UUID getMestreId() { return mestreId; }
    public void setMestreId(UUID mestreId) { this.mestreId = mestreId; }
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    
    public String getSistema() { return sistema; }
    public void setSistema(String sistema) { this.sistema = sistema; }
    
    public StatusEnum getStatus() { return status; }
    public void setStatus(StatusEnum status) { this.status = status; }
    
    // Getters e Setters do novo vo
    public CapacidadeJogadores getCapacidade() { return capacidade; }
    public void setCapacidade(CapacidadeJogadores capacidade) { this.capacidade = capacidade; }
    
    public LocalDateTime getCriadaEm() { return criadaEm; }
    public void setCriadaEm(LocalDateTime criadaEm) { this.criadaEm = criadaEm; }

    // Getters e Setters da Lista de Notas
    public List<Notes> getNotes() { return notes; }
    public void setNotes(List<Notes> notes) { this.notes = notes; }
}