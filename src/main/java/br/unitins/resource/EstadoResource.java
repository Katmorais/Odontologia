package br.unitins.resource;

import br.unitins.application.Result;
import br.unitins.dto.EstadoDTO;
import br.unitins.dto.EstadoResponseDTO;
import br.unitins.model.Estado;
import br.unitins.repository.EstadoRepository;
import br.unitins.service.EstadoService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.List;

@Path("/estados")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EstadoResource {

    @Inject
    EstadoRepository repository;

    @Inject
    EstadoService estadoService;


    @GET
    public List<EstadoResponseDTO> getAll() {
        return estadoService.getAll();
    }

    @POST
    @Transactional
    public Response Insert(EstadoDTO estadoDTO) {
        try {
            EstadoResponseDTO estado = estadoService.create(estadoDTO);
            return Response.status(Status.CREATED).entity(estado).build();
        } catch(ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response Update (EstadoDTO estadoDTO) {
        try {
            EstadoResponseDTO estado = estadoService.create(estadoDTO);
            return Response.status(Status.NO_CONTENT).entity(estado).build();
        } catch(ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @GET
    @Path("/count")
    public long count(){
        return repository.count();
    }

    @GET
    @Path("/{id}")
    public EstadoResponseDTO searchId(@PathParam("id") Long id) {
        var result = estadoService.findById(id);
        return result;
    }

    @GET
    @Path("/search/{nome}")
    public List<Estado> search(@PathParam("nome") String nome){
        return repository.findByNome(nome);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteEstado(@PathParam("id") Long id) {
        estadoService.delete(id);
        return Response.status(Status.NO_CONTENT).build();

    }
}



