package Pathfinding.Networks;

import Pathfinding.Arcs.OrientedArc;
import Pathfinding.Arcs.CompositeOrientedArc;
import Pathfinding.Arcs.WeightedOrientedArc;
import Pathfinding.DistanceInfo;
import Pathfinding.Places.Place;

import java.util.*;
import java.util.stream.Collectors;

public class WeightedOrientedNetwork<A extends WeightedOrientedArc<P>, P extends Place> extends OrientedNetwork<A,P> {

    public DistanceInfo<P> getDistanceInfo(P startPlace)
    {
        DistanceInfo<P> distanceInfo = new DistanceInfo<>(getPlaces());
        distanceInfo.setDistance(startPlace,0);
        distanceInfo.setPredecessor(startPlace,startPlace);
        Collection<P> notExplored = getPlaces().stream().collect(Collectors.toList());
        while (!notExplored.isEmpty())
        {
            Optional<P> nearestUnexploredOpt = notExplored.stream()
                    .sorted(Comparator.comparingDouble(p -> distanceInfo.getDistance(p))).findFirst();
            P nearestUnexploredPlace = nearestUnexploredOpt.get();
            notExplored.remove(nearestUnexploredPlace);
            Collection<A> exitingArcs = getExitingArcs(nearestUnexploredPlace);
            Collection<A> notExploredExitingArcs = exitingArcs.stream()
                    .filter(a->notExplored.contains(a.getFinish())).collect(Collectors.toList());
            for(A notExploredExitingArc : notExploredExitingArcs)
            {
                double actualCost = distanceInfo.getDistance(notExploredExitingArc.getFinish());
                double proposition = distanceInfo.getDistance(nearestUnexploredPlace) + notExploredExitingArc.getCost();
                if(proposition<actualCost)
                {
                    distanceInfo.setDistance(notExploredExitingArc.getFinish(),proposition);
                    distanceInfo.setPredecessor(notExploredExitingArc.getFinish(),nearestUnexploredPlace);
                }
            }
        }
        return distanceInfo;
    }

    public OrientedNetwork<CompositeOrientedArc<P,A>,P> getNetworkExpansion(Collection<OrientedArc<P>> arcsToExpand) {
        OrientedNetwork<CompositeOrientedArc<P, A>, P> expansion = new OrientedNetwork<>();
        expansion.addPlaces(getPlaces());
        Set<P> starts = arcsToExpand.stream().map(a -> a.getStart()).collect(Collectors.toSet());
        Map<Place, DistanceInfo<P>> distancesInfo = starts.stream().collect(Collectors.toMap(p -> p, p -> getDistanceInfo(p)));
        for(OrientedArc<P> expandOrientedArc : arcsToExpand) {
            DistanceInfo<P> distanceInfo = distancesInfo.get(expandOrientedArc.getStart());
            List<P> placesChain = distanceInfo.getPlacesChain(expandOrientedArc.getFinish());
            List<A> arcs = placesChainToArcs(placesChain);
            CompositeOrientedArc<P, A> compositeArc = new CompositeOrientedArc<>(arcs);
            expansion.addArc(compositeArc);
        }
        return expansion;
    }
}
