package Engine.Pathfinding.NetworkComponents.Networks;

import Engine.Pathfinding.Itinerary.WeightedItinerary;
import Engine.Pathfinding.NetworkComponents.Arcs.OrientedArc;
import Engine.Pathfinding.NetworkComponents.Arcs.CompositeOrientedArc;
import Engine.Pathfinding.NetworkComponents.Arcs.WeightedOrientedArc;
import Engine.Pathfinding.NetworkComponents.Cost.Cost;
import Engine.Pathfinding.NetworkComponents.Cost.Evaluators.CostEvaluator;
import Engine.Pathfinding.NetworkComponents.DistanceInfo;
import Engine.Pathfinding.Itinerary.Itinerary;
import Engine.Pathfinding.NetworkComponents.Places.Place;

import java.util.*;
import java.util.stream.Collectors;

public class WeightedOrientedNetwork<A extends WeightedOrientedArc<P>, P extends Place> extends OrientedNetwork<A,P> {

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
    public Optional<WeightedItinerary<P,A>> placesChainToWeightedItinerary(List<P> places)
    {
        Optional<Itinerary<P, A>> itinerary = placesChainToItinerary(places);
        if(itinerary.isPresent())
        {
            WeightedItinerary<P, A> weightedItinerary = new WeightedItinerary<>(itinerary.get().getArcSequence());
            return Optional.of(weightedItinerary);
        }else{
            return Optional.empty();
        }
    }
    public Cost getTravelCost(List<P> travelElements)
    {
        Optional<Itinerary<P,A>> itineraryOption = placesChainToItinerary(travelElements);
        if(itineraryOption.isEmpty())
        {
            return new Cost(0.0);
        }else{
            Itinerary<P,A> itinerary = itineraryOption.get();
            Optional<Cost> costOption = itinerary.getArcSequence().stream().map(a -> a.getCost()).reduce((c1, c2) -> c1.sumWith(c2));
            Cost cost = costOption.orElse(new Cost(0.0));
            return cost;
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

    public Optional<WeightedItinerary<P,A>> getBestItinerary(P startPlace, P finishPlace, CostEvaluator evaluator){
        DistanceInfo<P> distanceInfo = getDistanceInfo(startPlace,evaluator);
        List<P> placesChain = distanceInfo.getPlacesChain(finishPlace);
        Optional<WeightedItinerary<P, A>> weightedItinerary = placesChainToWeightedItinerary(placesChain);
        return weightedItinerary;
    }

    public WeightedOrientedNetwork<CompositeOrientedArc<P,A>,P> getNetworkFactorization(Collection<OrientedArc<P>> arcsToExpand, CostEvaluator evaluator) {
        WeightedOrientedNetwork<CompositeOrientedArc<P, A>, P> expansion = new WeightedOrientedNetwork<>();
        expansion.addPlaces(getPlaces());
        Set<P> starts = arcsToExpand.stream().map(a -> a.getStart()).collect(Collectors.toSet());
        Map<Place, DistanceInfo<P>> distancesInfo = starts.stream().collect(Collectors.toMap(p -> p, p -> getDistanceInfo(p,evaluator)));
        for(OrientedArc<P> expandOrientedArc : arcsToExpand) {
            DistanceInfo<P> distanceInfo = distancesInfo.get(expandOrientedArc.getStart());
            List<P> placesChain = distanceInfo.getPlacesChain(expandOrientedArc.getFinish());
            Optional<Itinerary<P,A>> itinerary = placesChainToItinerary(placesChain);
            if(itinerary.isPresent()) {
                CompositeOrientedArc<P, A> compositeArc = new CompositeOrientedArc<>(itinerary.get().getArcSequence());
                expansion.addArc(compositeArc);
            }
        }
        return expansion;
    }

    public WeightedOrientedNetwork<CompositeOrientedArc<P,A>,P> getCompleteNetworkFactorization(Collection<P> placesToExpand, CostEvaluator evaluator)
    {
        Collection<OrientedArc<P>> arcsToExpand = placesToExpand.stream().flatMap(p->placesToExpand.stream()
                .map(q->new OrientedArc<P>(p,q))).collect(Collectors.toList());
        WeightedOrientedNetwork<CompositeOrientedArc<P,A>,P> networkExpansion = getNetworkFactorization( arcsToExpand, evaluator);
        return networkExpansion;
    }
}
