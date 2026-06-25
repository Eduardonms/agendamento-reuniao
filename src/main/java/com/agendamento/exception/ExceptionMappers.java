package com.agendamento.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Provider
public class ExceptionMappers {

    @Provider
    public static class AgendamentoNaoEncontradoMapper implements ExceptionMapper<AgendamentoNaoEncontradoException> {
        @Override
        public Response toResponse(AgendamentoNaoEncontradoException exception) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("mensagem", exception.getMessage()))
                    .build();
        }
    }

    @Provider
    public static class RegraNegocioMapper implements ExceptionMapper<RegraNegocioException> {
        @Override
        public Response toResponse(RegraNegocioException exception) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("mensagem", exception.getMessage()))
                    .build();
        }
    }

    @Provider
    public static class CredenciaisInvalidasMapper implements ExceptionMapper<CredenciaisInvalidasException> {
        @Override
        public Response toResponse(CredenciaisInvalidasException exception) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(Map.of("mensagem", exception.getMessage()))
                    .build();
        }
    }

    @Provider
    public static class EmailJaCadastradoMapper implements ExceptionMapper<EmailJaCadastradoException> {
        @Override
        public Response toResponse(EmailJaCadastradoException exception) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(Map.of("mensagem", exception.getMessage()))
                    .build();
        }
    }

    @Provider
    public static class ConstraintViolationMapper implements ExceptionMapper<ConstraintViolationException> {
        @Override
        public Response toResponse(ConstraintViolationException exception) {
            Map<String, String> erros = exception.getConstraintViolations().stream()
                    .collect(Collectors.toMap(
                            violation -> violation.getPropertyPath().toString(),
                            ConstraintViolation::getMessage,
                            (first, second) -> first,
                            LinkedHashMap::new
                    ));

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("mensagem", "Erro de validação", "erros", erros))
                    .build();
        }
    }
}
