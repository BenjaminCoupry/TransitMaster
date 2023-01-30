package Engine.Pathfinding.TravellingSalesman;

import Engine.Pathfinding.NetworkComponents.Arcs.CompositeOrientedArc;
import Engine.Pathfinding.NetworkComponents.Arcs.WeightedOrientedArc;
import Engine.Pathfinding.NetworkComponents.Cost.Cost;
import Engine.Pathfinding.NetworkComponents.Cost.Evaluators.CostEvaluator;
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

    private List<P> randomPlacesOrder(ItineraryMode itineraryMode)
    {
        List<P> order = toVisit.stream().collect(Collectors.toList());
        Collections.shuffle(order,random);
        switch (itineraryMode)
        {
            case ONE_WAY -> order = order;
            case LOOP -> order.add(order.get(0));
            case LINE -> {
                List<P> reverse = order.stream().collect(Collectors.toList());
                Collections.reverse(reverse);
                reverse.remove(0);
                order.addAll(reverse);
            }
        }
        return order;
    }

    @Override
    public SalesmanResult<A,P> proposeOptimalTravel(ItineraryMode itineraryMode) {
        CostEvaluator evaluator = getEvaluator();
        Cost minCost = new Cost(Double.POSITIVE_INFINITY);
        List<P> bestPath = randomPlacesOrder(itineraryMode);
        for(int i=0;i<maxIteration;i++)
        {
            List<P> offer = randomPlacesOrder(itineraryMode);
            Cost loopCost = getFactorizedNetwork().getTravelCost(offer);
            if(evaluator.evaluate(loopCost)<evaluator.evaluate(minCost))
            {
                minCost = loopCost;
                bestPath = offer;
            }
        }
        Optional<List<CompositeOrientedArc<P, A>>> compositeOrientedArcs = getFactorizedNetwork().placesChainToArcs(bestPath);
        return new SalesmanResult<A, P>(compositeOrientedArcs.get(),getFactorizedNetwork(),bestPath);
    }
}
