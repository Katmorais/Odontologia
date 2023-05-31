package br.unitins;


import br.unitins.dto.EquipamentoOdontologicoDTO;
import br.unitins.dto.EquipamentoOdontologicoResponseDTO;
import br.unitins.service.EquipamentoOdontologicoService;
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
public class EquipamentoOdontologicoResourceTest {
    @Inject
    EquipamentoOdontologicoService equipamentoOdontologicoService;

    @Test
    public void testGetAll() {
        given()
                .when().get("/equipamentosOdontologicos")
                .then()
                .statusCode(200);
    }

    @Test
    public void testInsert() {
        EquipamentoOdontologicoDTO equipamentoOdontologico = new EquipamentoOdontologicoDTO(
                "Obter imagem");

        EquipamentoOdontologicoResponseDTO EquipamentoOdontologicocreate = equipamentoOdontologicoService.create(equipamentoOdontologico);
        given()
                .contentType(ContentType.JSON)
                .body(EquipamentoOdontologicocreate)
                .when().post("/equipamentosOdontologicos")
                .then()
                .statusCode(201)
                .body("id", notNullValue(), "finalidade", is("Obter imagem"));
    }

    @Test
    public void testUpdate() {
        // Adicionando um equipamento odontologico no banco de dados
        EquipamentoOdontologicoDTO equipamentoOdontologico = new EquipamentoOdontologicoDTO(
                "Obter imagem");
        Long id = equipamentoOdontologicoService.create(equipamentoOdontologico).id();

        // Criando outro equipamento odontologico para atualizacao
        EquipamentoOdontologicoDTO telefoneupdate = new EquipamentoOdontologicoDTO(
                "Inserir implantes");

        EquipamentoOdontologicoResponseDTO equipamentoOdontologicoAtualizado = equipamentoOdontologicoService.update(id, telefoneupdate);

        given()
                .contentType(ContentType.JSON)
                .body(equipamentoOdontologicoAtualizado)
                .when().put("/equipamentosOdontologicos/" + id)
                .then()
                .statusCode(204);

        // Verificando se os dados foram atualizados no banco de dados
        EquipamentoOdontologicoResponseDTO equipamentoOdontologicoResponse = equipamentoOdontologicoService.findById(id);
        assertThat(equipamentoOdontologicoResponse.finalidade(), is("Inserir implantes"));

    }

    @Test
    public void testDelete() {
        // Adicionando um equipamento no banco de dados
        EquipamentoOdontologicoDTO equipamentoOdontologico = new EquipamentoOdontologicoDTO(
                "fazer limpeza");
        Long id = equipamentoOdontologicoService.create(equipamentoOdontologico).id();
        given()
                .when().delete("/equipamentosOdontologicos/" + id)
                .then()
                .statusCode(204);

        // verificando se o equipamento foi excluido
        EquipamentoOdontologicoResponseDTO equipamentoOdontologicoResponse = null;
        try {
            equipamentoOdontologicoResponse = equipamentoOdontologicoService.findById(id);
        } catch (Exception e) {
            assert true;
        } finally {
            assertNull(equipamentoOdontologicoResponse);
        }

    }
}




