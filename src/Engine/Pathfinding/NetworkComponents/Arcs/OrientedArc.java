package Engine.Pathfinding.NetworkComponents.Arcs;

import Engine.Pathfinding.NetworkComponents.Pointer;
import Engine.Pathfinding.NetworkComponents.Places.Place;

public class OrientedArc<P extends Place> extends Arc<P> implements Pointer<P> {


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

    @Override
    public String toString() {
        return "OrientedArc_" +UID+"["+getStart().toString()+"->"+getFinish().toString()+"]";
    }

    @Override
    public P getPointed() {
        return getFinish();
    }
}
