package core.usecases;

import core.domain.Route;
import core.dto.RouteDTO;
import core.port.RouteRepository;

import java.util.ArrayList;
import java.util.List;

public class SearchRoutesUseCase {

    private final RouteRepository routeRepository;

    // Constructor: inject repository
    public SearchRoutesUseCase(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    /**
     * Search for routes and return a list of DTOs.
     */
    public List<RouteDTO> execute(String startStop, String endStop) {

        // Get all matching routes from repository
        List<Route> routes = routeRepository.searchByStops(startStop, endStop);

        // Create output list manually (no streams)
        List<RouteDTO> dtoList = new ArrayList<>();

        // Convert each Route domain object to a RouteDTO
        for (Route route : routes) {

            RouteDTO dto = new RouteDTO();
            dto.setRouteId(route.getRouteId());
            dto.setStartBusStop(route.getStartBusStop());
            dto.setEndBusStop(route.getEndBusStop());
            dto.setTime(route.getTime());
            dto.setDelayed(route.isDelayed());

            // Add DTO to the result list
            dtoList.add(dto);
        }

        // Return the final list of DTOs
        return dtoList;
    }
}
