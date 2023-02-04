import Engine.Pathfinding.NetworkComponents.Arcs.PhysicalArc;
import Engine.Pathfinding.NetworkComponents.Cost.DistanceElements;
import Engine.Pathfinding.NetworkComponents.Cost.Evaluators.CostEvaluator;
import Engine.Pathfinding.NetworkComponents.Cost.Evaluators.ElementaryCostEvaluator;
import Engine.Pathfinding.NetworkComponents.Networks.DistanceInfo;
import Engine.Pathfinding.NetworkComponents.Networks.WeightedOrientedNetwork;
import Engine.Pathfinding.NetworkComponents.Places.PhysicalPlace;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        WeightedOrientedNetwork<PhysicalArc<PhysicalPlace>,PhysicalPlace> network = new WeightedOrientedNetwork<>();

        PhysicalPlace p0 = network.addPlace(new PhysicalPlace(1,1));
        PhysicalPlace p1 = network.addPlace(new PhysicalPlace(1,2));
        PhysicalPlace p2 = network.addPlace(new PhysicalPlace(2,5));
        PhysicalPlace p3 = network.addPlace(new PhysicalPlace(4,1));
        PhysicalPlace p4 = network.addPlace(new PhysicalPlace(2,3));
        PhysicalPlace p5 = network.addPlace(new PhysicalPlace(12,3));

        PhysicalArc<PhysicalPlace> a01 = network.addBidirectionalArc(new PhysicalArc<>(p0,p1,2),false);
        PhysicalArc<PhysicalPlace> a03 = network.addBidirectionalArc(new PhysicalArc<>(p0,p3,3.5),false);
        PhysicalArc<PhysicalPlace> a14 = network.addBidirectionalArc(new PhysicalArc<>(p1,p4,0.1),false);
        PhysicalArc<PhysicalPlace> a34 = network.addBidirectionalArc(new PhysicalArc<>(p3,p4,1.2),false);
        PhysicalArc<PhysicalPlace> a42 = network.addBidirectionalArc(new PhysicalArc<>(p4,p2,2),false);

        CostEvaluator ce1 = new ElementaryCostEvaluator(DistanceElements.TIME);
        DistanceInfo<PhysicalPlace> distanceInfo = network.getDistanceInfo(p0,ce1);
        List<PhysicalPlace> placesChain = distanceInfo.getPlacesChain(p2);


        List<PhysicalPlace> physicalPlaces = Arrays.asList(p0, p2, p3, p5);
        //MonteCarloTravellingSalesman<CompositeOrientedArc<PhysicalPlace, PhysicalArc<PhysicalPlace>>, PhysicalPlace>
        //        monteCarloTravellingSalesman = new MonteCarloTravellingSalesman(network,physicalPlaces,ce1, 100);
        //List<PhysicalPlace> travel = monteCarloTravellingSalesman.proposeOptimalTravel();
    }
}