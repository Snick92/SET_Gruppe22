package api.controllers;

import core.dto.TripDTO;
import core.usecases.RegisterTripUseCase;
import io.javalin.Javalin;

public class TripController {

    private final RegisterTripUseCase registerTripUseCase;

    // Constructor
    public TripController(Javalin app, RegisterTripUseCase registerTripUseCase) {
        this.registerTripUseCase = registerTripUseCase;

        // POST /trips
        app.post("/trips", ctx -> {
            // Read input JSON → DTO
            TripDTO input = ctx.bodyAsClass(TripDTO.class);

            // Execute usecase
            TripDTO result = registerTripUseCase.execute(input);

            // Return JSON
            ctx.json(result);
        });
    }
}
