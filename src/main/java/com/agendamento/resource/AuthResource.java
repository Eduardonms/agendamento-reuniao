package com.agendamento.resource;

import com.agendamento.dto.AuthResponse;
import com.agendamento.dto.LoginRequest;
import com.agendamento.dto.RegisterRequest;
import com.agendamento.service.AuthService;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Autenticação", description = "Registro e login com JWT")
public class AuthResource {

    @Inject
    AuthService authService;

    @POST
    @Path("/register")
    @PermitAll
    @Operation(summary = "Registrar novo usuário")
    public Response registrar(@Valid RegisterRequest request) {
        AuthResponse response = authService.registrar(request);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @POST
    @Path("/login")
    @PermitAll
    @Operation(summary = "Autenticar usuário")
    public AuthResponse login(@Valid LoginRequest request) {
        return authService.login(request);
    }
}
