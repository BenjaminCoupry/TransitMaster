package Engine.Pathfinding.Itinerary;

import Engine.Pathfinding.NetworkComponents.Places.Place;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Itinerary {
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
