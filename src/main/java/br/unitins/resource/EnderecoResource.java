package br.unitins.resource;

import br.unitins.application.Result;
import br.unitins.dto.EnderecoDTO;
import br.unitins.dto.EnderecoResponseDTO;
import br.unitins.service.EnderecoService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.List;

@Path("/enderecos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EnderecoResource {
    @Inject
    EnderecoService enderecoService;

    @GET
    public List<EnderecoResponseDTO> getAll() {
        return enderecoService.getAll();
    }

    @POST
    @Transactional
    public Response Insert(EnderecoDTO enderecoDTO) {
        try {
            EnderecoResponseDTO endereco = enderecoService.create(enderecoDTO);
            return Response.status(Status.CREATED).entity(endereco).build();
        } catch(ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }
    @PUT
    @Path("/{id}")
    @Transactional
    public Response Update (EnderecoDTO enderecoDTO) {
        try {
            EnderecoResponseDTO endereco = enderecoService.create(enderecoDTO);
            return Response.status(Status.NO_CONTENT).entity(endereco).build();
        } catch(ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @GET
    @Path("Count")
    public long count() {
        return enderecoService.count();
    }

    @GET
    @Path("/search/{id}")
    public EnderecoResponseDTO searchId(@PathParam("id") Long id) {
        return enderecoService.findById(id);
    }

    @GET
    @Path("/search/{bairro}")
    public List<EnderecoResponseDTO> search(@PathParam("bairro") String bairro) {

        return enderecoService.findByNome(bairro);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteEndereco(@PathParam("id") Long id) {
        enderecoService.delete(id);
        return Response.status(Status.NO_CONTENT).build();

    }

}




