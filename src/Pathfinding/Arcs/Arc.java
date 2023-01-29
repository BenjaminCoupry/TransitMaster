package Pathfinding.Arcs;

import Pathfinding.Places.Place;

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
}
