package br.unitins;

import br.unitins.dto.EstadoDTO;
import br.unitins.dto.EstadoResponseDTO;
import br.unitins.service.EstadoService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import jakarta.inject.Inject;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

@QuarkusTest
public class EstadoResourceTest {

    @Inject
    EstadoService estadoService;

    @Test
    public void testGetAll() {
        given()
                .when().get("/estados")
                .then()
                .statusCode(200);
    }

    @Test
    public void testInsert() {
        EstadoDTO estado = new EstadoDTO(
                "Tocantins", "TO");

        EstadoResponseDTO estadocreate = estadoService.create(estado);
        given()
                .contentType(ContentType.JSON)
                .body(estadocreate)
                .when().post("/estados")
                .then()
                .statusCode(201)
                .body("id", notNullValue(), "nome", is("Tocantins"), "sigla",
                        is("TO"));

    }

    @Test
    public void testUpdate() {
        // Adicionando um produto no banco de dados
        EstadoDTO produto = new EstadoDTO(
                "Goias", "TO");
        Long id = estadoService.create(produto).id();

        // Criando outro produto para atualizacao
        EstadoDTO estadoUpdate = new EstadoDTO(
                "Bahia", "Ba");

        EstadoResponseDTO estadoAtualizado = estadoService.update(id, estadoUpdate);

        given()
                .contentType(ContentType.JSON)
                .body(estadoAtualizado)
                .when().put("/estados/" + id)
                .then()
                .statusCode(204);

        // Verificando se os dados foram atualizados no banco de dados
        EstadoResponseDTO estadoResponse = estadoService.findById(id);
        assertThat(estadoResponse.nome(), is("Bahia"));
        assertThat(estadoResponse.sigla(), is("Ba"));
    }

    @Test
    public void testDelete() {
        // Adicionando um estado no banco de dados
        EstadoDTO estado = new EstadoDTO(
                "Goias", "GO");
        Long id = estadoService.create(estado).id();
        given()
                .when().delete("/estados/" + id)
                .then()
                .statusCode(204);

        // verificando se o estado foi excluido
        EstadoResponseDTO estadoResponse = null;
        try {
            estadoResponse = estadoService.findById(id);
        } catch (Exception e) {
            assert true;
        } finally {
            assertNull(estadoResponse);
        }
    }
}

