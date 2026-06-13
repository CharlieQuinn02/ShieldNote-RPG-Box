package br.ifg.urt.shieldnoterpgbox.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import br.ifg.urt.shieldnoterpgbox.enums.PostItCat;
import br.ifg.urt.shieldnoterpgbox.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Notes implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    // RELACIONAMENTO JPA: N Notas para 1 Campanha
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id", nullable = false)
    private Campaign campaign;
    
    @Column(nullable = false)
    private UUID sessionId;
    
    @Column(nullable = false)
    private UUID authorId;
    
    @Column(nullable = false, length = 100)
    private String titulo;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostItCat categoria;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleEnum visibilidade;
    
    @Column(nullable = false)
    private boolean isFixado;
    
    @Column(columnDefinition = "TEXT") 
    private String conteudo;
    
    @Column(nullable = false)
    private LocalDateTime criadoEm;

    
    // MÉTODOS DE REGRA DE NEGÓCIO DO AGREGADO
    
    
    // Método de atualização de conteúdo
    public void atualizarConteudo(String novoTitulo, String novoConteudo, 
                                  PostItCat novaCategoria, RoleEnum novaVisibilidade) {
        this.titulo = novoTitulo;
        this.conteudo = novoConteudo;
        this.categoria = novaCategoria;
        this.visibilidade = novaVisibilidade;
    }

    public void alternarFixacao() { 
        this.isFixado = !this.isFixado; 
    }

    
    // GETTERS E SETTERS
    
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }

    public UUID getAuthorId() {
        return authorId;
    }

    public void setAuthorId(UUID authorId) {
        this.authorId = authorId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public PostItCat getCategoria() {
        return categoria;
    }

    public void setCategoria(PostItCat categoria) {
        this.categoria = categoria;
    }

    public RoleEnum getVisibilidade() {
        return visibilidade;
    }

    public void setVisibilidade(RoleEnum visibilidade) {
        this.visibilidade = visibilidade;
    }

    public boolean isFixado() {
        return isFixado;
    }

    public void setFixado(boolean isFixado) {
        this.isFixado = isFixado;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
}