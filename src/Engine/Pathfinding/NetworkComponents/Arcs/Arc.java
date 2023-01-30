package Engine.Pathfinding.NetworkComponents.Arcs;

import Engine.Pathfinding.NetworkComponents.Places.Place;

import java.util.HashSet;
import java.util.Set;

public class Arc<P extends Place> {
    static int UIDCount=0;
    int UID;
    P a;
    P b;

    public Arc(P a, P b) {
        this.UID = UIDCount;
        UIDCount ++;
        this.a = a;
        this.b = b;
    }

    public boolean hasSameBounds(Arc other)
    {
        boolean same = getA().equals(other.getA()) && getB().equals(other.getB());
        boolean swapped = getA().equals(other.getB()) && getB().equals(other.getA());
        return same || swapped;
    }

    public boolean isConnected(P place)
    {
        return a.equals(place) || b.equals(place);
    }

    public Set<P> getPlaces()
    {
        Set<P> places = new HashSet<>();
        places.add(a);
        places.add(b);
        return places;
    }

    public P getA() {
        return a;
    }

    public P getB() {
        return b;
    }

    @Override
    public String toString() {
        return "Arc_" +UID+"["+a.toString()+"|"+b.toString()+"]";
    }
}
