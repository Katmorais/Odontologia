package br.unitins;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import br.unitins.dto.EnderecoDTO;
import br.unitins.dto.EnderecoResponseDTO;
import br.unitins.service.EnderecoService;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;


import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class EnderecoResourceTest {
    @Inject
    EnderecoService enderecoService;

    @Test
    public void testGetAll() {
        given()
                .when().get("/enderecos")
                .then()
                .statusCode(200);
    }

    @Test
    public void testInsert() {
        EnderecoDTO endereco = new EnderecoDTO(
                "77023110", "Centro", "30", "Casa", 1L);

        EnderecoResponseDTO enderecocreate = enderecoService.create(endereco);
        given()
                .contentType(ContentType.JSON)
                .body(enderecocreate)
                .when().post("/enderecos")
                .then()
                .statusCode(201)
                .body("id", notNullValue(), "cep", is(77023110), "bairro",
                        is("Centro"), "numero", is(30), "complemento", is("Casa"), "idMunicipio", ("1L"));
    }

    @Test
    public void testUpdate() {
        // Adicionando um endereço no banco de dados
        EnderecoDTO endereco = new EnderecoDTO(
                "77023110", "Centro", "30", "Casa", 1L);
        Long id = enderecoService.create(endereco).id();

        // Criando outro endereco para atualizacao
        EnderecoDTO enderecoUpdate = new EnderecoDTO(
                "77500000", "Setor Aeroporto ", "1856", "Casa", 2L);

        EnderecoResponseDTO enderecoAtualizado = enderecoService.update(id, enderecoUpdate);

        given()
                .contentType(ContentType.JSON)
                .body(enderecoAtualizado)
                .when().put("/enderecos/" + id)
                .then()
                .statusCode(204);

        // Verificando se os dados foram atualizados no banco de dados
        EnderecoResponseDTO enderecoResponse = enderecoService.findById(id);
        assertThat(enderecoResponse.cep(), is("77500000"));
        assertThat(enderecoResponse.bairro(), is("Setor Aeroporto"));
        assertThat(enderecoResponse.numero(), is(1856));
        assertThat(enderecoResponse.complemento(), is("Casa"));
        assertThat(enderecoResponse.idMunicipio(), is(2L));
    }

    @Test
    public void testDelete() {
        // Adicionando um endereco no banco de dados
        EnderecoDTO endereco = new EnderecoDTO(
                "77264750", "Setor Lago Sul", "20", "Casa",2L);
        Long id = enderecoService.create(endereco).id();
        given()
                .when().delete("/enderecos/" + id)
                .then()
                .statusCode(204);

        // verificando se o produto foi excluido
        EnderecoResponseDTO enderecoResponse = null;
        try {
            enderecoResponse = enderecoService.findById(id);
        } catch (Exception e) {
            assert true;
        } finally {
            assertNull(enderecoResponse);
        }

    }
}
