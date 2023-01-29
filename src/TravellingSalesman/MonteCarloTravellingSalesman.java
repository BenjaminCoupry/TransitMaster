package TravellingSalesman;

import Pathfinding.Arcs.CompositeOrientedArc;
import Pathfinding.Arcs.WeightedOrientedArc;
import Pathfinding.Cost.Cost;
import Pathfinding.Cost.Evaluators.CostEvaluator;
import Pathfinding.Networks.WeightedOrientedNetwork;
import Pathfinding.Places.Place;

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
    public SalesmanResult<A,P> proposeOptimalTravel() {
        CostEvaluator evaluator = getEvaluator();
        Cost minCost = new Cost(Double.POSITIVE_INFINITY);
        List<P> bestPath = toVisit.stream().toList();
        for(int i=0;i<maxIteration;i++)
        {
            List<P> offer = randomPlacesOrder();
            Cost loopCost = getFactorizedNetwork().getLoopCost(offer);
            if(evaluator.evaluate(loopCost)<evaluator.evaluate(minCost))
            {
                minCost = loopCost;
                bestPath = offer;
            }
        }
        bestPath.add(bestPath.get(0));
        Optional<List<CompositeOrientedArc<P, A>>> compositeOrientedArcs = getFactorizedNetwork().placesChainToArcs(bestPath);
        return new SalesmanResult<A, P>(compositeOrientedArcs,getFactorizedNetwork(),bestPath);
    }
}
