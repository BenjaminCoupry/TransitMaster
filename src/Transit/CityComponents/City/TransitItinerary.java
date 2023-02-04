package Transit.CityComponents.City;

import Engine.Pathfinding.NetworkComponents.Arcs.CompositeOrientedArc;
import Engine.Pathfinding.NetworkComponents.Arcs.OrientedArc;
import Transit.CityComponents.RoadElements.Crossing;
import Transit.Lines.TransitStop;
import Transit.Vehicles.VehicleFamily;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TransitItinerary extends CompositeOrientedArc<TransitStop, OrientedArc<TransitStop>> {
    public TransitItinerary(List<OrientedArc<TransitStop>> children) {
        super(children);
    }

    public List<Crossing> getCrossings()
    {
        Optional<List<TransitStop>> placesSequence = getPlacesSequence();
        List<Crossing> crossings = placesSequence.orElseGet(()->new LinkedList<>()).stream().map(ts -> ts.getPointed()).collect(Collectors.toList());
        return crossings;

    }
    public VehicleFamily getVehicleFamily()
    {
        return getFinish().getVehicleFamily();
    }
}
