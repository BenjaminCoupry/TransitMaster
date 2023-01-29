package Pathfinding.Networks;

import Pathfinding.Arcs.Arc;
import Pathfinding.Places.Place;

import java.util.*;
import java.util.stream.Collectors;


public class Network<A extends Arc<P>, P extends Place>
{
    Map<Integer,P> placeUID;
    Set<P> places;
    Set<A> arcs;

    public Network() {
        placeUID = new Hashtable<>();
        places = new HashSet<>();
        arcs = new HashSet<>();
    }

    public P addPlace(P place)
    {
        placeUID.put(place.getUID(),place);
        places.add(place);
        return place;
    }

    public void addPlaces(Collection<P> toAdd)
    {
        Map<Integer, P> addMap = toAdd.stream().collect(Collectors.toMap(p -> p.getUID(), p -> p));
        placeUID.putAll(addMap);
        places.addAll(toAdd);
    }
    public A addArc(A arc)
    {
        arcs.add(arc);
        return arc;
    }

    public Collection<A> getRelatedArcs(P place)
    {
        List<A> relatedArcs = arcs.stream().filter(a -> a.isConnected(place)).collect(Collectors.toList());
        return relatedArcs;
    }
    public Collection<P> getRelatedPlaces(P place)
    {
        Collection<A> relatedArcs = getRelatedArcs(place);
        List<P> relatedPlaces = relatedArcs.stream()
                .map(a -> a.getA().equals(place) ? a.getB() : a.getA()).collect(Collectors.toList());
        return relatedPlaces;
    }
    public P getPlace(int UID)
    {
        return placeUID.get(UID);
    }

    public Set<P> getPlaces() {
        return places;
    }

    public Set<A> getArcs() {
        return arcs;
    }



}