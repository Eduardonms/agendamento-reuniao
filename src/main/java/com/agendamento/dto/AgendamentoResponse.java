package com.agendamento.dto;

import com.agendamento.entity.AgendamentoReuniao;
import com.agendamento.entity.StatusAgendamento;
import java.time.LocalDateTime;

public record AgendamentoResponse(
        Long id,
        String titulo,
        String descricao,
        String responsavel,
        String participantes,
        String localReuniao,
        LocalDateTime dataHoraInicio,
        LocalDateTime dataHoraFim,
        StatusAgendamento status,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {
    public static AgendamentoResponse from(AgendamentoReuniao entity) {
        return new AgendamentoResponse(
                entity.id,
                entity.titulo,
                entity.descricao,
                entity.responsavel,
                entity.participantes,
                entity.localReuniao,
                entity.dataHoraInicio,
                entity.dataHoraFim,
                entity.status,
                entity.criadoEm,
                entity.atualizadoEm
        );
    }
}
