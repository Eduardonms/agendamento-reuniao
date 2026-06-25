package com.agendamento.service;

import com.agendamento.dto.AgendamentoRequest;
import com.agendamento.dto.AgendamentoResponse;
import com.agendamento.entity.AgendamentoReuniao;
import com.agendamento.entity.StatusAgendamento;
import com.agendamento.exception.AgendamentoNaoEncontradoException;
import com.agendamento.exception.RegraNegocioException;
import com.agendamento.repository.AgendamentoReuniaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class AgendamentoReuniaoService {

    @Inject
    AgendamentoReuniaoRepository repository;

    public List<AgendamentoResponse> listarTodos() {
        return repository.listAll().stream()
                .map(AgendamentoResponse::from)
                .toList();
    }

    public AgendamentoResponse buscarPorId(Long id) {
        return AgendamentoResponse.from(buscarEntidade(id));
    }

    public List<AgendamentoResponse> listarPorStatus(StatusAgendamento status) {
        return repository.listarPorStatus(status).stream()
                .map(AgendamentoResponse::from)
                .toList();
    }

    public List<AgendamentoResponse> listarPorResponsavel(String responsavel) {
        return repository.listarPorResponsavel(responsavel).stream()
                .map(AgendamentoResponse::from)
                .toList();
    }

    public List<AgendamentoResponse> listarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        validarPeriodo(inicio, fim);
        return repository.listarPorPeriodo(inicio, fim).stream()
                .map(AgendamentoResponse::from)
                .toList();
    }

    @Transactional
    public AgendamentoResponse criar(AgendamentoRequest request) {
        validarDatas(request.dataHoraInicio(), request.dataHoraFim());

        AgendamentoReuniao entity = new AgendamentoReuniao();
        aplicarDados(entity, request);
        repository.persist(entity);

        return AgendamentoResponse.from(entity);
    }

    @Transactional
    public AgendamentoResponse atualizar(Long id, AgendamentoRequest request) {
        validarDatas(request.dataHoraInicio(), request.dataHoraFim());

        AgendamentoReuniao entity = buscarEntidade(id);
        aplicarDados(entity, request);

        return AgendamentoResponse.from(entity);
    }

    @Transactional
    public void excluir(Long id) {
        AgendamentoReuniao entity = buscarEntidade(id);
        repository.delete(entity);
    }

    private AgendamentoReuniao buscarEntidade(Long id) {
        AgendamentoReuniao entity = repository.findById(id);
        if (entity == null) {
            throw new AgendamentoNaoEncontradoException(id);
        }
        return entity;
    }

    private void aplicarDados(AgendamentoReuniao entity, AgendamentoRequest request) {
        entity.titulo = request.titulo();
        entity.descricao = request.descricao();
        entity.responsavel = request.responsavel();
        entity.participantes = request.participantes();
        entity.localReuniao = request.localReuniao();
        entity.dataHoraInicio = request.dataHoraInicio();
        entity.dataHoraFim = request.dataHoraFim();
        entity.status = request.status() != null ? request.status() : StatusAgendamento.AGENDADO;
    }

    private void validarDatas(LocalDateTime inicio, LocalDateTime fim) {
        if (!fim.isAfter(inicio)) {
            throw new RegraNegocioException("A data/hora de fim deve ser posterior à data/hora de início.");
        }
    }

    private void validarPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        if (fim.isBefore(inicio)) {
            throw new RegraNegocioException("O fim do período deve ser posterior ou igual ao início.");
        }
    }
}
