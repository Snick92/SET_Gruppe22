package core.port;

import core.domain.User;

import java.util.List;
import java.util.Optional;

/**
 * Outbound
 * This is the repository port for User.
 * It defines what the application needs from the data layer.
 * Implemented later in the infrastructure layer (ex: SQLite).
 */
public interface UserRepository {

    // Find user by user ID (might not exist, so we return Optional)
    Optional<User> findUserById(int id);

    // Find user by email (used for login)
    Optional<User> findUserByEmail(String email);

    // Save a new or existing user to storage
    User saveUser(User user);

    // Get all users (used mostly by admin features)
    List<User> findAllUsers();

    // Delete a user by ID
    void deleteUserById(int id);

}
