package com.agendamento.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "agendamentos_reuniao")
public class AgendamentoReuniao extends PanacheEntity {

    @Column(nullable = false, length = 150)
    public String titulo;

    @Column(length = 1000)
    public String descricao;

    @Column(nullable = false, length = 200)
    public String responsavel;

    @Column(nullable = false, length = 500)
    public String participantes;

    @Column(length = 200)
    public String localReuniao;

    @Column(nullable = false)
    public LocalDateTime dataHoraInicio;

    @Column(nullable = false)
    public LocalDateTime dataHoraFim;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    public StatusAgendamento status = StatusAgendamento.AGENDADO;

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
