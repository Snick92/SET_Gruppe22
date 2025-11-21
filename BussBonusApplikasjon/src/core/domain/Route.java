package core.domain;

import java.time.LocalDateTime;

public class Route {

    private int routeId;
    private String startBusStop;
    private String endBusStop;
    private LocalDateTime time;
    private boolean isDelayed;

    //Constructor
    public Route (int routeId, String startBusStop, String endBusStop, LocalDateTime time, boolean isDelayed){
        this.routeId = routeId;
        this.startBusStop = startBusStop;
        this.endBusStop = endBusStop;
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

    public String getEndBusStop() {
        return endBusStop;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public boolean isDelayed() {
        return isDelayed;
    }



}
