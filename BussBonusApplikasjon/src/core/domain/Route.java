package core.domain;

import java.time.LocalDateTime;

public class Route {

    private int routeId;
    private String startBusStop;
    private String stopBusStop;
    private LocalDateTime time;
    private boolean isDelayed;

    //Constructor
    public Route (int routeId, String startBusStop, String stopBusStop, LocalDateTime time, boolean isDelayed){
        this.routeId = routeId;
        this.startBusStop = startBusStop;
        this.stopBusStop = stopBusStop;
        this.time = time;
        this.isDelayed = isDelayed;
    }

    //Getter
    public int getRouteId() {
        return routeId;
    }

    public String getStartBusStop() {
        return startBusStop;
    }

    public String getStopBusStop() {
        return stopBusStop;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public boolean isDelayed() {
        return isDelayed;
    }



}
