package Engine.Pathfinding.TravellingSalesman;

import Engine.Pathfinding.NetworkComponents.Arcs.CompositeOrientedArc;
import Engine.Pathfinding.NetworkComponents.Arcs.WeightedOrientedArc;
import Engine.Pathfinding.NetworkComponents.Cost.Evaluators.CostEvaluator;
import Engine.Pathfinding.NetworkComponents.Networks.WeightedOrientedNetwork;
import Engine.Pathfinding.NetworkComponents.Places.Place;

import java.util.Set;

public abstract class TravellingSalesman<A extends WeightedOrientedArc<P>, P extends Place> {
    WeightedOrientedNetwork<CompositeOrientedArc<P, A>, P> factorizedNetwork;
    Set<P> toVisit;

    CostEvaluator evaluator;

    public TravellingSalesman(WeightedOrientedNetwork<A, P> network, Set<P> toVisit, CostEvaluator evaluator) {
        this.evaluator = evaluator;
        this.toVisit = toVisit;
        WeightedOrientedNetwork<CompositeOrientedArc<P, A>, P> completeFactorization =
                network.getCompleteNetworkFactorization(toVisit, evaluator);
        this.factorizedNetwork = completeFactorization;

    }

    public TravellingSalesman(WeightedOrientedNetwork<A, P> network,CostEvaluator evaluator) {
        this(network, network.getPlaces(),evaluator);
    }

    public WeightedOrientedNetwork<CompositeOrientedArc<P, A>, P> getFactorizedNetwork()
    {
        return factorizedNetwork;
    }
    Set<P> getToVisit()
    {
        return toVisit;
    }
    abstract SalesmanResult<A,P> proposeOptimalTravel(ItineraryMode itineraryMode);

    public CostEvaluator getEvaluator() {
        return evaluator;
    }
}
