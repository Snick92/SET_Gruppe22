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
    Optional<Trip> findTripById(int tripId);

    // Save a new trip entry
    Trip saveTrip(Trip trip);

    // Get all trips for one user
    List<Trip> findTripByUserId(int userId);

    // Get all trips in database
    List<Trip> findAllTrips();

}
