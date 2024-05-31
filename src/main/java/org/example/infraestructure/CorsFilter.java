package org.example.infraestructure;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;


@Provider
public class CorsFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext){
        // Allow origin permite que qualquer origem acesse a API ex: http://google.com.br e sua API está em http://localhost:8080
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
        // Allow headers permite que qualquer header seja enviado na requisição, como por exemplo: Authorization, Content-Type, etc.
        // origin é o header que permite que a origem acesse a API
        // content-type é o header que permite que o conteúdo seja enviado no corpo da requisição, ex: application/json
        // accept é o header que permite que o cliente aceite um determinado tipo de conteúdo, ex: application/json
        // authorization é o header que permite que o cliente envie um token de autenticação
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        // Allow credentials permite que as credenciais sejam enviadas na requisição, como por exemplo: cookies, tokens, etc.
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
        // Allow methods permite que qualquer método HTTP seja utilizado na requisição, como por exemplo: GET, POST, PUT, DELETE, etc.
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD, PATCH");
    }
}