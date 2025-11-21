package core.usecases;

import core.domain.Trip;
import core.dto.TripDTO;
import core.port.TripRepository;

import java.time.LocalDateTime;

public class RegisterTripUseCase {

    private final TripRepository tripRepository;

    // Constructor: inject repository
    public RegisterTripUseCase(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    /**
     * Register a new trip and return a DTO.
     */
    public TripDTO execute(TripDTO dto) throws Exception {

        // Basic validation
        if (dto.getUserId() <= 0) {
            throw new IllegalArgumentException("userId is required.");
        }
        if (dto.getRouteId() <= 0) {
            throw new IllegalArgumentException("routeId is required.");
        }

        Trip trip = new Trip(
                0,
                dto.getUserId(),
                dto.getRouteId(),
                dto.getDepartureTime(),
                dto.getArrivalTime(),
                dto.isBonusTrip(),
                LocalDateTime.now()
        );


        Trip saved = tripRepository.saveTrip(trip);


        TripDTO out = new TripDTO();
        out.setTripId(saved.getTripId());
        out.setUserId(saved.getUserId());
        out.setRouteId(saved.getRouteId());
        out.setDepartureTime(saved.getDepartureTime());
        out.setArrivalTime(saved.getArrivalTime());
        out.setBonusTrip(saved.isBonusTrip());
        out.setCreatedAt(saved.getCreatedAt());

        return out;
    }
}
