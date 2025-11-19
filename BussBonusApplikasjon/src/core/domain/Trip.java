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

    //Getter
    public int getTripId() {
        return tripId;
    }

    public int getUserId() {
        return userId;
    }

    public int getRouteId() {
        return routeId;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public boolean isBonusTrip() {
        return isBonusTrip;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
