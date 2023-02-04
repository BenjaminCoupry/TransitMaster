package Engine.Pathfinding.NetworkComponents.Arcs;

import Engine.Pathfinding.NetworkComponents.Cost.Cost;
import Engine.Pathfinding.NetworkComponents.Places.Place;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CompositeWeightedOrientedArc<P extends Place,A extends OrientedArc<P> & WeightedArc> extends CompositeOrientedArc<P,A> implements WeightedArc {
    public CompositeWeightedOrientedArc(List<A> children) {
        super(children);
    }

    @Override
    public Cost getCost() {
        Cost sumCost = children.stream().map(p->p.getCost()).reduce((c1, c2)->c1.sumWith(c2)).get();
        return sumCost;
    }

    public List<Cost> getTravelCosts()
    {
        List<Cost> travelCosts = getChildren().stream().map(WeightedArc::getCost).collect(Collectors.toList());
        return travelCosts;
    }

    public List<Cost> getCumulativeTravelCosts()
    {
        List<Cost> totalTravelCosts = new LinkedList<>();
        List<Cost> travelCosts = getTravelCosts();
        Cost cost = new Cost(0);
        for(int i=0; i < getChildren().size(); i++)
        {
            totalTravelCosts.add(cost);
            Cost travelCost = i<getChildren().size()-1 ? travelCosts.get(i) : new Cost(0);
            cost = cost.sumWith(travelCost);
        }
        return totalTravelCosts;
    }
}
