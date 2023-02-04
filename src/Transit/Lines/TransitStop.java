package Transit.Lines;

import Engine.Pathfinding.NetworkComponents.Pointer;
import Engine.Pathfinding.NetworkComponents.Places.Place;
import Transit.CityComponents.RoadElements.Crossing;
import Transit.Vehicles.VehicleFamily;

public class TransitStop extends Place implements Pointer<Crossing> {
    String name;
    Crossing location;
    VehicleFamily vehicleFamily;

    public String getName() {
        return name;
    }

    public VehicleFamily getVehicleFamily() {
        return vehicleFamily;
    }

    @Override
    public Crossing getPointed() {
        return location;
    }
}
