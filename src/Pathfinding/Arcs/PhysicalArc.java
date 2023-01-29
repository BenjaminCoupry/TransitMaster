package Pathfinding.Arcs;

import Pathfinding.Cost.Cost;
import Pathfinding.Cost.DistanceElements;
import Pathfinding.Places.PhysicalPlace;
import Pathfinding.Places.Place;

public class PhysicalArc<P extends PhysicalPlace> extends WeightedOrientedArc<P>{
    double speed;
    public PhysicalArc(P start, P finish, double speed) {
        super(start, finish);
        this.speed = speed;
    }
    @Override
    public Cost getCost() {
        double distance = getStart().distance(getFinish());
        double time = distance/speed;
        Cost cost = new Cost(0);
        cost.setDistanceElement(DistanceElements.TIME.label,time);
        cost.setDistanceElement(DistanceElements.DISTANCE.label, distance);
        return cost;
    }

    @Override
    public PhysicalArc<P> getReverse()
    {
        return new PhysicalArc<>(getFinish(),getStart(),getSpeed());
    }

    public double getSpeed() {
        return speed;
    }
}
