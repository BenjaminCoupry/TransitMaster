package Pathfinding.Arcs;

import Pathfinding.Places.Place;

public class OrientedArc<P extends Place> extends Arc<P>{


    public OrientedArc(P start, P finish) {
        super(start,finish);
    }

    public P getStart()
    {
        return getA();
    }

    public P getFinish()
    {
        return getB();
    }

    public OrientedArc<P> getReverse()
    {
        return new OrientedArc<>(getFinish(),getStart());
    }

}
