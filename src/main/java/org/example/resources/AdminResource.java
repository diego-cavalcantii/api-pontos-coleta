package org.example.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.model.User;

@Path("/admin")
public class AdminResource {

    private static final String ADMIN_USERNAME = System.getenv("ADMIN_USERNAME");
    private static final String ADMIN_PASSWORD = System.getenv("ADMIN_PASSWORD");


    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginAdmin(User user) {
        if (ADMIN_USERNAME.equals(user.getUsername()) && ADMIN_PASSWORD.equals(user.getPassword())) {
            String token = JwtUtil.generateToken(user.getUsername());
            return Response.ok(token).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
