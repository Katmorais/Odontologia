package br.unitins;

import br.unitins.dto.ProdutoDTO;
import br.unitins.dto.ProdutoResponseDTO;
import br.unitins.service.ProdutoService;
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
public class ProdutoResourceTest {
    @Inject
    ProdutoService produtoService;

    @Test
    public void testGetAll() {
        given()
                .when().get("/produtos")
                .then()
                .statusCode(200);
    }

    @Test
    public void testInsert() {
        ProdutoDTO produto = new ProdutoDTO(
                "Cadeira", "Odontologica", 50, 7.000);

        ProdutoResponseDTO produtocreate = produtoService.create(produto);
        given()
                .contentType(ContentType.JSON)
                .body(produtocreate)
                .when().post("/produtos")
                .then()
                .statusCode(201)
                .body("id", notNullValue(), "nome", is("Cadeira"), "descricao",
                        is("Odontologica"),
                        "preco", is(7.000F), "estoque", is(50));
    }

    @Test
    public void testUpdate() {
        // Adicionando um produto no banco de dados
        ProdutoDTO produto = new ProdutoDTO(
                "Cadeira", "Odontologica", 50, 7.000);
        Long id = produtoService.create(produto).id();

        // Criando outro produto para atualizacao
        ProdutoDTO produtoupdate = new ProdutoDTO(
                "Caneta", "azul", 30, 4.000);

        ProdutoResponseDTO produtoatualizado = produtoService.update(id, produtoupdate);

        given()
                .contentType(ContentType.JSON)
                .body(produtoatualizado)
                .when().put("/produtos/" + id)
                .then()
                .statusCode(204);

        // Verificando se os dados foram atualizados no banco de dados
        ProdutoResponseDTO produtoresponse = produtoService.findById(id);
        assertThat(produtoresponse.nome(), is("Caneta"));
        assertThat(produtoresponse.descricao(), is("azul"));
        assertThat(produtoresponse.preco(), is(4.000));
        assertThat(produtoresponse.estoque(), is(30));
    }

    @Test
    public void testDelete() {
        // Adicionando um produto no banco de dados
        ProdutoDTO produto = new ProdutoDTO(
                "Cadeira", "azul", 30, 4.000);
        Long id = produtoService.create(produto).id();
        given()
                .when().delete("/produtos/" + id)
                .then()
                .statusCode(204);

        // verificando se o produto foi excluido
        ProdutoResponseDTO produtoresponse = null;
        try {
            produtoresponse = produtoService.findById(id);
        } catch (Exception e) {
            assert true;
        } finally {
            assertNull(produtoresponse);
        }

    }
}