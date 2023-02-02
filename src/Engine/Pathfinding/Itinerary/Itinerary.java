package Engine.Pathfinding.Itinerary;

import Engine.Pathfinding.NetworkComponents.Arcs.OrientedArc;
import Engine.Pathfinding.NetworkComponents.Places.Place;
import Engine.Pathfinding.TravellingSalesman.ItineraryMode;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Itinerary<P extends Place, A extends OrientedArc<P>> {
    List<A> arcSequence;

    public Itinerary(List<A> arcSequence) {
        this.arcSequence = arcSequence;
    }
    public List<A> getArcSequence() {
        return arcSequence;
    }
    public Optional<List<P>> getPlacesSequence()
    {
        List<P> starts = getArcSequence().stream().skip(1).map(a -> a.getStart()).collect(Collectors.toList());
        List<P> finished = getArcSequence().stream().map(a -> a.getFinish()).collect(Collectors.toList());
        for(int i=0;i<starts.size();i++)
        {
            if(!starts.get(i).equals(finished.get(i)))
            {
                return Optional.empty();
            }
        }
        finished.add(0,getArcSequence().get(0).getStart());
        return Optional.of(finished);
    }

    public static<P extends Place> List<P> completeToItineraryMode(ItineraryMode itineraryMode, List<P> places )
    {
        List<P> result = places.stream().collect(Collectors.toList());
        switch (itineraryMode)
        {
            case ONE_WAY ->{;}
            case LOOP ->
            {
                if(!result.get(0).equals(result.get(result.size()-1)))
                {
                    result.add(result.get(0));
                }
            }
            case LINE -> {
                List<P> reverse = result.stream().collect(Collectors.toList());
                Collections.reverse(reverse);
                reverse.remove(0);
                result.addAll(reverse);
            }
        }
        return result;
    }
}
