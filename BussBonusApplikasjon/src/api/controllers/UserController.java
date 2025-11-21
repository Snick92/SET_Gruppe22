package api.controllers;

import core.dto.UserDTO;
import core.usecases.CreateUserUseCase;
import io.javalin.Javalin;

public class UserController {

    private final CreateUserUseCase createUserUseCase;

    // Constructor
    public UserController(Javalin app, CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;

        // POST /users
        app.post("/users", ctx -> {
            // Read JSON → DTO
            UserDTO input = ctx.bodyAsClass(UserDTO.class);

            // Execute usecase
            UserDTO result = createUserUseCase.execute(input);

            // Return created user as JSON
            ctx.json(result);
        });
    }
}