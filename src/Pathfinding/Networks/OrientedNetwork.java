package Pathfinding.Networks;

import Pathfinding.Arcs.OrientedArc;
import Pathfinding.Places.Place;

import java.util.*;
import java.util.stream.Collectors;


public class OrientedNetwork<A extends OrientedArc<P>, P extends Place> extends Network<A,P>
{
    public Optional<List<A>> placesChainToArcs(List<P> places)
    {
        if(places.size()<2)
        {
            return Optional.empty();
        }
        List<A> arcs = new LinkedList<>();
        for(int i=0;i<places.size()-1;i++)
        {
            P p0 = places.get(i);
            P p1 = places.get(i+1);
            Optional<A> arc = getArc(p0,p1);
            if(arc.isPresent()) {
                arcs.add(arc.get());
            }else{
                return Optional.empty();
            }
        }
        return Optional.of(arcs);
    }

    public Optional<List<P>> arcsChainToPlaces(List<A> arcs)
    {
        List<P> starts = arcs.stream().skip(1).map(a -> a.getStart()).collect(Collectors.toList());
        List<P> finished = arcs.stream().map(a -> a.getFinish()).collect(Collectors.toList());
        for(int i=0;i<starts.size();i++)
        {
            if(!starts.get(i).equals(finished.get(i)))
            {
                return Optional.empty();
            }
        }
        finished.add(0,arcs.get(0).getStart());
        return Optional.of(finished);
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
    public Optional<A> getArc(P start, P finish)
    {
        Collection<A> exitingArcs = getExitingArcs(start);
        Optional<A> arc = exitingArcs.stream().filter(a -> a.getFinish().equals(finish)).findFirst();
        return arc;
    }

    public void removeArc(P start, P finish)
    {
        Optional<A> arc = getArc(start, finish);
        if(arc.isPresent())
        {
            removeArc(arc.get());
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