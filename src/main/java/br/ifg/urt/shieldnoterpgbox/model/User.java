package br.ifg.urt.shieldnoterpgbox.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity 
@Table(name = "users")
public class User {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.UUID) 
    private UUID id;
    
    @Column(nullable = false) 
    private String nome;
    
    @Column(nullable = false, unique = true) 
    private String email;
    
    @Column(nullable = false) 
    private String senhaHash;
    
    @Column(nullable = false, updatable = false) 
    private LocalDateTime criadoEm;

    // construtores
    public User() {
    }

    public User(String nome, String email, String senhaHash) {
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
    }

    // get e set
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenhaHash() { return senhaHash; }
    public void setSenhaHash(String senhaHash) { this.senhaHash = senhaHash; }

    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }

    // métodos de ciclo de vida do Jpa
    @PrePersist 
    public void prePersist() { 
        this.criadoEm = LocalDateTime.now(); 
    }
}