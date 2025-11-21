import core.usecases.CreateUserUseCase;
import infrastructure.db.SQLiteDatabaseConnection;
import infrastructure.db.SqliteDatabase;
import io.javalin.Javalin;
import io.restassured.RestAssured;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateUserApiTest {

    private static Javalin app;

    @BeforeAll
    static void setup() {
        // Setup database
        SQLiteDatabaseConnection conn =
                new SQLiteDatabaseConnection("jdbc:sqlite:acceptance_test.db");
        SqliteDatabase db = new SqliteDatabase(conn);
        db.initialize();

        // Setup usecases
        CreateUserUseCase createUser = new CreateUserUseCase(db);

        // Start Javalin on test port
        app = Javalin.create().start(7500);

        // Register controller manually
        new api.controllers.UserController(app, createUser);

        // RestAssured config
        RestAssured.baseURI = "http://localhost:7500";
    }

    @AfterAll
    static void teardown() {
        app.stop();
    }

    @Test
    public void postUsersShouldCreateUser() {
        given()
                .contentType("application/json")
                .body("""
                {
                  "firstName": "Test",
                  "lastName": "Tester",
                  "email": "tester@test.no",
                  "userType": "ENDUSER"
                }
            """)
                .when()
                .post("/users")
                .then()
                .statusCode(200)
                .body("firstName", equalTo("Test"))
                .body("email", equalTo("tester@test.no"));
    }
}
