package api.controllers;

import core.usecases.SearchRoutesUseCase;
import io.javalin.Javalin;

public class RouteController {

    private final SearchRoutesUseCase searchRoutesUseCase;

    // Constructor
    public RouteController(Javalin app, SearchRoutesUseCase searchRoutesUseCase) {
        this.searchRoutesUseCase = searchRoutesUseCase;

        // GET /routes/search?from=X&to=Y
        app.get("/routes/search", ctx -> {
            String from = ctx.queryParam("from");
            String to = ctx.queryParam("to");

            if (from == null || to == null) {
                ctx.status(400).result("Missing 'from' or 'to' query parameters.");
                return;
            }

            // Execute usecase and return JSON
            ctx.json(searchRoutesUseCase.execute(from, to));
        });
    }
}
