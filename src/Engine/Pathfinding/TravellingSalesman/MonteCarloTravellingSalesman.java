package Engine.Pathfinding.TravellingSalesman;

import Engine.Pathfinding.Itinerary.WeightedItinerary;
import Engine.Pathfinding.NetworkComponents.Arcs.CompositeOrientedArc;
import Engine.Pathfinding.NetworkComponents.Arcs.WeightedOrientedArc;
import Engine.Pathfinding.NetworkComponents.Cost.Cost;
import Engine.Pathfinding.NetworkComponents.Cost.Evaluators.CostEvaluator;
import Engine.Pathfinding.Itinerary.Itinerary;
import Engine.Pathfinding.NetworkComponents.Networks.WeightedOrientedNetwork;
import Engine.Pathfinding.NetworkComponents.Places.Place;

import java.util.*;
import java.util.stream.Collectors;

public class MonteCarloTravellingSalesman<A extends WeightedOrientedArc<P>, P extends Place> extends TravellingSalesman<A,P>{
    Random random;
    int maxIteration;

    public MonteCarloTravellingSalesman(WeightedOrientedNetwork<A, P> network, Set<P> toVisit, CostEvaluator evaluator, int maxIteration) {
        super(network, toVisit,evaluator);
        this.maxIteration = maxIteration;
        random = new Random();
    }

    public MonteCarloTravellingSalesman(WeightedOrientedNetwork<A, P> network, CostEvaluator evaluator, int maxIteration) {
        super(network,evaluator);
        this.maxIteration = maxIteration;
        random = new Random();
    }

    private List<P> randomPlacesOrder()
    {
        List<P> order = toVisit.stream().collect(Collectors.toList());
        Collections.shuffle(order,random);
        return order;
    }

    @Override
    public Optional<WeightedItinerary<P,CompositeOrientedArc<P, A>>> proposeOptimalTravel(ItineraryMode itineraryMode) {
        CostEvaluator evaluator = getEvaluator();
        Cost minCost = new Cost(Double.POSITIVE_INFINITY);
        List<P> bestPath = randomPlacesOrder();
        for(int i=0;i<maxIteration;i++)
        {
            List<P> randomOrder = randomPlacesOrder();
            List<P> offer = Itinerary.completeToItineraryMode(itineraryMode,randomOrder);
            Cost loopCost = getTravelCost(offer);
            if(evaluator.evaluate(loopCost)<evaluator.evaluate(minCost))
            {
                minCost = loopCost;
                bestPath = offer;
            }
        }
        Optional<WeightedItinerary<P, CompositeOrientedArc<P, A>>> bestItinerary = getFactorizedNetwork().placesChainToWeightedItinerary(bestPath);
        return bestItinerary;
    }
}
