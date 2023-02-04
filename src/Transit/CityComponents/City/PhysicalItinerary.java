package Transit.CityComponents.City;

import Engine.Pathfinding.NetworkComponents.Arcs.CompositeWeightedOrientedArc;
import Transit.CityComponents.RoadElements.Crossing;
import Transit.CityComponents.RoadElements.TrafficWay;

import java.util.List;

public class PhysicalItinerary extends CompositeWeightedOrientedArc<Crossing,CompositeWeightedOrientedArc<Crossing,TrafficWay>> {
    public PhysicalItinerary(List<CompositeWeightedOrientedArc<Crossing,TrafficWay>> children) {
        super(children);
    }
}
