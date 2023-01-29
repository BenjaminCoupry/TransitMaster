package Pathfinding.Networks;

import Pathfinding.Arcs.OrientedArc;
import Pathfinding.Places.Place;

import java.util.*;
import java.util.stream.Collectors;


public class OrientedNetwork<A extends OrientedArc<P>, P extends Place> extends Network<A,P>
{
    public List<A> placesChainToArcs(List<P> places)
    {
        List<A> arcs = new LinkedList<>();
        for(int i=0;i<places.size()-1;i++)
        {
            P p0 = places.get(i);
            P p1 = places.get(i+1);
            A arc = getArc(p0,p1);
            arcs.add(arc);
        }
        return arcs;
    }

    public List<P> arcsChainToPlaces(List<A> arcs)
    {
        List<P> places = arcs.stream().map(a -> a.getStart()).collect(Collectors.toList());
        places.add(arcs.get(arcs.size()-1).getFinish());
        return places;
    }
    public Collection<A> getEnteringArcs(P place)
    {
        List<A> entering = getArcs().stream().filter(a -> a.getFinish().equals(place)).collect(Collectors.toList());
        return entering;
    }

    public Collection<A> getExitingArcs(P place)
    {
        List<A> exiting = getArcs().stream().filter(a -> a.getStart().equals(place)).collect(Collectors.toList());
        return exiting;
    }
    public A getArc(P start, P finish)
    {
        Collection<A> exitingArcs = getExitingArcs(start);
        Optional<A> arc = exitingArcs.stream().filter(a -> a.getFinish().equals(finish)).findFirst();
        if(arc.isPresent())
        {
            return arc.get();
        }else {
            return null;
        }
    }
    public Collection<P> getAccessiblePlaces(P place)
    {
        Collection<A> exitingArcs = getExitingArcs(place);
        List<P> neighbouringPlaces = exitingArcs.stream().map(a -> a.getFinish()).collect(Collectors.toList());
        return neighbouringPlaces;
    }

    public A addBidirectionalArc(A arc, boolean getReversed)
    {
        OrientedArc<P> reverse = arc.getReverse();
        super.addArc(arc);
        super.addArc((A) reverse);
        return getReversed ? ((A) reverse) : arc;
    }
}