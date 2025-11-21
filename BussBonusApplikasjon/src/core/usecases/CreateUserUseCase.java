package core.usecases;

import core.domain.Admin;
import core.domain.EndUser;
import core.domain.User;
import core.dto.UserDTO;
import core.port.UserRepository;

import java.time.LocalDateTime;

public class CreateUserUseCase {

    private final UserRepository userRepository;

    // Constructor: inject the repository
    public CreateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Create a new user and return a UserDTO.
     */
    public UserDTO execute(UserDTO dto) throws Exception {

        // Simple validation: email must not be empty
        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty.");
        }

        // Check if a user with this email already exists
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalStateException("User already exists.");
        }

        // Create a domain object based on userType
        User user;
        if ("ADMIN".equalsIgnoreCase(dto.getUserType())) {
            user = new Admin(
                    0,
                    dto.getFirstName(),
                    dto.getLastName(),
                    dto.getEmail(),
                    dto.getUserType(),
                    LocalDateTime.now()
            );
        } else {
            user = new EndUser(
                    0,
                    dto.getFirstName(),
                    dto.getLastName(),
                    dto.getEmail(),
                    dto.getUserType(),
                    LocalDateTime.now()
            );
        }

        // Save user using the repository
        User saved = userRepository.save(user);

        // Map domain -> DTO
        UserDTO result = new UserDTO();
        result.setUserId(saved.getUserId());
        result.setFirstName(saved.getFirstName());
        result.setLastName(saved.getLastName());
        result.setEmail(saved.getEmail());
        result.setUserType(saved.getUserType());
        result.setCreatedAt(saved.getCreatedAt());

        return result;
    }
}
