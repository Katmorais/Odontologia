package br.unitins.resource;

import br.unitins.application.Result;
import br.unitins.dto.PessoaFisicaDTO;
import br.unitins.dto.PessoaFisicaResponseDTO;
import br.unitins.service.PessoaFisicaService;

import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/pessoasfisicas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PessoaFisicaResource {

    @Inject
    PessoaFisicaService pessoaFisicaService;

    @GET
    public List<PessoaFisicaResponseDTO> getAll() {
        return pessoaFisicaService.getAll();
    }

    @GET
    @Path("/{id}")
    public PessoaFisicaResponseDTO findById(@PathParam("id") Long id) {
        return pessoaFisicaService.findById(id);
    }

    @POST
    public Response insert(PessoaFisicaDTO dto) {
        try {
            PessoaFisicaResponseDTO pessoafisica = pessoaFisicaService.create(dto);
            return Response.status(Response.Status.CREATED).entity(pessoafisica).build();
        } catch(ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Response.Status.NOT_FOUND).entity(result).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, PessoaFisicaDTO dto) {
        try {
            pessoaFisicaService.update(id, dto);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch(ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Response.Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        pessoaFisicaService.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }


    @GET
    @Path("/count")
    public long count(){
        return pessoaFisicaService.count();
    }

    @GET
    @Path("/search/{nome}")
    public List<PessoaFisicaResponseDTO> search(@PathParam("nome") String nome){
        return pessoaFisicaService.findByNome(nome);

    }
}
