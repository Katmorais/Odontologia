package br.unitins.resource;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import br.unitins.application.Result;
import br.unitins.dto.*;
import br.unitins.dto.EquipamentoOdontologicoResponseDTO;
import br.unitins.service.EquipamentoOdontologicoService;

@Path("/equipamentosOdontologicos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EquipamentoOdontologicoResource {

    @Inject
    EquipamentoOdontologicoService equipamentoOdontologicoService;

    @POST
    @Transactional
    public Response Insert(EquipamentoOdontologicoDTO equipamentoOdontologicoDTO) {
        try {
            EquipamentoOdontologicoResponseDTO equipamentoOdontologico = equipamentoOdontologicoService.create(equipamentoOdontologicoDTO);
            return Response.status(Status.CREATED).entity(equipamentoOdontologico).build();
        } catch(ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }
    @PUT
    @Path("/{id}")
    @Transactional
    public Response Update (EquipamentoOdontologicoDTO equipamentoOdontologicoDTO) {
        try {
            EquipamentoOdontologicoResponseDTO equipamentoOdontologico = equipamentoOdontologicoService.create(equipamentoOdontologicoDTO);
            return Response.status(Status.NO_CONTENT).entity(equipamentoOdontologico).build();
        } catch(ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @GET
    public List<EquipamentoOdontologicoResponseDTO> getAll() {

        return equipamentoOdontologicoService.getAll();
    }

    @GET
    @Path("/search/{id}")
    public EquipamentoOdontologicoResponseDTO searchId(@PathParam("id") Long id) {
        return equipamentoOdontologicoService.findById(id);
    }

    @GET
    @Path("/search/{nome}")
    public List<EquipamentoOdontologicoResponseDTO> search(@PathParam("nome") String nome) {
        return equipamentoOdontologicoService.findByNome(nome);
    }

    @GET
    @Path("Count")
    public long count() {
        return equipamentoOdontologicoService.count();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteEquipamentoOdontologico(@PathParam("id") Long id) {
        equipamentoOdontologicoService.delete(id);
        return Response.status(Status.NO_CONTENT).build();

    }

}
