package Pathfinding.Networks;

import Pathfinding.Arcs.OrientedArc;
import Pathfinding.Arcs.CompositeOrientedArc;
import Pathfinding.Arcs.WeightedOrientedArc;
import Pathfinding.Cost.Cost;
import Pathfinding.Cost.Evaluators.CostEvaluator;
import Pathfinding.DistanceInfo;
import Pathfinding.Places.Place;

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

    public Cost getLoopCost(List<P> loopElements)
    {
        Cost cost = new Cost(0.0);
        for(int i0=0;i0<loopElements.size();i0++)
        {
            int i1 = (i0+1)% loopElements.size();
            P p0 = loopElements.get(i0);
            P p1 = loopElements.get(i1);
            Cost localCost = getCost(p0,p1);
            cost = cost.sumWith(localCost);
        }
        return cost;
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

    public WeightedOrientedNetwork<CompositeOrientedArc<P,A>,P> getNetworkFactorization(Collection<OrientedArc<P>> arcsToExpand, CostEvaluator evaluator) {
        WeightedOrientedNetwork<CompositeOrientedArc<P, A>, P> expansion = new WeightedOrientedNetwork<>();
        expansion.addPlaces(getPlaces());
        Set<P> starts = arcsToExpand.stream().map(a -> a.getStart()).collect(Collectors.toSet());
        Map<Place, DistanceInfo<P>> distancesInfo = starts.stream().collect(Collectors.toMap(p -> p, p -> getDistanceInfo(p,evaluator)));
        for(OrientedArc<P> expandOrientedArc : arcsToExpand) {
            DistanceInfo<P> distanceInfo = distancesInfo.get(expandOrientedArc.getStart());
            List<P> placesChain = distanceInfo.getPlacesChain(expandOrientedArc.getFinish());
            Optional<List<A>> arcs = placesChainToArcs(placesChain);
            if(arcs.isPresent()) {
                CompositeOrientedArc<P, A> compositeArc = new CompositeOrientedArc<>(arcs.get());
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
