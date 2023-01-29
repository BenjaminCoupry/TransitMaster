package Transit.City;

import Pathfinding.Arcs.CompositeOrientedArc;
import Pathfinding.Arcs.PhysicalArc;
import Pathfinding.Cost.DistanceElements;
import Pathfinding.Cost.Evaluators.CostEvaluator;
import Pathfinding.Cost.Evaluators.ElementaryCostEvaluator;
import Pathfinding.Networks.WeightedOrientedNetwork;
import Transit.Lines.Line;
import Transit.Lines.TransitStop;
import Transit.RoadElements.Crossing;
import Transit.Vehicles.VehicleFamily;
import TravellingSalesman.MonteCarloTravellingSalesman;
import TravellingSalesman.SalesmanResult;

import java.util.*;
import java.util.stream.Collectors;

public class TransitCity extends City{

    Map<String,Line> transitLines;
    Map<Crossing,TransitStop> transitStops;

    public TransitCity() {
        transitLines = new Hashtable<>();
        transitStops = new Hashtable<>();
    }

    public Line getLine(String name)
    {
        return transitLines.get(name);
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
        transitLines.put(name,line);
        return line;
    }

    public Line addLineAuto(VehicleFamily vehicleFamily, List<TransitStop> stops, String name)
    {
        CostEvaluator costEvaluator = new ElementaryCostEvaluator(DistanceElements.TIME);
        WeightedOrientedNetwork<PhysicalArc<Crossing>, Crossing> subnet = generateSpecificNetwork(vehicleFamily);
        Set<Crossing> stopSet = stops.stream().map(s->s.getPosition()).collect(Collectors.toSet());
        MonteCarloTravellingSalesman<PhysicalArc<Crossing>,Crossing> salesman 
                = new MonteCarloTravellingSalesman<>(subnet,stopSet,costEvaluator,1000);
        SalesmanResult<PhysicalArc<Crossing>, Crossing> salesmanResult = salesman.proposeOptimalTravel();
        List<TransitStop> bestPath = salesmanResult.getBestPath().stream().map(p -> getTransitStop(p)).collect(Collectors.toList());
        List<CompositeOrientedArc<Crossing, PhysicalArc<Crossing>>> compositeOrientedArcs = salesmanResult.getCompositeOrientedArcs().get();
        List<Double> travelTimes = compositeOrientedArcs.stream().map(ca -> costEvaluator.evaluate(ca.getCost())).collect(Collectors.toList());
        List<List<Crossing>> itinerary = compositeOrientedArcs.stream()
                .map(ca -> subnet.arcsChainToPlaces(ca.getChildren()).get()).collect(Collectors.toList());
        Line line = new Line(vehicleFamily, bestPath, itinerary, travelTimes,name);
        transitLines.put(name,line);
        return line;
    }
}
