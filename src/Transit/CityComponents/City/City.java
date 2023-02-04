package Transit.CityComponents.City;

import Engine.Pathfinding.NetworkComponents.Networks.Network;
import Engine.Pathfinding.NetworkComponents.Networks.WeightedOrientedNetwork;
import Transit.CityComponents.RoadElements.Crossing;
import Transit.CityComponents.RoadElements.PassageType;
import Transit.CityComponents.RoadElements.Road;
import Transit.CityComponents.RoadElements.TrafficWay;
import Transit.Vehicles.VehicleFamily;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    public Set<VehicleFamily> getAuthorizedVehicles()
    {
        Set<VehicleFamily> authorizedVehicles = getArcs().stream().flatMap(a -> a.getAuthorizedVehicles().stream()).collect(Collectors.toSet());
        return authorizedVehicles;
    }

    public WeightedOrientedNetwork<TrafficWay, Crossing> getSpecificNetwork(VehicleFamily vehicleFamily)
    {
        WeightedOrientedNetwork<TrafficWay, Crossing> network = new WeightedOrientedNetwork<>();
        List<TrafficWay> specificArcs = getArcs().stream().flatMap(a -> a.getTrafficWays(vehicleFamily).stream()).collect(Collectors.toList());
        network.addArcs(specificArcs);
        Set<Crossing> crossings = specificArcs.stream().flatMap(a -> a.getPlaces().stream()).collect(Collectors.toSet());
        network.addPlaces(crossings);
        return network;
    }

}
