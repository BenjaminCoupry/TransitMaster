package Engine.Pathfinding.Itinerary;

import Engine.Pathfinding.NetworkComponents.Arcs.WeightedOrientedArc;
import Engine.Pathfinding.NetworkComponents.Cost.Cost;
import Engine.Pathfinding.NetworkComponents.Cost.DistanceElements;
import Engine.Pathfinding.NetworkComponents.Cost.Evaluators.CostEvaluator;
import Engine.Pathfinding.NetworkComponents.Cost.Evaluators.ElementaryCostEvaluator;
import Engine.Pathfinding.NetworkComponents.Places.Place;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class WeightedItinerary<P extends Place,A extends WeightedOrientedArc<P>> extends Itinerary<P,A>{
    public WeightedItinerary(List<A> arcSequence) {
        super(arcSequence);
    }

    public List<Cost> getTravelCosts()
    {
        List<Cost> travelCosts = getArcSequence().stream().map(WeightedOrientedArc::getCost).collect(Collectors.toList());
        return travelCosts;
    }

    public List<Cost> getTotalTravelCosts()
    {
        List<Cost> totalTravelCosts = new LinkedList<>();
        List<Cost> travelCosts = getTravelCosts();
        Cost cost = new Cost(0);
        for(int i=0; i < getArcSequence().size(); i++)
        {
            totalTravelCosts.add(cost);
            Cost travelCost = i<getArcSequence().size()-1 ? travelCosts.get(i) : new Cost(0);
            cost = cost.sumWith(travelCost);
        }
        return totalTravelCosts;
    }
}
