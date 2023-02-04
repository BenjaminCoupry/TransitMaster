package Engine.Pathfinding.NetworkComponents.Arcs;

import Engine.Pathfinding.NetworkComponents.Places.Place;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CompositeOrientedArc <P extends Place,A extends OrientedArc<P>> extends OrientedArc<P>{
    List<A> children;
    public CompositeOrientedArc(List<A> children) {
        super(children.get(0).getStart(), children.get(children.size()-1).getFinish());
        this.children = children;
    }
    @Override
    public CompositeOrientedArc<P,OrientedArc<P>> getReverse()
    {
        List<OrientedArc<P>> invChild = getChildren().stream().map(a -> a.getReverse()).collect(Collectors.toList());
        Collections.reverse(invChild);
        return new CompositeOrientedArc<P,OrientedArc<P>>(invChild);
    }

    public List<A> getChildren() {
        return children;
    }

    public Optional<List<P>> getPlacesSequence()
    {
        List<P> starts = getChildren().stream().skip(1).map(a -> a.getStart()).collect(Collectors.toList());
        List<P> finished = getChildren().stream().map(a -> a.getFinish()).collect(Collectors.toList());
        for(int i=0;i<starts.size();i++)
        {
            if(!starts.get(i).equals(finished.get(i)))
            {
                return Optional.empty();
            }
        }
        finished.add(0,getChildren().get(0).getStart());
        return Optional.of(finished);
    }
}
