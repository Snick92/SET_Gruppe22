package core.port;


/**
 * Simple outbound port for password hashing and verification.
 * Implement this in the infrastructure layer (e.g. BCrypt adapter).
 */

public interface PasswordEncoder {

    /**
     * Hash the raw password and return the hashed string.
     * @param rawPassword plain text password
     * @return hashed password string
     */
    String hash(String rawPassword);

    /**
     * Check if a raw password matches the stored hash.
     * @param rawPassword plain text password to check
     * @param hashed stored password hash
     * @return true if matches, false otherwise
     */
    boolean matches(String rawPassword, String hashed);
}
