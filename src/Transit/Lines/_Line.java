package Transit.Lines;

import Transit.CityComponents.RoadElements.Crossing;
import Transit.Time.Hour;
import Transit.Vehicles.VehicleFamily;

import java.util.LinkedList;
import java.util.List;

public class _Line {
    VehicleFamily vehicleFamily;
    List<_TransitStop> stops;
    List<List<Crossing>> itinerary;
    List<Double> travelTimes;
    String name;


    public _Line(VehicleFamily vehicleFamily, List<_TransitStop> stops, List<List<Crossing>> itinerary, List<Double> travelTimes, String name) {
        this.vehicleFamily = vehicleFamily;
        this.stops = stops;
        this.itinerary = itinerary;
        this.travelTimes = travelTimes;
        this.name = name;
    }

    public VehicleFamily getVehicleFamily() {
        return vehicleFamily;
    }

    public List<_TransitStop> getStops() {
        return stops;
    }

    public List<List<Crossing>> getItinerary() {
        return itinerary;
    }

    public List<Double> getTravelTimes() {
        return travelTimes;
    }

    public List<_PassageTime> getPassageTimes(Hour departure) {
        List<_PassageTime> passageTimes = new LinkedList<>();
        Hour time = departure.incremented(0);
        for(int i=0; i < getStops().size(); i++)
        {
            _TransitStop stop = getStops().get(i);
            _PassageTime passageTime = new _PassageTime(time, stop,this,i);
            passageTimes.add(passageTime);
            double travelTime = i<getStops().size()-1 ? getTravelTimes().get(i) : 0.0;
            time = time.incremented(travelTime);
        }
        return passageTimes;
    }

    public String getName() {
        return name;
    }
}
