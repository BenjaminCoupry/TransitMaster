package Engine.Pathfinding.NetworkComponents.Arcs;

import Engine.Pathfinding.NetworkComponents.Cost.Cost;
import Engine.Pathfinding.NetworkComponents.Cost.DistanceElements;
import Engine.Pathfinding.NetworkComponents.Places.PhysicalPlace;

public class PhysicalArc<P extends PhysicalPlace> extends OrientedArc<P> implements WeightedArc{
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

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
