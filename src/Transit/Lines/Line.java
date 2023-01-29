package Transit.Lines;

import Transit.RoadElements.Crossing;
import Transit.Vehicles.VehicleFamily;

import java.util.List;

public class Line {
    VehicleFamily vehicleFamily;
    List<TransitStop> stops;
    List<List<Crossing>> itinerary;
    List<Double> travelTime;
    String name;


    public Line(VehicleFamily vehicleFamily, List<TransitStop> stops, List<List<Crossing>> itinerary, List<Double> travelTime, String name) {
        this.vehicleFamily = vehicleFamily;
        this.stops = stops;
        this.itinerary = itinerary;
        this.travelTime = travelTime;
        this.name = name;
    }

    public VehicleFamily getVehicleFamily() {
        return vehicleFamily;
    }

    public List<TransitStop> getStops() {
        return stops;
    }

    public List<List<Crossing>> getItinerary() {
        return itinerary;
    }

    public List<Double> getTravelTime() {
        return travelTime;
    }
}
