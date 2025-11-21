package core.port;

import core.domain.Route;
import java.util.List;
import java.util.Optional;

/**
 * Outbound
 * Repository port for Route domain objects.
 * Defines the operations the application needs for routes.
 */

public interface RouteRepository {

    // Find a route by its ID
    Optional<Route> findById(int routeId);

    // Search for routes between two bus stops
    List<Route> searchByStops(String startBusStop, String endBusStop);

    // Save or update a route
    Route save(Route route);

    // Return all routes
    List<Route> findAll();


}
