import core.domain.User;
import core.port.UserRepository;
import infrastructure.db.SQLiteDatabaseConnection;
import infrastructure.db.SqliteDatabase;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserRepositoryIntegrationTest {

    private static UserRepository repo;

    @BeforeAll
    static void setup() {
        SQLiteDatabaseConnection conn =
                new SQLiteDatabaseConnection("jdbc:sqlite:testdb_users.db");
        SqliteDatabase db = new SqliteDatabase(conn);
        db.initialize();
        repo = db;
    }

    @Test
    @Order(1)
    public void shouldInsertUserIntoDatabase() {
        var user = TestUserFactory.create("Test", "User", "test@example.com");
        User saved = repo.saveUser(user);

        assertTrue(saved.getUserId() > 0);
    }

    @Test @Order(2)
    public void shouldFindUserByEmail() {
        Optional<User> result = repo.findUserByEmail("test@example.com");

        assertTrue(result.isPresent());
        assertEquals("test@example.com", result.get().getEmail());
    }

    // simple helper
    static class TestUserFactory {
        static User create(String f, String l, String email, String userType) {
            return new core.domain.EndUser(0, f, l, email, userType, java.time.LocalDateTime.now());
        }
    }
}
