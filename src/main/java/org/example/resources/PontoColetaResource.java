package org.example.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.Main;
import org.example.model.PontoColeta;
import org.example.repositories.PontoColetaRepository;

import java.util.List;
import java.util.Optional;

@Path("ponto_coleta")
public class PontoColetaResource {

    PontoColetaRepository pontoColetaRepository = new PontoColetaRepository();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAstro(){
        List<PontoColeta> astroList = pontoColetaRepository.findAll();
        if (astroList.isEmpty()){
            Main.LOGGER.info("404 - Ponto de Coleta não encontrado");
            return Response.status(404).entity("Ponto de Coleta não encontrado").build();
        }
        Main.LOGGER.info("[GET] - 200 - OK");
        return Response.status(200).entity(astroList).build();
    }

    @GET
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAstroById(@PathParam("name") String name){
        Optional<PontoColeta> astro = pontoColetaRepository.findPontoColetaByName(name);
        if(astro.isEmpty()){
            Main.LOGGER.info("404 - Pontos de coleta não encontrado");
            return Response.status(404).entity("Pontos de coleta não encontrado").build();
        }
        Main.LOGGER.info("[GET] - 200 OK");
        return Response.status(200).entity(astro).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAstro(PontoColeta astro){
        if (astro == null){
            Main.LOGGER.info("400 - Dados do Ponto de Coleta não fornecidos");
            return Response.status(400).entity("Dados do Ponto de Coleta não fornecidos").build();
        }
        pontoColetaRepository.createPontoColeta(astro);
        Main.LOGGER.info("[POST] - 201 - Astro Criado");
        return Response.status(201).entity(astro).build();
    }

    @PUT
    @Path("{name}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAstro(@PathParam("name") String name, PontoColeta astro){
        if(pontoColetaRepository.findPontoColetaByName(name).isPresent()){
            astro.setName(name);
            pontoColetaRepository.updatePontoColeta(astro);
            Main.LOGGER.info("[PUT] - 200 - OK");
            return
                    Response.status(200).entity(astro).build();
        }
        Main.LOGGER.info("404 - Pontos de coleta não encontrado");
        return Response.status(404).entity("Pontos de coleta não encontrado").build();
    }

    @DELETE
    @Path("{name}")
    public Response deleteAstro(@PathParam("name") String name){
        pontoColetaRepository.deletePontoColeta(name);
        Main.LOGGER.info("[DELETE] - 204 - Ponto de Coleta Deletado");
        return Response.noContent().build();
    }

    // Endpoint para obter pontos de coleta pendentes
    @GET
    @Path("pending")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPendingAstro(){
        List<PontoColeta> pendingAstroList = pontoColetaRepository.findPendingPontoColeta();
        if (pendingAstroList.isEmpty()){
            Main.LOGGER.info("404 - Nenhum ponto de coleta pendente encontrado");
            return Response.status(404).entity("Nenhum ponto de coleta pendente encontrado").build();
        }
        Main.LOGGER.info("[GET] - 200 - OK");
        return Response.status(200).entity(pendingAstroList).build();
    }

    // Endpoint para aceitar um ponto de coleta pendente
    @PUT
    @Path("{name}/accept")
    public Response acceptAstro(@PathParam("name") String name){
        int updatedRows = pontoColetaRepository.acceptPontoColeta(name);
        if (updatedRows > 0) {
            Main.LOGGER.info("Ponto de coleta pendente aceito: " + name);
            return Response.ok().build();
        } else {
            Main.LOGGER.info("Ponto de coleta pendente não encontrado ou já aceito: " + name);
            return Response.status(Response.Status.NOT_FOUND).entity("Ponto de coleta pendente não encontrado ou já aceito").build();
        }
    }

    // Endpoint para rejeitar um ponto de coleta pendente
    @PUT
    @Path("{name}/reject")
    public Response rejectAstro(@PathParam("name") String name){
        pontoColetaRepository.rejectPontoColeta(name);
        Main.LOGGER.info("Ponto de coleta pendente rejeitado: " + name);
        return Response.ok().build();
    }
}
