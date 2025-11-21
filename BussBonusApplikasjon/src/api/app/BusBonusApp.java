package api.app;

import api.controllers.RouteController;
import api.controllers.TripController;
import api.controllers.UserController;
import core.usecases.CreateUserUseCase;
import core.usecases.RegisterTripUseCase;
import core.usecases.SearchRoutesUseCase;
import infrastructure.db.SQLiteDatabaseConnection;
import infrastructure.db.SqliteDatabase;
import io.javalin.Javalin;

public class BusBonusApp {

    public static void main(String[] args) {

        // SQLite connection
        SQLiteDatabaseConnection connection =
                new SQLiteDatabaseConnection("jdbc:sqlite:bussbonus.db");

        // Single DB implementing all repositories
        SqliteDatabase database = new SqliteDatabase(connection);

        // Create tables if missing
        database.initialize();

        // Build usecases
        CreateUserUseCase createUser = new CreateUserUseCase(database);
        SearchRoutesUseCase searchRoutes = new SearchRoutesUseCase(database);
        RegisterTripUseCase registerTrip = new RegisterTripUseCase(database);

        // Start Javalin
        Javalin app = Javalin.create(config -> {
            config.defaultContentType = "application/json";
        }).start(7000);

        // Register controllers
        new UserController(app, createUser);
        new RouteController(app, searchRoutes);
        new TripController(app, registerTrip);

        System.out.println("BussBonus API running at http://localhost:7000");
    }
}
