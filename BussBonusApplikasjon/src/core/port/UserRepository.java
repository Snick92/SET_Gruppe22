package core.port;

import core.domain.User;

import java.util.List;
import java.util.Optional;

/**
 * This is the repository port for User.
 * It defines what the application needs from the data layer.
 * Implemented later in the infrastructure layer (ex: SQLite).
 */
public interface UserRepository {

    // Find user by user ID (might not exist, so we return Optional)
    Optional<User> findById(int id);

    // Find user by email (used for login)
    Optional<User> findByEmail(String email);

    // Save a new or existing user to storage
    User save(User user);

    // Get all users (used mostly by admin features)
    List<User> findAll();

    // Delete a user by ID
    void deleteById(int id);


}
