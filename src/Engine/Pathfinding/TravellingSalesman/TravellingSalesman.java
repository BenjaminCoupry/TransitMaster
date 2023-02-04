package Engine.Pathfinding.TravellingSalesman;
import Engine.Pathfinding.Itinerary.ItineraryMode;
import Engine.Pathfinding.NetworkComponents.Arcs.CompositeWeightedOrientedArc;
import Engine.Pathfinding.NetworkComponents.Arcs.OrientedArc;
import Engine.Pathfinding.NetworkComponents.Arcs.WeightedArc;
import Engine.Pathfinding.NetworkComponents.Cost.Cost;
import Engine.Pathfinding.NetworkComponents.Cost.Evaluators.CostEvaluator;
import Engine.Pathfinding.NetworkComponents.Networks.WeightedOrientedNetwork;
import Engine.Pathfinding.NetworkComponents.Places.Place;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public abstract class TravellingSalesman<A extends OrientedArc<P> & WeightedArc, P extends Place> {
    Set<P> toVisit;

    CostEvaluator evaluator;

    WeightedOrientedNetwork<A, P> network;

    public TravellingSalesman(WeightedOrientedNetwork<A, P> network, Set<P> toVisit, CostEvaluator evaluator) {
        this.evaluator = evaluator;
        this.toVisit = toVisit;
        this.network = network;
    }

    public TravellingSalesman(WeightedOrientedNetwork<A, P> network,CostEvaluator evaluator) {
        this(network, network.getPlaces(),evaluator);
    }

    public Cost getTravelCost(List<P> offer)
    {
        return getNetwork().getTravelCost(offer);
    }
    Set<P> getToVisit()
    {
        return toVisit;
    }
    abstract Optional<CompositeWeightedOrientedArc<P, A>> proposeOptimalTravel(ItineraryMode itineraryMode);

    public CostEvaluator getEvaluator() {
        return evaluator;
    }

    public WeightedOrientedNetwork<A, P> getNetwork() {
        return network;
    }
}
