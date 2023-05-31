package br.unitins.resource;

import br.unitins.application.Result;
import br.unitins.dto.TelefoneDTO;
import br.unitins.dto.TelefoneResponseDTO;
import br.unitins.service.TelefoneService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.List;

@Path("/telefones")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TelefoneResource {
    @Inject
    TelefoneService telefoneService;

    @GET
    public List<TelefoneResponseDTO> getAll() {
        return telefoneService.getAll();
    }

    @POST
    @Transactional
    public Response Insert(TelefoneDTO telefoneDTO) {
        try {
            TelefoneResponseDTO telefone = telefoneService.create(telefoneDTO);
            return Response.status(Status.CREATED).entity(telefone).build();
        } catch(ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response Update (TelefoneDTO telefoneDTO) {
        try {
            TelefoneResponseDTO telefone = telefoneService.create(telefoneDTO);
            return Response.status(Status.NO_CONTENT).entity(telefone).build();
        } catch(ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @GET
    @Path("/search/{numero}")
    public List<TelefoneResponseDTO> search(@PathParam("numero") String numero) {
        return telefoneService.findByNome(numero);
    }
    @GET
    @Path("/buscar/{id}")
    public TelefoneResponseDTO searchId(@PathParam("id") Long id) {
        var result = telefoneService.findById(id);
        return result;
    }

    @GET
    @Path("Count")
    public long count() {
        return telefoneService.count();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteTelefone(@PathParam("id") Long id) {
        telefoneService.delete(id);
        return Response.status(Status.NO_CONTENT).build();

    }
}



