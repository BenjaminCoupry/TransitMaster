package Transit.Lines;

import Transit.CityComponents.RoadElements.Crossing;
import Transit.CityComponents.RoadElements.PassageType;
import Transit.Time.Hour;
import Transit.Vehicles.VehicleFamily;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Line {
    VehicleFamily vehicleFamily;
    List<TransitStop> stops;
    List<List<Crossing>> itinerary;
    List<Double> travelTimes;
    String name;


    public Line(VehicleFamily vehicleFamily, List<TransitStop> stops, List<List<Crossing>> itinerary, List<Double> travelTimes, String name) {
        this.vehicleFamily = vehicleFamily;
        this.stops = stops;
        this.itinerary = itinerary;
        this.travelTimes = travelTimes;
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

    public List<Double> getTravelTimes() {
        return travelTimes;
    }

    public List<PassageTime> getPassageTimes(Hour departure) {
        List<PassageTime> passageTimes = new LinkedList<>();
        Hour time = departure.incremented(0);
        for(int i=0; i < getStops().size(); i++)
        {
            TransitStop stop = getStops().get(i);
            PassageTime passageTime = new PassageTime(time, stop);
            passageTimes.add(passageTime);
            double travelTime = getTravelTimes().get(i);
            time = time.incremented(travelTime);
        }
        return passageTimes;
    }

    public String getName() {
        return name;
    }
}
