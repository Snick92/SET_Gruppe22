package core.dto;

import java.time.LocalDateTime;

//Class for creating a trip
public class RegisterTripRequest {

    private int userId;
    private int routeId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    //Empty constructor
    public RegisterTripRequest(){

    }

    //Getter & setter
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
}
