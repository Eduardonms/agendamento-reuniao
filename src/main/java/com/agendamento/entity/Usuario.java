package com.agendamento.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
public class Usuario extends PanacheEntity {

    @Column(nullable = false, length = 100)
    public String nome;

    @Column(nullable = false, unique = true, length = 150)
    public String email;

    @Column(nullable = false, length = 100)
    public String senhaHash;

    @Column(nullable = false, updatable = false)
    public LocalDateTime criadoEm;

    @Column(nullable = false)
    public LocalDateTime atualizadoEm;

    @PrePersist
    void prePersist() {
        LocalDateTime agora = LocalDateTime.now();
        criadoEm = agora;
        atualizadoEm = agora;
    }

    @PreUpdate
    void preUpdate() {
        atualizadoEm = LocalDateTime.now();
    }
}
