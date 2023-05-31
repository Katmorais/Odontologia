package br.unitins.resource;

import br.unitins.application.Result;
import br.unitins.dto.CompraResponseDTO;
import br.unitins.dto.CompraDTO;
import br.unitins.service.CompraService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/compras")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CompraResource {
        @Inject
        CompraService compraService;

        @GET
        public List<CompraResponseDTO> getAll() {
            return compraService.getAll();
        }

        @POST
        @Transactional
        public Response Insert(CompraDTO compraDTO) {
            try {
                CompraResponseDTO compra = compraService.create(compraDTO);
                return Response.status(Response.Status.CREATED).entity(compra).build();
            } catch(ConstraintViolationException e) {
                Result result = new Result(e.getConstraintViolations());
                return Response.status(Response.Status.NOT_FOUND).entity(result).build();
            }
        }

        @PUT
        @Path("/{id}")
        @Transactional
        public Response Update (CompraDTO compraDTO) {
            try {
                CompraResponseDTO compra = compraService.create(compraDTO);
                return Response.status(Response.Status.NO_CONTENT).entity(compra).build();
            } catch(ConstraintViolationException e) {
                Result result = new Result(e.getConstraintViolations());
                return Response.status(Response.Status.NOT_FOUND).entity(result).build();
            }
        }

        @GET
        @Path("/search/{nome}")
        public List<CompraResponseDTO> search(@PathParam("nome") String nome) {
            return compraService.findByNome(nome);
        }
        @GET
        @Path("/buscar/{id}")
        public CompraResponseDTO searchId(@PathParam("id") Long id) {
            var result = compraService.findById(id);
            return result;
        }

        @GET
        @Path("Count")
        public long count() {
            return compraService.count();
        }

        @DELETE
        @Path("/{id}")
        @Transactional
        public Response deleteCompra(@PathParam("id") Long id) {
            compraService.delete(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        }
 }


