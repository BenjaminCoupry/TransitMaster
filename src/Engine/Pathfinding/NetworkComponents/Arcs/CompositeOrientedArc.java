package Engine.Pathfinding.NetworkComponents.Arcs;

import Engine.Pathfinding.NetworkComponents.Cost.Cost;
import Engine.Pathfinding.NetworkComponents.Places.Place;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CompositeOrientedArc<P extends Place,A extends WeightedOrientedArc<P>> extends WeightedOrientedArc<P> {
    List<A> children;
    public CompositeOrientedArc(List<A> children) {
        super(children.get(0).getStart(), children.get(children.size()-1).getFinish());
        this.children = children;
    }
    @Override
    public Cost getCost() {
        Cost sumCost = children.stream().map(p->p.getCost()).reduce((c1, c2)->c1.sumWith(c2)).get();
        return sumCost;
    }
    @Override
    public CompositeOrientedArc<P,WeightedOrientedArc<P>> getReverse()
    {
        List<WeightedOrientedArc<P>> invChild = getChildren().stream().map(a -> a.getReverse()).collect(Collectors.toList());
        Collections.reverse(invChild);
        return new CompositeOrientedArc<P,WeightedOrientedArc<P>>(invChild);
    }

    public List<A> getChildren() {
        return children;
    }
}
