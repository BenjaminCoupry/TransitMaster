package Engine.Pathfinding.TravellingSalesman;

import Engine.Pathfinding.NetworkComponents.Arcs.CompositeOrientedArc;
import Engine.Pathfinding.NetworkComponents.Arcs.WeightedOrientedArc;
import Engine.Pathfinding.NetworkComponents.Networks.WeightedOrientedNetwork;
import Engine.Pathfinding.NetworkComponents.Places.Place;

import java.util.List;
import java.util.Optional;

public class SalesmanResult<A extends WeightedOrientedArc<P>, P extends Place> {
    List<CompositeOrientedArc<P, A>> compositeOrientedArcs;
    WeightedOrientedNetwork<CompositeOrientedArc<P, A>, P> getFactorizedNetwork;
    List<P> bestPath;

    public SalesmanResult(List<CompositeOrientedArc<P, A>> compositeOrientedArcs, WeightedOrientedNetwork<CompositeOrientedArc<P, A>, P> getFactorizedNetwork, List<P> bestPath) {
        this.compositeOrientedArcs = compositeOrientedArcs;
        this.getFactorizedNetwork = getFactorizedNetwork;
        this.bestPath = bestPath;
    }

    public List<CompositeOrientedArc<P, A>> getCompositeOrientedArcs() {
        return compositeOrientedArcs;
    }

    public WeightedOrientedNetwork<CompositeOrientedArc<P, A>, P> getGetFactorizedNetwork() {
        return getFactorizedNetwork;
    }

    public List<P> getBestPath() {
        return bestPath;
    }
}
