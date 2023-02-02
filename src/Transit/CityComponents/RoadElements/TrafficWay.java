package Transit.CityComponents.RoadElements;

import Engine.Pathfinding.NetworkComponents.Arcs.PhysicalArc;
import Transit.Vehicles.VehicleFamily;

public class TrafficWay extends PhysicalArc<Crossing> {
    VehicleFamily vehicleFamily;

    public TrafficWay(Crossing start, Crossing finish, double speed, VehicleFamily vehicleFamily) {
        super(start, finish, speed);
        this.vehicleFamily = vehicleFamily;
    }

    public VehicleFamily getVehicleFamily() {
        return vehicleFamily;
    }
}
