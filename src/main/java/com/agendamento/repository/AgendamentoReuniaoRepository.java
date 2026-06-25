package com.agendamento.repository;

import com.agendamento.entity.AgendamentoReuniao;
import com.agendamento.entity.StatusAgendamento;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class AgendamentoReuniaoRepository implements PanacheRepository<AgendamentoReuniao> {

    public List<AgendamentoReuniao> listarPorStatus(StatusAgendamento status) {
        return list("status", status);
    }

    public List<AgendamentoReuniao> listarPorResponsavel(String responsavel) {
        return list("responsavel", responsavel);
    }

    public List<AgendamentoReuniao> listarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return list("dataHoraInicio >= ?1 and dataHoraFim <= ?2", inicio, fim);
    }
}
