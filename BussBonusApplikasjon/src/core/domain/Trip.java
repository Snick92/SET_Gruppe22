package core.domain;

import java.time.LocalDateTime;

public class Trip {

    private final int tripId;
    private final int userId;
    private final int routeId;
    private final LocalDateTime departureTime;
    private final LocalDateTime arrivalTime;
    private final boolean isBonusTrip;
    private final LocalDateTime createdAt;


    //Constructor

    public Trip(int tripId, int userId, int routeId, LocalDateTime departureTime, LocalDateTime arrivalTime, boolean isBonusTrip, LocalDateTime createdAt) {
        this.tripId = tripId;
        this.userId = userId;
        this.routeId = routeId;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.isBonusTrip = isBonusTrip;
        this.createdAt = createdAt;
    }
}
