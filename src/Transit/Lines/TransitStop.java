package Transit.Lines;

import Engine.Pathfinding.NetworkComponents.Places.Place;
import Transit.CityComponents.RoadElements.Crossing;
import Transit.Vehicles.VehicleFamily;

public class TransitStop extends Place {
    String name;
    Crossing location;
    VehicleFamily vehicleFamily;

    public String getName() {
        return name;
    }

    public Crossing getLocation() {
        return location;
    }

    public VehicleFamily getVehicleFamily() {
        return vehicleFamily;
    }
}
