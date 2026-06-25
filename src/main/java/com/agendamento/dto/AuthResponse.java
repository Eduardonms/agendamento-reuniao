package com.agendamento.dto;

public record AuthResponse(
        String token,
        String tipo,
        String nome,
        String email
) {
    public static AuthResponse of(String token, String nome, String email) {
        return new AuthResponse(token, "Bearer", nome, email);
    }
}
