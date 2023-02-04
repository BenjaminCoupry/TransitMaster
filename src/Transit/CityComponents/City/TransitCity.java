package Transit.CityComponents.City;

import Engine.Pathfinding.NetworkComponents.Arcs.CompositeWeightedOrientedArc;
import Engine.Pathfinding.NetworkComponents.Cost.DistanceElements;
import Engine.Pathfinding.NetworkComponents.Cost.Evaluators.CostEvaluator;
import Engine.Pathfinding.NetworkComponents.Cost.Evaluators.ElementaryCostEvaluator;
import Engine.Pathfinding.NetworkComponents.Networks.CompositeWeightedNetwork;
import Transit.CityComponents.RoadElements.Crossing;
import Transit.CityComponents.RoadElements.Road;
import Transit.CityComponents.RoadElements.TrafficWay;
import Transit.Lines.TransitStop;
import Transit.Vehicles.VehicleFamily;

import java.util.*;
import java.util.stream.Collectors;

public class TransitCity extends City{
    Collection<TransitStop> transitStops;
    Map<VehicleFamily,CompositeWeightedNetwork<TrafficWay,Crossing>> expanded;
    CostEvaluator costEvaluator;

    public CompositeWeightedNetwork<TrafficWay,Crossing> getExpanded(VehicleFamily vehicleFamily) {
        return expanded.get(vehicleFamily);
    }
    public TransitCity() {
        transitStops = new LinkedList<>();
        expanded = new HashMap<>();
        costEvaluator = new ElementaryCostEvaluator(DistanceElements.TIME);
    }

    public void refreshExpansion()
    {
        Set<VehicleFamily> authorizedVehicles = getAuthorizedVehicles();
        expanded = authorizedVehicles.stream().
                collect(Collectors.toMap(vf -> vf,
                        vf -> getSpecificNetwork(vf).getCompleteNetworkFactorization(getCrossingsWithTransitStops(vf), getCostEvaluator())));
    }
    @Override
    public void removeArc(Road road)
    {
        super.removeArc(road);
        refreshExpansion();
    }
    @Override
    public Road addArc(Road road)
    {
        Road added = super.addArc(road);
        refreshExpansion();
        return added;
    }

    public List<TransitStop> getTransitStops(Crossing crossing)
    {
        List<TransitStop> transitStops = getTransitStops().stream()
                .filter(ts -> ts.getPointed().equals(crossing)).collect(Collectors.toList());
        return transitStops;
    }

    public Optional<TransitStop> getTransitStop(Crossing crossing, VehicleFamily family)
    {
        List<TransitStop> crossingStops = getTransitStops(crossing);
        Optional<TransitStop> stop = crossingStops.stream().filter(ts -> ts.getVehicleFamily().equals(family)).findFirst();
        return stop;
    }

    public Set<Crossing> getCrossingsWithTransitStops()
    {
        Set<Crossing> crossings = getTransitStops().stream().map(ts -> ts.getPointed()).collect(Collectors.toSet());
        return crossings;
    }

    public Set<Crossing> getCrossingsWithTransitStops(VehicleFamily vehicleFamily)
    {
        Set<Crossing> crossings = getTransitStops().stream().filter(ts->ts.getVehicleFamily().equals(vehicleFamily))
                .map(ts -> ts.getPointed()).collect(Collectors.toSet());
        return crossings;
    }



    public void addTransitStop(TransitStop transitStop)
    {
        Optional<TransitStop> present = getTransitStop(transitStop.getPointed(),transitStop.getVehicleFamily());
        if(present.isEmpty()) {
            transitStops.add(transitStop);
            refreshExpansion();
        }
    }


    public Collection<TransitStop> getTransitStops() {
        return transitStops;
    }

    public Optional<PhysicalItinerary> getItinerary(TransitItinerary transitItinerary)
    {
        VehicleFamily vehicleFamily = transitItinerary.getVehicleFamily();
        CompositeWeightedNetwork<TrafficWay, Crossing> expandedNet = getExpanded(vehicleFamily);
        List<Crossing> crossings = transitItinerary.getCrossings();
        Optional<CompositeWeightedOrientedArc<Crossing, CompositeWeightedOrientedArc<Crossing, TrafficWay>>> potentialItinerary;
        potentialItinerary = expandedNet.placesChainToCompositeWeightedOrientedArc(crossings);
        if(potentialItinerary.isEmpty())
        {
            return Optional.empty();
        }else {
            return Optional.of(new PhysicalItinerary(potentialItinerary.get().getChildren()));
        }
    }
    
    public CostEvaluator getCostEvaluator() {
        return costEvaluator;
    }
}
