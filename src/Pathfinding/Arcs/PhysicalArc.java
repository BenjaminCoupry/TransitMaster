package Pathfinding.Arcs;

import Pathfinding.Places.PhysicalPlace;
import Pathfinding.Places.Place;

public class PhysicalArc<P extends PhysicalPlace> extends WeightedOrientedArc<P>{
    double speed;
    public PhysicalArc(P start, P finish, double speed) {
        super(start, finish);
        this.speed = speed;
    }
    @Override
    public double getCost() {
        return getStart().distance(getFinish())/speed;
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
