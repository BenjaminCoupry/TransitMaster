package Engine.Pathfinding.NetworkComponents.Networks;

import Engine.Pathfinding.NetworkComponents.Arcs.CompositeWeightedOrientedArc;
import Engine.Pathfinding.NetworkComponents.Arcs.OrientedArc;
import Engine.Pathfinding.NetworkComponents.Arcs.WeightedArc;
import Engine.Pathfinding.NetworkComponents.Places.Place;

public class CompositeWeightedNetwork<A extends OrientedArc<P> & WeightedArc,P extends Place> extends WeightedOrientedNetwork<CompositeWeightedOrientedArc<P,A>,P>{
}
