package TravellingSalesman;

import Pathfinding.Arcs.CompositeOrientedArc;
import Pathfinding.Arcs.WeightedOrientedArc;
import Pathfinding.Cost.Evaluators.CostEvaluator;
import Pathfinding.Networks.WeightedOrientedNetwork;
import Pathfinding.Places.Place;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
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
    abstract SalesmanResult<A,P> proposeOptimalTravel();

    public CostEvaluator getEvaluator() {
        return evaluator;
    }
}
