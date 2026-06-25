package com.agendamento.service;

import com.agendamento.dto.AuthResponse;
import com.agendamento.dto.LoginRequest;
import com.agendamento.dto.RegisterRequest;
import com.agendamento.entity.Usuario;
import com.agendamento.exception.CredenciaisInvalidasException;
import com.agendamento.exception.EmailJaCadastradoException;
import com.agendamento.repository.UsuarioRepository;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.Duration;
import java.util.Set;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class AuthService {

    @Inject
    UsuarioRepository usuarioRepository;

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    @ConfigProperty(name = "jwt.expiration-seconds", defaultValue = "86400")
    long expirationSeconds;

    @Transactional
    public AuthResponse registrar(RegisterRequest request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new EmailJaCadastradoException(request.email());
        }

        Usuario usuario = new Usuario();
        usuario.nome = request.nome();
        usuario.email = request.email().toLowerCase();
        usuario.senhaHash = BcryptUtil.bcryptHash(request.senha());
        usuarioRepository.persist(usuario);

        return AuthResponse.of(gerarToken(usuario), usuario.nome, usuario.email);
    }

    public AuthResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.email().toLowerCase())
                .orElseThrow(CredenciaisInvalidasException::new);

        if (!BcryptUtil.matches(request.senha(), usuario.senhaHash)) {
            throw new CredenciaisInvalidasException();
        }

        return AuthResponse.of(gerarToken(usuario), usuario.nome, usuario.email);
    }

    private String gerarToken(Usuario usuario) {
        return Jwt.issuer(issuer)
                .upn(usuario.email)
                .subject(String.valueOf(usuario.id))
                .claim("nome", usuario.nome)
                .groups(Set.of("USER"))
                .expiresIn(Duration.ofSeconds(expirationSeconds))
                .sign();
    }
}
