package Pathfinding.Arcs;

import Pathfinding.Places.Place;

public class SimpleWeightedOrientedArc<P extends Place> extends WeightedOrientedArc<P> {
    double cost;
    public SimpleWeightedOrientedArc(P start, P finish, double cost) {
        super(start, finish);
        this.cost = cost;
    }

    @Override
    public double getCost() {
        return cost;
    }
    @Override
    public SimpleWeightedOrientedArc<P> getReverse()
    {
        return new SimpleWeightedOrientedArc<>(getFinish(),getStart(),getCost());
    }
}
