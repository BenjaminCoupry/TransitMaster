package Pathfinding.Arcs;

import Pathfinding.Cost.Cost;
import Pathfinding.Places.Place;

public class SimpleWeightedOrientedArc<P extends Place> extends WeightedOrientedArc<P> {
    Cost cost;
    public SimpleWeightedOrientedArc(P start, P finish, Cost cost) {
        super(start, finish);
        this.cost = cost;
    }

    @Override
    public Cost getCost() {
        return cost;
    }
    @Override
    public SimpleWeightedOrientedArc<P> getReverse()
    {
        return new SimpleWeightedOrientedArc<>(getFinish(),getStart(),getCost());
    }
}
