package br.unitins.resource;

import java.util.List;

import jakarta.inject.Inject;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import br.unitins.application.Result;
import br.unitins.dto.MunicipioDTO;
import br.unitins.dto.MunicipioResponseDTO;
import br.unitins.service.MunicipioService;

@Path("/municipios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MunicipioResource {
    @Inject
    MunicipioService municipioService;

    @GET
    public List<MunicipioResponseDTO> getAll() {
        return municipioService.getAll();
    }

    @POST
    @Transactional
    public Response Insert(MunicipioDTO municipioDTO) {
        try {
            MunicipioResponseDTO municipio = municipioService.create(municipioDTO);
            return Response.status(Status.CREATED).entity(municipio).build();
        } catch(ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response Update (@PathParam("id") Long id, MunicipioDTO municipioDTO) {
        try {
            MunicipioResponseDTO municipio = municipioService.update(id, municipioDTO);
            return Response.status(Status.NO_CONTENT).entity(municipio).build();
        } catch(ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @GET
    @Path("/count")
    public long count(){

        return municipioService.count();

    }
    @GET
    @Path("/buscar/{id}")
    public MunicipioResponseDTO searchId(@PathParam("id") Long id) {
        var result = municipioService.findById(id);
        return result;
    }

    @GET
    @Path("/search/{nome}")
    public List<MunicipioResponseDTO> search(@PathParam("nome") String nome){
        return municipioService.findByNome(nome);

    }
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        municipioService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }
}

