package core.dto;

import java.time.LocalDateTime;

//The return from route search
public class RouteResponse {

    private int routeId;
    private String startBusStop;
    private String endBusStop;
    private LocalDateTime time;
    private boolean delayed;

    //Empty constructor
    public RouteResponse() {

    }

    //Getter & setter
    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public String getStartBusStop() {
        return startBusStop;
    }

    public void setStartBusStop(String startBusStop) {
        this.startBusStop = startBusStop;
    }

    public String getEndBusStop() {
        return endBusStop;
    }

    public void setEndBusStop(String endBusStop) {
        this.endBusStop = endBusStop;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public boolean isDelayed() {
        return delayed;
    }

    public void setDelayed(boolean delayed) {
        this.delayed = delayed;
    }
}
