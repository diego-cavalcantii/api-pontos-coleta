package org.example.resources;

import org.example.model.User;
import org.example.repositories.UserRepository;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/user")
public class UserResource {
    private final UserRepository userRepository = new UserRepository();

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(User user) {
        if (userRepository.findByUserName(user.getUsername()).isPresent()) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        if (userRepository.save(user)) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        User authenticatedUser = userRepository.authenticate(username, password).orElse(null);
        if (authenticatedUser != null) {
            String token = JwtUtil.generateToken(username);
            return Response.ok().entity(token).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}
