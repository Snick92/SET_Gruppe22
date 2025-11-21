package core.dto;

import java.time.LocalDateTime;

public class TripDTO {

    private int tripId;
    private int userId;
    private int routeId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private boolean isBonusTrip;
    private LocalDateTime createdAt;

    public TripDTO() {}

    // Getter & Setter
    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public boolean isBonusTrip() {
        return isBonusTrip;
    }

    public void setBonusTrip(boolean bonusTrip) {
        this.isBonusTrip = bonusTrip;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
