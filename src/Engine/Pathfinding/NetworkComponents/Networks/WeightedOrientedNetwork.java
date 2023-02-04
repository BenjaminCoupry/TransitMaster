package Engine.Pathfinding.NetworkComponents.Networks;
import Engine.Pathfinding.NetworkComponents.Arcs.*;
import Engine.Pathfinding.NetworkComponents.Cost.Cost;
import Engine.Pathfinding.NetworkComponents.Cost.Evaluators.CostEvaluator;
import Engine.Pathfinding.NetworkComponents.Places.Place;

import java.util.*;
import java.util.stream.Collectors;

public class WeightedOrientedNetwork<A extends OrientedArc<P> & WeightedArc, P extends Place> extends OrientedNetwork<A,P> {

    public Cost getCost(P start, P finish)
    {
        Optional<A> arc = getArc(start,finish);
        Cost cost;
        if(arc.isPresent()) {
            cost  = arc.get().getCost();
        }else {
            cost = new Cost(Double.POSITIVE_INFINITY);
        }
        return cost;
    }
    public  Optional<CompositeWeightedOrientedArc<P,A>> placesChainToCompositeWeightedOrientedArc(List<P> places)
    {
        Optional<CompositeOrientedArc<P,A>> compositeOrientedArc  = placesChainToCompositeOrientedArc(places);
        if(compositeOrientedArc.isPresent())
        {
            CompositeWeightedOrientedArc<P, A> weightedItinerary = new CompositeWeightedOrientedArc<>(compositeOrientedArc.get().getChildren());
            return Optional.of(weightedItinerary);
        }else{
            return Optional.empty();
        }
    }

    public Cost getTravelCost(List<P> travelElements)
    {
        Optional<CompositeWeightedOrientedArc<P, A>> compositeWeightedOrientedArc = placesChainToCompositeWeightedOrientedArc(travelElements);
        if(compositeWeightedOrientedArc.isEmpty())
        {
            return new Cost(0.0);
        }else{

            return compositeWeightedOrientedArc.get().getCost();
        }
    }

    public DistanceInfo<P> getDistanceInfo(P startPlace, CostEvaluator evaluator)
    {
        DistanceInfo<P> distanceInfo = new DistanceInfo<>(getPlaces());
        distanceInfo.setDistance(startPlace,0);
        distanceInfo.setPredecessor(startPlace,startPlace);
        Collection<P> notExplored = getPlaces().stream().collect(Collectors.toList());
        while (!notExplored.isEmpty())
        {
            Optional<P> nearestUnexploredOpt = notExplored.stream()
                    .sorted(Comparator.comparingDouble(p -> evaluator.evaluate(distanceInfo.getDistance(p)))).findFirst();
            P nearestUnexploredPlace = nearestUnexploredOpt.get();
            notExplored.remove(nearestUnexploredPlace);
            Collection<A> exitingArcs = getExitingArcs(nearestUnexploredPlace);
            Collection<A> notExploredExitingArcs = exitingArcs.stream()
                    .filter(a->notExplored.contains(a.getFinish())).collect(Collectors.toList());
            for(A notExploredExitingArc : notExploredExitingArcs)
            {
                Cost actualCost = distanceInfo.getDistance(notExploredExitingArc.getFinish());
                Cost proposition = distanceInfo.getDistance(nearestUnexploredPlace).sumWith(notExploredExitingArc.getCost());
                if(evaluator.evaluate(proposition)<evaluator.evaluate(actualCost))
                {
                    distanceInfo.setDistance(notExploredExitingArc.getFinish(),proposition);
                    distanceInfo.setPredecessor(notExploredExitingArc.getFinish(),nearestUnexploredPlace);
                }
            }
        }
        return distanceInfo;
    }

    public Optional<CompositeWeightedOrientedArc<P,A>> getBestItinerary(P startPlace, P finishPlace, CostEvaluator evaluator){
        DistanceInfo<P> distanceInfo = getDistanceInfo(startPlace,evaluator);
        List<P> placesChain = distanceInfo.getPlacesChain(finishPlace);
        Optional<CompositeWeightedOrientedArc<P,A>> result = placesChainToCompositeWeightedOrientedArc(placesChain);
        return result;
    }

    public CompositeWeightedNetwork<A,P> getNetworkFactorization(Collection<OrientedArc<P>> arcsToExpand, CostEvaluator evaluator) {
        CompositeWeightedNetwork<A,P> expansion = new CompositeWeightedNetwork<>();
        expansion.addPlaces(getPlaces());
        Set<P> starts = arcsToExpand.stream().map(a -> a.getStart()).collect(Collectors.toSet());
        Map<Place, DistanceInfo<P>> distancesInfo = starts.stream().collect(Collectors.toMap(p -> p, p -> getDistanceInfo(p,evaluator)));
        for(OrientedArc<P> expandOrientedArc : arcsToExpand) {
            DistanceInfo<P> distanceInfo = distancesInfo.get(expandOrientedArc.getStart());
            List<P> placesChain = distanceInfo.getPlacesChain(expandOrientedArc.getFinish());
            Optional<CompositeWeightedOrientedArc<P,A>> itinerary = placesChainToCompositeWeightedOrientedArc(placesChain);
            if(itinerary.isPresent()) {
                CompositeWeightedOrientedArc<P, A> compositeArc = new CompositeWeightedOrientedArc<>(itinerary.get().getChildren());
                expansion.addArc(compositeArc);
            }
        }
        return expansion;
    }

    public CompositeWeightedNetwork<A,P> getCompleteNetworkFactorization(Collection<P> placesToExpand, CostEvaluator evaluator)
    {
        Collection<OrientedArc<P>> arcsToExpand = placesToExpand.stream().flatMap(p->placesToExpand.stream()
                .map(q->new OrientedArc<P>(p,q))).collect(Collectors.toList());
        CompositeWeightedNetwork<A,P> networkExpansion = getNetworkFactorization( arcsToExpand, evaluator);
        return networkExpansion;
    }
}
