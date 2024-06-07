package org.example.resources;


import org.example.Main;
import org.example.model.PontoColeta;
import org.example.repositories.PontoColetaRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("ponto_coleta")
public class PontoColetaResource {

    PontoColetaRepository pontoColetaRepository = new PontoColetaRepository();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPontoColeta(){
        List<PontoColeta> pontoColetaList = pontoColetaRepository.findAll();
        if (pontoColetaList.isEmpty()){
            Main.LOGGER.info("404 - Ponto de Coleta não encontrado");
            return Response.status(404).entity("Ponto de Coleta não encontrado").build();
        }
        Main.LOGGER.info("[GET] - 200 - OK");
        return Response.status(200).entity(pontoColetaList).build();
    }

    @GET
    @Path("{idColeta}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getColetaById(@PathParam("idColeta") int idColeta){
        Optional<PontoColeta> coleta = pontoColetaRepository.findPontoColetaByName(idColeta);
        if(!coleta.isPresent()){ // Verifica se o Optional NÃO está presente
            Main.LOGGER.info("404 - Pontos de coleta não encontrado");
            return Response.status(404).entity("Pontos de coleta não encontrado").build();
        }
        Main.LOGGER.info("[GET] - 200 OK");
        return Response.status(200).entity(coleta).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPontoColeta(PontoColeta coleta){
        if (coleta == null || coleta.getName() == null || coleta.getType() == null || coleta.getCep() == null || coleta.getLogradouro() == null || coleta.getBairro() == null || coleta.getCidade() == null){
            Main.LOGGER.info("400 - Dados do Ponto de Coleta não fornecidos");
            return Response.status(400).entity("Dados do Ponto de Coleta não fornecidos").build();
        }
        pontoColetaRepository.createPontoColeta(coleta);
        Main.LOGGER.info("[POST] - 201 - Ponto de Coleta Criado");
        return Response.status(201).entity(coleta).build();
    }



    @PUT
    @Path("{idColeta}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePontoColeta(@PathParam("idColeta") int idColeta, PontoColeta coleta){
        if(pontoColetaRepository.findPontoColetaByName(idColeta).isPresent()){
            coleta.setidColeta(idColeta);
            pontoColetaRepository.updatePontoColeta(coleta);
            Main.LOGGER.info("[PUT] - 200 - OK");
            return
                    Response.status(200).entity(coleta).build();
        }
        Main.LOGGER.info("404 - Pontos de coleta não encontrado");
        return Response.status(404).entity("Pontos de coleta não encontrado").build();
    }

    @DELETE
    @Path("{idColeta}")
    public Response deletePontoColeta(@PathParam("idColeta") int idColeta){
        pontoColetaRepository.deletePontoColeta(idColeta);
        Main.LOGGER.info("[DELETE] - 204 - Ponto de Coleta Deletado");
        return Response.noContent().build();
    }

    // Endpoint para obter pontos de coleta pendentes
    @GET
    @Path("pending")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPendingPontoColeta(){
        List<PontoColeta> pendingPontoColetaList = pontoColetaRepository.findPendingPontoColeta();
        if (pendingPontoColetaList.isEmpty()){
            Main.LOGGER.info("404 - Nenhum ponto de coleta pendente encontrado");
            return Response.status(404).entity("Nenhum ponto de coleta pendente encontrado").build();
        }
        Main.LOGGER.info("[GET] - 200 - OK");
        return Response.status(200).entity(pendingPontoColetaList).build();
    }

    // Endpoint para aceitar um ponto de coleta pendente
    @PUT
    @Path("{idColeta}/accept")
    public Response acceptPontoColeta(@PathParam("idColeta") int idColeta){
        int updatedRows = pontoColetaRepository.acceptPontoColeta(idColeta);
        if (updatedRows > 0) {
            Main.LOGGER.info("Ponto de coleta pendente aceito: " + idColeta);
            return Response.ok().build();
        } else {
            Main.LOGGER.info("Ponto de coleta pendente não encontrado ou já aceito: " + idColeta);
            return Response.status(Response.Status.NOT_FOUND).entity("Ponto de coleta pendente não encontrado ou já aceito").build();
        }
    }

    // Endpoint para rejeitar um ponto de coleta pendente
    @PUT
    @Path("{idColeta}/reject")
    public Response rejectPontoColeta(@PathParam("idColeta") int idColeta){
        pontoColetaRepository.rejectPontoColeta(idColeta);
        Main.LOGGER.info("Ponto de coleta pendente rejeitado: " + idColeta);
        return Response.ok().build();
    }
}
