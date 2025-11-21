package core.port;

import core.domain.Trip;
import java.util.List;
import java.util.Optional;

/**
 * Outbound
 * Repository port for Trip domain objects.
 * Usecases rely on this to save and fetch trips.
 */

public interface TripRepository {

    // Find one trip by ID
    Optional<Trip> findById(int tripId);

    // Save a new trip entry
    Trip save(Trip trip);

    // Get all trips for one user
    List<Trip> findByUserId(int userId);

    // Get all trips in database
    List<Trip> findAll();


}
