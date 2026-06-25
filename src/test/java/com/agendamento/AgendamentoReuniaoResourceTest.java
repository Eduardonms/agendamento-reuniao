package com.agendamento;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
class AgendamentoReuniaoResourceTest {

    private String token;

    @BeforeEach
    void autenticar() {
        String registerPayload = """
                {
                  "nome": "Usuario Teste",
                  "email": "teste@example.com",
                  "senha": "senha123"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(registerPayload)
                .when()
                .post("/api/auth/register")
                .then()
                .statusCode(201);

        String loginPayload = """
                {
                  "email": "teste@example.com",
                  "senha": "senha123"
                }
                """;

        token = given()
                .contentType(ContentType.JSON)
                .body(loginPayload)
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .path("token");
    }

    @Test
    void deveCriarEListarAgendamento() {
        String payload = """
                {
                  "titulo": "Reunião de planejamento",
                  "descricao": "Alinhar sprint",
                  "responsavel": "Maria Silva",
                  "participantes": "João, Ana, Pedro",
                  "localReuniao": "Sala 3",
                  "dataHoraInicio": "2026-07-01T10:00:00",
                  "dataHoraFim": "2026-07-01T11:00:00",
                  "status": "AGENDADO"
                }
                """;

        given()
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/api/agendamentos")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("titulo", is("Reunião de planejamento"));

        given()
                .auth().oauth2(token)
                .when()
                .get("/api/agendamentos")
                .then()
                .statusCode(200)
                .body("size()", is(1));
    }
}
