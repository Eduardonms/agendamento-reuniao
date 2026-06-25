package com.agendamento.dto;

import com.agendamento.entity.StatusAgendamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public record AgendamentoRequest(
        @NotBlank @Size(max = 150) String titulo,
        @Size(max = 1000) String descricao,
        @NotBlank @Size(max = 200) String responsavel,
        @NotBlank @Size(max = 500) String participantes,
        @Size(max = 200) String localReuniao,
        @NotNull LocalDateTime dataHoraInicio,
        @NotNull LocalDateTime dataHoraFim,
        StatusAgendamento status
) {
}
