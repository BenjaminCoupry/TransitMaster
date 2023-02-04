package Transit.Lines;

import Engine.Pathfinding.Itinerary.Itinerary.Itinerary;
import Engine.Pathfinding.Itinerary.Itinerary.WeightedItinerary;
import Engine.Pathfinding.NetworkComponents.Arcs.CompositeWeightedOrientedArc;
import Transit.CityComponents.RoadElements.Crossing;
import Transit.CityComponents.RoadElements.TrafficWay;
import Transit.Time.Hour;
import Transit.Vehicles.VehicleFamily;

import java.util.List;

public class Line extends CompositeWeightedOrientedArc<TransitStop,> {

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
