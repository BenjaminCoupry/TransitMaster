package Transit.CityComponents.City;

import Engine.Pathfinding.NetworkComponents.Arcs.CompositeWeightedOrientedArc;
import Engine.Pathfinding.NetworkComponents.Arcs.PhysicalArc;
import Engine.Pathfinding.NetworkComponents.Cost.DistanceElements;
import Engine.Pathfinding.NetworkComponents.Cost.Evaluators.CostEvaluator;
import Engine.Pathfinding.NetworkComponents.Cost.Evaluators.ElementaryCostEvaluator;
import Engine.Pathfinding.NetworkComponents.Networks.WeightedOrientedNetwork;
import Engine.Pathfinding.Itinerary.ItineraryMode;
import Transit.Lines._Line;
import Transit.Lines._TransitStop;
import Transit.CityComponents.RoadElements.Crossing;
import Transit.Vehicles.VehicleFamily;
import Engine.Pathfinding.TravellingSalesman.MonteCarloTravellingSalesman;

import java.util.*;
import java.util.stream.Collectors;

public class _TransitCity extends City{

    Map<String, _Line> transitLines;
    Map<Crossing, _TransitStop> transitStops;

    public _TransitCity() {
        transitLines = new Hashtable<>();
        transitStops = new Hashtable<>();
    }

    public Optional<_Line> getLine(String name)
    {
        if(transitLines.containsKey(name)) {
            return Optional.of(transitLines.get(name));
        }else{
            return Optional.empty();
        }
    }

    public _TransitStop addTransitStop(Crossing crossing, String name)
    {
        _TransitStop transitStop = new _TransitStop(crossing,name);
        transitStops.put(crossing,transitStop);
        return transitStop;
    }

    public _TransitStop getTransitStop(Crossing crossing)
    {
        return transitStops.get(crossing);
    }




    public _Line addLine(_Line line)
    {
        transitLines.put(line.getName(),line);
        return line;
    }

    public void removeLine(_Line line)
    {
        transitLines.remove(line.getName());
    }

    public void removeLine(String lineName)
    {
        Optional<_Line> line = getLine(lineName);
        if(line.isPresent())
        {
            removeLine(line.get());
        }

    }

    public _Line addLine(VehicleFamily vehicleFamily, List<_TransitStop> stops, String name)
    {
        CostEvaluator costEvaluator = new ElementaryCostEvaluator(DistanceElements.TIME);
        List<Crossing> stopLocations = stops.stream().map(s->s.getPosition()).collect(Collectors.toList());
        Set<Crossing> stopSet = stopLocations.stream().collect(Collectors.toSet());
        stopLocations.add(stopLocations.get(0));
        WeightedOrientedNetwork<PhysicalArc<Crossing>, Crossing> subnet = generateSpecificNetwork(vehicleFamily);
        WeightedOrientedNetwork<CompositeWeightedOrientedArc<Crossing, PhysicalArc<Crossing>>, Crossing> factorized
                = subnet.getCompleteNetworkFactorization(stopSet, costEvaluator);
        List<CompositeWeightedOrientedArc<Crossing, PhysicalArc<Crossing>>> compositeWeightedOrientedArcs
                = factorized.placesChainToArcs(stopLocations).get();
        List<Double> travelTimes = compositeWeightedOrientedArcs.stream().map(ca -> costEvaluator.evaluate(ca.getCost())).collect(Collectors.toList());
        List<List<Crossing>> itinerary = compositeWeightedOrientedArcs.stream()
                .map(ca -> subnet.arcsChainToPlaces(ca.getChildren()).get()).collect(Collectors.toList());
        _Line line = new _Line(vehicleFamily, stops, itinerary, travelTimes,name);
        addLine(line);
        return line;
    }

    public _Line addLineAuto(VehicleFamily vehicleFamily, List<_TransitStop> stops, String name, ItineraryMode itineraryMode)
    {
        CostEvaluator costEvaluator = new ElementaryCostEvaluator(DistanceElements.TIME);
        WeightedOrientedNetwork<PhysicalArc<Crossing>, Crossing> subnet = generateSpecificNetwork(vehicleFamily);
        Set<Crossing> stopSet = stops.stream().map(s->s.getPosition()).collect(Collectors.toSet());
        MonteCarloTravellingSalesman<PhysicalArc<Crossing>,Crossing> salesman 
                = new MonteCarloTravellingSalesman<>(subnet,stopSet,costEvaluator,100);
        SalesmanResult<PhysicalArc<Crossing>, Crossing> salesmanResult = salesman.proposeOptimalTravel(itineraryMode);
        List<_TransitStop> bestPath = salesmanResult.getBestPath().stream().map(p -> getTransitStop(p)).collect(Collectors.toList());
        List<CompositeWeightedOrientedArc<Crossing, PhysicalArc<Crossing>>> compositeWeightedOrientedArcs = salesmanResult.getCompositeOrientedArcs();
        List<Double> travelTimes = compositeWeightedOrientedArcs.stream().map(ca -> costEvaluator.evaluate(ca.getCost())).collect(Collectors.toList());
        List<List<Crossing>> itinerary = compositeWeightedOrientedArcs.stream()
                .map(ca -> subnet.arcsChainToPlaces(ca.getChildren()).get()).collect(Collectors.toList());
        _Line line = new _Line(vehicleFamily, bestPath, itinerary, travelTimes,name);
        addLine(line);
        return line;
    }
}
