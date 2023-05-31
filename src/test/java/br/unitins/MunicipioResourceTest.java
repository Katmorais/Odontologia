package br.unitins;

import br.unitins.dto.EstadoDTO;
import br.unitins.dto.MunicipioDTO;
import br.unitins.dto.MunicipioResponseDTO;
import br.unitins.model.Estado;
import br.unitins.service.EstadoService;
import br.unitins.service.MunicipioService;
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
public class MunicipioResourceTest {

    @Inject
    MunicipioService municipioService;

    @Inject
    EstadoService estadoService;

    @Test
    public void testGetAll() {
        given()
                .when().get("/municipios")
                .then()
                .statusCode(200);
    }

    @Test
    public void testInsert() {
        Long id = estadoService.create(new EstadoDTO("Tocantins", "TO")).id();
        MunicipioDTO municipio = new MunicipioDTO(
                "Palmas", id);

        given()
                .contentType(ContentType.JSON)
                .body(municipio)
                .when().post("/municipios")
                .then()
                .statusCode(201)
                .body("id", notNullValue(), "nome", is("Palmas"), "estado",
                        notNullValue(Estado.class));
    }

    @Test
    public void testUpdate() {
        Long id = estadoService.create(new EstadoDTO("Tocantins", "TO")).id();
        // Adicionando um produto no banco de dados
        MunicipioDTO municipio = new MunicipioDTO(
                "Miranorte", id);
        Long idMunicipio = municipioService.create(municipio).id();

        // Criando outro produto para atualizacao
        MunicipioDTO municipioUpdate = new MunicipioDTO(
                "Barrolândia", id);

        given()
                .contentType(ContentType.JSON)
                .body(municipioUpdate)
                .when().put("/municipios/" + idMunicipio)
                .then()
                .statusCode(204);

        // Verificando se os dados foram atualizados no banco de dados
        MunicipioResponseDTO municipioresponse = municipioService.findById(id);
        assertThat(municipioresponse.nome(), is("Barrolândia"));
        assertThat(municipioresponse.estado(), notNullValue());
    }

    @Test
    public void testDelete() {
        Long id = estadoService.create(new EstadoDTO("Tocantins", "TO")).id();
        MunicipioDTO municipio= new MunicipioDTO(
                "Palmas", id);

        Long idMunicipio = municipioService.create(municipio).id();

        given()
                .when().delete("/municipios/" + idMunicipio)
                .then()
                .statusCode(204);

        // verificando se a cidade foi excluida
        MunicipioResponseDTO municipioResponse = null;
        try {
            municipioResponse = municipioService.findById(id);
        } catch (Exception e) {
            assert true;
        } finally {
            assertNull(municipioResponse);
        }
    }
}