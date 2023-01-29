import Pathfinding.Arcs.CompositeOrientedArc;
import Pathfinding.Arcs.OrientedArc;
import Pathfinding.Arcs.PhysicalArc;
import Pathfinding.Arcs.WeightedOrientedArc;
import Pathfinding.DistanceInfo;
import Pathfinding.Networks.OrientedNetwork;
import Pathfinding.Networks.WeightedOrientedNetwork;
import Pathfinding.Places.PhysicalPlace;
import Pathfinding.Places.Place;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        WeightedOrientedNetwork<PhysicalArc<PhysicalPlace>,PhysicalPlace> network = new WeightedOrientedNetwork<>();
        PhysicalPlace p0 = network.addPlace(new PhysicalPlace(1,1));
        PhysicalPlace p1 = network.addPlace(new PhysicalPlace(1,2));
        PhysicalPlace p2 = network.addPlace(new PhysicalPlace(2,5));
        PhysicalPlace p3 = network.addPlace(new PhysicalPlace(4,1));
        PhysicalPlace p4 = network.addPlace(new PhysicalPlace(2,3));

        PhysicalArc<PhysicalPlace> a01 = network.addBidirectionalArc(new PhysicalArc<>(p0,p1,1),false);
        PhysicalArc<PhysicalPlace> a03 = network.addBidirectionalArc(new PhysicalArc<>(p0,p3,3.5),false);
        PhysicalArc<PhysicalPlace> a14 = network.addBidirectionalArc(new PhysicalArc<>(p1,p4,1),false);
        PhysicalArc<PhysicalPlace> a34 = network.addBidirectionalArc(new PhysicalArc<>(p3,p4,1),false);
        PhysicalArc<PhysicalPlace> a42 = network.addBidirectionalArc(new PhysicalArc<>(p4,p2,1),false);

        DistanceInfo<PhysicalPlace> distanceInfo = network.getDistanceInfo(p0);
        List<PhysicalPlace> placesChain = distanceInfo.getPlacesChain(p2);

        OrientedArc<PhysicalPlace> e1 = new OrientedArc<>(p0,p2);
        OrientedArc<PhysicalPlace> e2 = new OrientedArc<>(p0,p3);
        OrientedArc<PhysicalPlace> e3 = new OrientedArc<>(p3,p2);
        OrientedArc<PhysicalPlace> e4 = new OrientedArc<>(p2,p3);
        List<OrientedArc<PhysicalPlace>> orientedArcs = Arrays.asList(e1, e2, e3, e4);
        OrientedNetwork<CompositeOrientedArc<PhysicalPlace, PhysicalArc<PhysicalPlace>>, PhysicalPlace> networkExpansion
                = network.getNetworkExpansion(orientedArcs);
    }
}