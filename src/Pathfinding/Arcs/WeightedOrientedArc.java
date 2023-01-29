package Pathfinding.Arcs;

import Pathfinding.Cost.Cost;
import Pathfinding.Places.Place;

public abstract class WeightedOrientedArc<P extends Place> extends OrientedArc<P> {
    public WeightedOrientedArc(P start, P finish) {
        super(start, finish);
    }

    public abstract Cost getCost();

    @Override
    public abstract WeightedOrientedArc<P> getReverse();

}
