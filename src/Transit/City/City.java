package Transit.City;

import Pathfinding.Networks.Network;
import Transit.RoadElements.Crossing;
import Transit.RoadElements.PassageType;
import Transit.RoadElements.Road;
import Transit.Vehicles.VehicleFamily;

import java.util.Optional;

public class City extends Network<Road, Crossing> {

    public void connect(Crossing a, Crossing b, VehicleFamily vehicleFamily, PassageType passageType, double speed) {
        Optional<Road> relatingArc = getRelatingArc(a, b);
        Road road = relatingArc.orElseGet(() -> addArc(new Road(a, b)));
        road.authorizeVehicle(vehicleFamily,passageType,speed);
    }

    public void connect(Crossing a,float x, float y,VehicleFamily vehicleFamily, PassageType passageType, double speed)
    {
        Crossing b = addPlace(new Crossing(x,y));
        connect(a,b,vehicleFamily,passageType,speed);
    }

    public void disconnect(Crossing a, Crossing b, VehicleFamily vehicleFamily) {
        Optional<Road> relatingArc = getRelatingArc(a, b);
        if(relatingArc.isPresent()) {
            Road road = relatingArc.get();
            road.unAuthorizeVehicle(vehicleFamily);
            if(road.getAuthorizedVehicles().isEmpty())
            {
                removeArc(road);
            }
        }
    }

}
