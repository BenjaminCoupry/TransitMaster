package Transit.Lines;

import Engine.Pathfinding.Itinerary.Itinerary;
import Engine.Pathfinding.Itinerary.WeightedItinerary;
import Transit.CityComponents.City.TransitCity;
import Transit.CityComponents.RoadElements.Crossing;
import Transit.CityComponents.RoadElements.TrafficWay;
import Transit.Time.Hour;
import Transit.Vehicles.VehicleFamily;

import java.util.List;

public class Line {

    VehicleFamily vehicleFamily;
    String name;
    List<Hour> departureTimes;
    WeightedItinerary<Crossing,TrafficWay> itinerary;

    public String getName() {
        return name;
    }

    public List<Hour> getDepartureTimes() {
        return departureTimes;
    }

    public VehicleFamily getVehicleFamily() {
        return vehicleFamily;
    }

    public WeightedItinerary<Crossing, TrafficWay> getItinerary() {
        return itinerary;
    }

    public List<Crossing> getCrossingStops()
    {
        List<Crossing> crossings = getItinerary().getPlacesSequence().get();
        return crossings;
    }

}
