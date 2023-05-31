//package br.unitins;
//
//import br.unitins.dto.EnderecoDTO;
//import br.unitins.dto.TelefoneDTO;
//import br.unitins.dto.UsuarioDTO;
//import br.unitins.dto.UsuarioResponseDTO;
//import br.unitins.service.UsuarioService;
//import io.quarkus.test.junit.QuarkusTest;
//import io.restassured.http.ContentType;
//import org.junit.jupiter.api.Test;
//import jakarta.inject.Inject;
//import java.util.ArrayList;
//import java.util.List;
//import static io.restassured.RestAssured.given;
//import static org.hamcrest.CoreMatchers.is;
//import static org.hamcrest.CoreMatchers.notNullValue;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.junit.jupiter.api.Assertions.assertNull;
//
//@QuarkusTest
//public class UsuarioResourceTest {
//    @Inject
//    UsuarioService usuarioService;
//
//    @Test
//    public void testGetAll() {
//        given()
//                .when().get("/usuarios")
//                .then()
//                .statusCode(200);
//    }
//
//    @Test
//    public void testInsert() {
//        List<TelefoneDTO> telefones = new ArrayList<>();
//        telefones.add(new TelefoneDTO("63", "999466871"));
//
//        List<EnderecoDTO> enderecos = new ArrayList<>();
//        enderecos.add(new EnderecoDTO("77023110", "Centro", "30", "Casa", 1L));
//
//        UsuarioDTO usuario = new UsuarioDTO(
//                "12345678910", "Maria", "Maria@gmail.com", "maria.alves", telefones, enderecos);
//
//        UsuarioResponseDTO usuarioCreate = usuarioService.create(usuario);
//        given()
//                .contentType(ContentType.JSON)
//                .body(usuarioCreate)
//                .when().post("/usuarios")
//                .then()
//                .statusCode(201)
//                .body("id", notNullValue(), "cpf", is("12345678910"), "nome",
//                        is("Maria"),"sexo.label", is("feminino"), "cpf", is("06175473185"),"telefone", is(telefones), "endereco", is(enderecos));
//    }
//
//    @Test
//    public void testUpdate() {
//        List<TelefoneDTO> telefones = new ArrayList<>();
//        telefones.add(new TelefoneDTO("63", "999566877"));
//        List<EnderecoDTO> enderecos = new ArrayList<>();
//        enderecos.add(new EnderecoDTO("77023210", "Centro", "30", "Casa", 1L));
//
//        UsuarioDTO usuario = new UsuarioDTO(
//                "45675276285", "Laura", "laura@gmail.com", "laura.santos", telefones, enderecos);
//        Long id = usuarioService.create(usuario).id();
//
//        // Criando outro usuario para atualizacao
//        UsuarioDTO usuarioupdate = new UsuarioDTO(
//                "89678974132", "Leonardo", "leonardo@gmail.com", "leonardo.martins", telefones, enderecos);
//
//        UsuarioResponseDTO usuarioatualizado = usuarioService.update(id, usuarioupdate);
//
//        given()
//                .contentType(ContentType.JSON)
//                .body(usuarioatualizado)
//                .when().put("/usuarios/" + id)
//                .then()
//                .statusCode(204);
//
//        // Verificando se os dados foram atualizados no banco de dados
//        UsuarioResponseDTO usuarioResponse = usuarioService.findById(id);
//        assertThat(usuarioResponse.nome(), is("Caneta"));
//        assertThat(usuarioResponse.email(), is("azul"));
//        assertThat(usuarioResponse.cpf(), is("30"));
//        assertThat(usuarioResponse.telefone(), is(telefones));
//        assertThat(usuarioResponse.endereco(), is(enderecos));
//
//    }
//
//    @Test
//    public void testDelete() {
//        List<TelefoneDTO> telefones = new ArrayList<>();
//        telefones.add(new TelefoneDTO("63", "999466871"));
//
//        List<EnderecoDTO> enderecos = new ArrayList<>();
//        enderecos.add(new EnderecoDTO("77023110", "Centro", "30", "Casa", 1L));
//
//        // Adicionando um usuario no banco de dados
//        UsuarioDTO usuario = new UsuarioDTO(
//                "45614712356", "Maria", "maria@gmail.com", "maria.santos", telefones, enderecos);
//
//        Long id = usuarioService.create(usuario).id();
//        given()
//                .when().delete("/usuarios/" + id)
//                .then()
//                .statusCode(204);
//
//        // verificando se o usuario foi excluido
//        UsuarioResponseDTO usuarioResponse = null;
//        try {
//            usuarioResponse = usuarioService.findById(id);
//        } catch (Exception e) {
//            assert true;
//        } finally {
//            assertNull(usuarioResponse);
//        }
//    }
//
//}
//
