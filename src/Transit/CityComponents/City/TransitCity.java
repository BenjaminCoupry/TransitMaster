package Transit.CityComponents.City;

import Engine.Pathfinding.NetworkComponents.Arcs.CompositeOrientedArc;
import Engine.Pathfinding.NetworkComponents.Arcs.PhysicalArc;
import Engine.Pathfinding.NetworkComponents.Cost.DistanceElements;
import Engine.Pathfinding.NetworkComponents.Cost.Evaluators.CostEvaluator;
import Engine.Pathfinding.NetworkComponents.Cost.Evaluators.ElementaryCostEvaluator;
import Engine.Pathfinding.NetworkComponents.Networks.WeightedOrientedNetwork;
import Engine.Pathfinding.TravellingSalesman.ItineraryMode;
import Transit.Lines.Line;
import Transit.Lines.TransitStop;
import Transit.CityComponents.RoadElements.Crossing;
import Transit.Vehicles.VehicleFamily;
import Engine.Pathfinding.TravellingSalesman.MonteCarloTravellingSalesman;
import Engine.Pathfinding.TravellingSalesman.SalesmanResult;

import java.util.*;
import java.util.stream.Collectors;

public class TransitCity extends City{

    Map<String,Line> transitLines;
    Map<Crossing,TransitStop> transitStops;

    public TransitCity() {
        transitLines = new Hashtable<>();
        transitStops = new Hashtable<>();
    }

    public Optional<Line> getLine(String name)
    {
        if(transitLines.containsKey(name)) {
            return Optional.of(transitLines.get(name));
        }else{
            return Optional.empty();
        }
    }

    public TransitStop addTransitStop(Crossing crossing)
    {
        TransitStop transitStop = new TransitStop(crossing);
        transitStops.put(crossing,transitStop);
        return transitStop;
    }

    public TransitStop getTransitStop(Crossing crossing)
    {
        return transitStops.get(crossing);
    }

    public WeightedOrientedNetwork<PhysicalArc<Crossing>, Crossing> generateSpecificNetwork(VehicleFamily vehicleFamily)
    {
        WeightedOrientedNetwork<PhysicalArc<Crossing>, Crossing> network = new WeightedOrientedNetwork<>();
        List<PhysicalArc<Crossing>> specificArcs = getArcs().stream().flatMap(a -> a.generateArcs(vehicleFamily).stream()).collect(Collectors.toList());
        network.addArcs(specificArcs);
        Set<Crossing> crossings = specificArcs.stream().flatMap(a -> a.getPlaces().stream()).collect(Collectors.toSet());
        network.addPlaces(crossings);
        return network;
    }


    public Line addLine(Line line)
    {
        transitLines.put(line.getName(),line);
        for(TransitStop stop : line.getStops())
        {
            stop.addLine(line);
        }
        return line;
    }

    public void removeLine(Line line)
    {
        transitLines.remove(line.getName());
        for(TransitStop stop : line.getStops())
        {
            stop.removeLine(line);
        }
    }

    public void removeLine(String lineName)
    {
        Optional<Line> line = getLine(lineName);
        if(line.isPresent())
        {
            removeLine(line.get());
        }

    }

    public Line addLine(VehicleFamily vehicleFamily, List<TransitStop> stops, String name)
    {
        CostEvaluator costEvaluator = new ElementaryCostEvaluator(DistanceElements.TIME);
        List<Crossing> stopLocations = stops.stream().map(s->s.getPosition()).collect(Collectors.toList());
        Set<Crossing> stopSet = stopLocations.stream().collect(Collectors.toSet());
        stopLocations.add(stopLocations.get(0));
        WeightedOrientedNetwork<PhysicalArc<Crossing>, Crossing> subnet = generateSpecificNetwork(vehicleFamily);
        WeightedOrientedNetwork<CompositeOrientedArc<Crossing, PhysicalArc<Crossing>>, Crossing> factorized
                = subnet.getCompleteNetworkFactorization(stopSet, costEvaluator);
        List<CompositeOrientedArc<Crossing, PhysicalArc<Crossing>>> compositeOrientedArcs
                = factorized.placesChainToArcs(stopLocations).get();
        List<Double> travelTimes = compositeOrientedArcs.stream().map(ca -> costEvaluator.evaluate(ca.getCost())).collect(Collectors.toList());
        List<List<Crossing>> itinerary = compositeOrientedArcs.stream()
                .map(ca -> subnet.arcsChainToPlaces(ca.getChildren()).get()).collect(Collectors.toList());
        Line line = new Line(vehicleFamily, stops, itinerary, travelTimes,name);
        addLine(line);
        return line;
    }

    public Line addLineAuto(VehicleFamily vehicleFamily, List<TransitStop> stops, String name, ItineraryMode itineraryMode)
    {
        CostEvaluator costEvaluator = new ElementaryCostEvaluator(DistanceElements.TIME);
        WeightedOrientedNetwork<PhysicalArc<Crossing>, Crossing> subnet = generateSpecificNetwork(vehicleFamily);
        Set<Crossing> stopSet = stops.stream().map(s->s.getPosition()).collect(Collectors.toSet());
        MonteCarloTravellingSalesman<PhysicalArc<Crossing>,Crossing> salesman 
                = new MonteCarloTravellingSalesman<>(subnet,stopSet,costEvaluator,100);
        SalesmanResult<PhysicalArc<Crossing>, Crossing> salesmanResult = salesman.proposeOptimalTravel(itineraryMode);
        List<TransitStop> bestPath = salesmanResult.getBestPath().stream().map(p -> getTransitStop(p)).collect(Collectors.toList());
        List<CompositeOrientedArc<Crossing, PhysicalArc<Crossing>>> compositeOrientedArcs = salesmanResult.getCompositeOrientedArcs();
        List<Double> travelTimes = compositeOrientedArcs.stream().map(ca -> costEvaluator.evaluate(ca.getCost())).collect(Collectors.toList());
        List<List<Crossing>> itinerary = compositeOrientedArcs.stream()
                .map(ca -> subnet.arcsChainToPlaces(ca.getChildren()).get()).collect(Collectors.toList());
        Line line = new Line(vehicleFamily, bestPath, itinerary, travelTimes,name);
        addLine(line);
        return line;
    }
}
