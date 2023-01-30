package Transit.CityComponents.RoadElements;

import Engine.Pathfinding.NetworkComponents.Arcs.Arc;
import Engine.Pathfinding.NetworkComponents.Arcs.PhysicalArc;
import Transit.Vehicles.VehicleFamily;

import java.util.*;

public class Road extends Arc<Crossing> {

    Set<VehicleFamily> authorizedVehicles;
    Map<VehicleFamily,PassageType> passageTypes;
    Map<VehicleFamily,Double> speeds;

    public Road(Crossing a, Crossing b) {
        super(a, b);
        authorizedVehicles = new HashSet<>();
        passageTypes = new Hashtable<>();
        speeds = new Hashtable<>();
    }

    public void authorizeVehicle(VehicleFamily vehicleFamily,PassageType passageType, double speed )
    {
        authorizedVehicles.add(vehicleFamily);
        passageTypes.put(vehicleFamily,passageType);
        speeds.put(vehicleFamily,speed);
    }

    public boolean isAuthorized(VehicleFamily vehicleFamily)
    {
        return authorizedVehicles.contains(vehicleFamily);
    }

    public double getSpeed(VehicleFamily vehicleFamily)
    {
        double speed = isAuthorized(vehicleFamily) ? speeds.get(vehicleFamily) : 0.0;
        return speed;
    }

    public PassageType getPassageType(VehicleFamily vehicleFamily)
    {
        PassageType passageType =  isAuthorized(vehicleFamily) ? passageTypes.get(vehicleFamily) : PassageType.BLOCKED;
        return passageType;
    }
    public void unAuthorizeVehicle(VehicleFamily vehicleFamily)
    {
        authorizedVehicles.remove(vehicleFamily);
        passageTypes.remove(vehicleFamily);
        speeds.remove(vehicleFamily);
    }

    public Set<VehicleFamily> getAuthorizedVehicles() {
        return authorizedVehicles;
    }

    public Map<VehicleFamily, PassageType> getPassageTypes() {
        return passageTypes;
    }

    public Collection<PhysicalArc<Crossing>> generateArcs(VehicleFamily vehicleFamily)
    {
        Collection<PhysicalArc<Crossing>> arcs = new LinkedList<>();
        PassageType passageType = getPassageType(vehicleFamily);
        double speed = getSpeed(vehicleFamily);
        if(passageType.equals(PassageType.BIDIRECTIONAL) || passageType.equals(PassageType.BLOCKED_B))
        {
            PhysicalArc<Crossing> arc = new PhysicalArc<>(getB(),getA(),speed);
            arcs.add(arc);
        }
        if(passageType.equals(PassageType.BIDIRECTIONAL) || passageType.equals(PassageType.BLOCKED_A))
        {
            PhysicalArc<Crossing> arc = new PhysicalArc<>(getA(),getB(),speed);
            arcs.add(arc);
        }
        return arcs;
    }
}
