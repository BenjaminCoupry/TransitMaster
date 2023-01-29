package TravellingSalesman;

import Pathfinding.Arcs.CompositeOrientedArc;
import Pathfinding.Arcs.WeightedOrientedArc;
import Pathfinding.Networks.WeightedOrientedNetwork;
import Pathfinding.Places.Place;

import java.util.List;
import java.util.Optional;

public class SalesmanResult<A extends WeightedOrientedArc<P>, P extends Place> {
    Optional<List<CompositeOrientedArc<P, A>>> compositeOrientedArcs;
    WeightedOrientedNetwork<CompositeOrientedArc<P, A>, P> getFactorizedNetwork;
    List<P> bestPath;

    public SalesmanResult(Optional<List<CompositeOrientedArc<P, A>>> compositeOrientedArcs, WeightedOrientedNetwork<CompositeOrientedArc<P, A>, P> getFactorizedNetwork, List<P> bestPath) {
        this.compositeOrientedArcs = compositeOrientedArcs;
        this.getFactorizedNetwork = getFactorizedNetwork;
        this.bestPath = bestPath;
    }

    public Optional<List<CompositeOrientedArc<P, A>>> getCompositeOrientedArcs() {
        return compositeOrientedArcs;
    }

    public WeightedOrientedNetwork<CompositeOrientedArc<P, A>, P> getGetFactorizedNetwork() {
        return getFactorizedNetwork;
    }

    public List<P> getBestPath() {
        return bestPath;
    }
}
