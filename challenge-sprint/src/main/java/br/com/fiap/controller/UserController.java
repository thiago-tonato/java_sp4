package br.com.fiap.controller;

import br.com.fiap.dominio.User;
import br.com.fiap.infra.dao.ConnectionFactory;
import br.com.fiap.service.UserService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.Connection;

@Path("/users")
public class UserController {

    private final ConnectionFactory connectionFactory;

    public UserController() {
        this.connectionFactory = new ConnectionFactory();
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response registerUser(User user) {
        try (Connection connection = connectionFactory.getConnection()) {
            UserService userService = new UserService();
            String result = userService.registerUser(user);
            return Response.status(Response.Status.CREATED)
                    .entity(result)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao cadastrar usuário: " + e.getMessage())
                    .build();
        }
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response loginUser(User user) {
        try (Connection connection = connectionFactory.getConnection()) {
            UserService userService = new UserService();
            boolean isLoggedIn = userService.loginUser(user.getUsername(), user.getPassword());

            if (isLoggedIn) {
                return Response.ok("Login bem-sucedido!").build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Credenciais inválidas.")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao fazer login: " + e.getMessage())
                    .build();
        }
    }
}