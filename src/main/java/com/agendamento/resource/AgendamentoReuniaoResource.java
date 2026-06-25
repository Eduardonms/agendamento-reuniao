package com.agendamento.resource;

import com.agendamento.dto.AgendamentoRequest;
import com.agendamento.dto.AgendamentoResponse;
import com.agendamento.entity.StatusAgendamento;
import com.agendamento.service.AgendamentoReuniaoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/api/agendamentos")
@RolesAllowed("USER")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Agendamentos de Reunião", description = "Cadastro e consulta de agendamentos")
public class AgendamentoReuniaoResource {

    @Inject
    AgendamentoReuniaoService service;

    @GET
    @Operation(summary = "Listar todos os agendamentos")
    public List<AgendamentoResponse> listar(
            @QueryParam("status") StatusAgendamento status,
            @QueryParam("responsavel") String responsavel,
            @QueryParam("inicio") LocalDateTime inicio,
            @QueryParam("fim") LocalDateTime fim) {

        if (status != null) {
            return service.listarPorStatus(status);
        }
        if (responsavel != null && !responsavel.isBlank()) {
            return service.listarPorResponsavel(responsavel);
        }
        if (inicio != null && fim != null) {
            return service.listarPorPeriodo(inicio, fim);
        }
        return service.listarTodos();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar agendamento por ID")
    public AgendamentoResponse buscar(@PathParam("id") Long id) {
        return service.buscarPorId(id);
    }

    @POST
    @Operation(summary = "Criar novo agendamento")
    public Response criar(@Valid AgendamentoRequest request) {
        AgendamentoResponse created = service.criar(request);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualizar agendamento existente")
    public AgendamentoResponse atualizar(@PathParam("id") Long id, @Valid AgendamentoRequest request) {
        return service.atualizar(id, request);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Excluir agendamento")
    public Response excluir(@PathParam("id") Long id) {
        service.excluir(id);
        return Response.noContent().build();
    }
}
