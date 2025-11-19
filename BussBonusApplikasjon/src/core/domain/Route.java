package core.domain;

import java.time.LocalDateTime;

public class Route {

    private int routeId;
    private String startFromBusStop;
    private String stopOnBusStop;
    private LocalDateTime time;
    private boolean isDelayed;

    //Constructor
    public Route (int routeId, String startFromBusStop, String stopOnBusStop, LocalDateTime time, boolean isDelayed){
        this.routeId = routeId;
        this.startFromBusStop = startFromBusStop;
        this.stopOnBusStop = stopOnBusStop;
        this.time = time;
        this.isDelayed = isDelayed;
    }

    //Getter
    public int getRouteId() {
        return routeId;
    }

    public String getStartFromBusStop() {
        return startFromBusStop;
    }

    public String getStopOnBusStop() {
        return stopOnBusStop;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public boolean isDelayed() {
        return isDelayed;
    }



}
