package br.unitins.resource;

import br.unitins.application.Result;
import br.unitins.dto.ProdutoDTO;
import br.unitins.dto.ProdutoResponseDTO;
import br.unitins.dto.UsuarioResponseDTO;
import br.unitins.form.ImageForm;
import br.unitins.service.FileService;
import br.unitins.service.ProdutoService;

import br.unitins.service.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import java.io.IOException;
import java.util.List;

@Path("/produtos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProdutoResource {

    @Inject
    ProdutoService produtoService;

    @Inject
    UsuarioService usuarioService;

    @Inject
    JsonWebToken jwt;

    @Inject
    FileService fileService;

    @GET
    public List<ProdutoResponseDTO> getAll() {

        return produtoService.getAll();
    }

    @GET
    @RolesAllowed({"Admin","User"})
    public Response getUsuario() {

        String login = jwt.getSubject();
        UsuarioResponseDTO usuario = usuarioService.findByLogin(login);

        return Response.ok(usuario).build();
    }

    @PATCH
    @Path("/novaimagem")
    @RolesAllowed({"Admin","User"})
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response salvarImagem(@MultipartForm ImageForm form){
        String nomeImagem = "";

        try {
            nomeImagem = fileService.salvarImagemUsuario(form.getImagem(), form.getNomeImagem());
        } catch (IOException e) {
            Result result = new Result(e.getMessage());
            return Response.status(Status.CONFLICT).entity(result).build();
        }

        // obtendo o login a partir do token
        String login = jwt.getSubject();
        UsuarioResponseDTO usuario = usuarioService.findByLogin(login);

        usuario = usuarioService.update(usuario.id(), nomeImagem);

        return Response.ok(usuario).build();

    }

    @GET
    @Path("/download/{nomeImagem}")
    @RolesAllowed({"Admin","User"})
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam("nomeImagem") String nomeImagem) {
        Response.ResponseBuilder response = Response.ok(fileService.download(nomeImagem));
        response.header("Content-Disposition", "attachment;filename="+nomeImagem);
        return response.build();
    }


    @POST
    public Response Insert(ProdutoDTO produtoDTO) {
        try {
            ProdutoResponseDTO produto = produtoService.create(produtoDTO);
            return Response.status(Status.CREATED).entity(produto).build();
        } catch(ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response Update (ProdutoDTO produtoDTO) {
        try {
            ProdutoResponseDTO produto = produtoService.create(produtoDTO);
            return Response.status(Status.NO_CONTENT).entity(produto).build();
        } catch(ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @GET
    @Path("/{id}")
    public ProdutoResponseDTO searchId(@PathParam("id") Long id) {

        return produtoService.findById(id);
    }

    @GET
    @Path("/search/{nome}")
    public List<ProdutoResponseDTO> search(@PathParam("nome") String nome) {

        return produtoService.findByNome(nome);
    }
    
    @GET
    @Path("Count")
    public long count() {
        return produtoService.count();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteProduto(@PathParam("id") Long id) {
        produtoService.delete(id);
        return Response.status(Status.NO_CONTENT).build();

    }
} 
