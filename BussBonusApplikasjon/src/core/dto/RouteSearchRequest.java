package core.dto;

//this class is for searching routes
public class RouteSearchRequest {

    private String startBusStop;
    private String endBusStop;

    //Empty constructor
    public RouteSearchRequest(){

    }


    //Getter & setter
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
}
