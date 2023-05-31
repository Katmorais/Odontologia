package br.unitins;

import br.unitins.dto.TelefoneDTO;
import br.unitins.dto.TelefoneResponseDTO;
import br.unitins.service.TelefoneService;
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
public class TelefoneResourceTest {
    @Inject
    TelefoneService telefoneService;

    @Test
    public void testGetAll() {
        given()
                .when().get("/telefones")
                .then()
                .statusCode(200);
    }

    @Test
    public void testInsert() {
        TelefoneDTO telefone = new TelefoneDTO(
                "63", "999562578");

        TelefoneResponseDTO telefonecreate = telefoneService.create(telefone);
        given()
                .contentType(ContentType.JSON)
                .body(telefonecreate)
                .when().post("/telefones")
                .then()
                .statusCode(201)
                .body("id", notNullValue(), "codigoArea", is("63"), "numero",
                        is("999562578"));
    }

    @Test
    public void testUpdate() {
        // Adicionando um telefone no banco de dados
        TelefoneDTO telefone = new TelefoneDTO(
                "63", "999562578");
        Long id = telefoneService.create(telefone).id();

        // Criando outro telefone para atualizacao
        TelefoneDTO telefoneupdate = new TelefoneDTO(
                "64", "9994632577");

        TelefoneResponseDTO telefoneatualizado = telefoneService.update(id, telefoneupdate);

        given()
                .contentType(ContentType.JSON)
                .body(telefoneatualizado)
                .when().put("/telefones/" + id)
                .then()
                .statusCode(204);

        // Verificando se os dados foram atualizados no banco de dados
        TelefoneResponseDTO telefoneresponse = telefoneService.findById(id);
        assertThat(telefoneresponse.codigoArea(), is("64"));
        assertThat(telefoneresponse.numero(), is("9994632577"));

    }

    @Test
    public void testDelete() {
        // Adicionando um telefone no banco de dados
        TelefoneDTO telefone = new TelefoneDTO(
                "62", "9994632577");
        Long id = telefoneService.create(telefone).id();
        given()
                .when().delete("/telefones/" + id)
                .then()
                .statusCode(204);

        // verificando se o telefone foi excluido
        TelefoneResponseDTO telefoneResponse = null;
        try {
            telefoneResponse = telefoneService.findById(id);
        } catch (Exception e) {
            assert true;
        } finally {
            assertNull(telefoneResponse);
        }
    }

}

