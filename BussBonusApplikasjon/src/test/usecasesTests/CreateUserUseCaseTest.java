import core.domain.EndUser;
import core.domain.User;
import core.dto.UserDTO;
import core.port.UserRepository;
import core.usecases.CreateUserUseCase;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateUserUseCaseTest {

    class FakeUserRepository implements UserRepository {

        private final List<User> users = new ArrayList<>();

        @Override
        public Optional<User> findUserById(int id) {
            // Regular for-loop instead of streams
            for (User u : users) {
                if (u.getUserId() == id) {
                    return Optional.of(u);
                }
            }
            return Optional.empty();
        }

        @Override
        public Optional<User> findUserByEmail(String email) {
            // Regular for-loop instead of streams
            for (User u : users) {
                if (u.getEmail().equals(email)) {
                    return Optional.of(u);
                }
            }
            return Optional.empty();
        }

        @Override
        public User saveUser(User user) {
            // Assign ID manually
            int newId = users.size() + 1;

            User stored = new EndUser(
                    newId,
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getUserType(),
                    LocalDateTime.now()
            );

            users.add(stored);
            return stored;
        }

        @Override
        public List<User> findAllUsers() {
            return users;
        }

        @Override
        public void deleteUserById(int id) {
            // Regular for-loop remove
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getUserId() == id) {
                    users.remove(i);
                    break;
                }
            }
        }
    }

    @Test
    void shouldCreateNewUser() throws Exception {
        // Arrange
        FakeUserRepository repo = new FakeUserRepository();
        CreateUserUseCase useCase = new CreateUserUseCase(repo);

        UserDTO input = new UserDTO();
        input.setFirstName("Ola");
        input.setLastName("Nordmann");
        input.setEmail("ola@test.no");
        input.setUserType("ENDUSER");

        // Act
        UserDTO result = useCase.execute(input);

        // Assert
        assertNotNull(result);
        assertEquals("Ola", result.getFirstName());
        assertEquals("ENDUSER", result.getUserType());
        assertEquals(1, result.getUserId());
    }
}
