import Pathfinding.Arcs.CompositeOrientedArc;
import Pathfinding.Arcs.PhysicalArc;
import Pathfinding.Cost.DistanceElements;
import Pathfinding.Cost.Evaluators.CostEvaluator;
import Pathfinding.Cost.Evaluators.ElementaryCostEvaluator;
import Pathfinding.DistanceInfo;
import Pathfinding.Networks.WeightedOrientedNetwork;
import Pathfinding.Places.PhysicalPlace;
import Transit.City.TransitCity;
import Transit.Lines.Line;
import Transit.Lines.TransitStop;
import Transit.RoadElements.Crossing;
import Transit.RoadElements.PassageType;
import Transit.Vehicles.VehicleFamily;
import TravellingSalesman.MonteCarloTravellingSalesman;

import java.util.Arrays;
import java.util.List;

public class Main2 {
    public static void main(String[] args) {
        TransitCity network = new TransitCity();

        Crossing p0 = network.addPlace(new Crossing(1,1));
        Crossing p1 = network.addPlace(new Crossing(1,2));
        Crossing p2 = network.addPlace(new Crossing(2,5));
        Crossing p3 = network.addPlace(new Crossing(4,1));
        Crossing p4 = network.addPlace(new Crossing(2,3));
        Crossing p5 = network.addPlace(new Crossing(12,3));
        Crossing p6 = network.addPlace(new Crossing(1,6));
        Crossing p7 = network.addPlace(new Crossing(2,6));
        Crossing p8 = network.addPlace(new Crossing(3,5));

        network.connect(p0,p1, VehicleFamily.BUS, PassageType.BIDIRECTIONAL,2);
        network.connect(p0,p3, VehicleFamily.BUS, PassageType.BLOCKED_A,3.5);
        network.connect(p1,p4, VehicleFamily.BUS, PassageType.BIDIRECTIONAL,2);
        network.connect(p3,p4, VehicleFamily.BUS, PassageType.BLOCKED_A,2);
        network.connect(p4,p2, VehicleFamily.BUS, PassageType.BIDIRECTIONAL,0.5);
        network.connect(p4,p5, VehicleFamily.BUS, PassageType.BIDIRECTIONAL,2);
        network.connect(p2,p6, VehicleFamily.BUS, PassageType.BIDIRECTIONAL,2);
        network.connect(p2,p7, VehicleFamily.BUS, PassageType.BLOCKED_A,2);
        network.connect(p7,p8, VehicleFamily.BUS, PassageType.BLOCKED_A,2);
        network.connect(p8,p2, VehicleFamily.BUS, PassageType.BLOCKED_A,2);

        TransitStop t0 = network.addTransitStop(p0);
        TransitStop t3 = network.addTransitStop(p3);
        TransitStop t5 = network.addTransitStop(p5);
        TransitStop t2 = network.addTransitStop(p2);
        TransitStop t7 = network.addTransitStop(p7);
        TransitStop t6 = network.addTransitStop(p6);

        Line l1 = network.addLine(VehicleFamily.BUS,Arrays.asList(t0,t5,t3,t6,t7,t2),"B1");
        Line l2 = network.addLineAuto(VehicleFamily.BUS,Arrays.asList(t0,t5,t3,t6,t7,t2),"B1");

    }
}