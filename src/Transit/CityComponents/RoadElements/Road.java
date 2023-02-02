package Transit.CityComponents.RoadElements;

import Engine.Pathfinding.NetworkComponents.Arcs.Arc;
import Engine.Pathfinding.NetworkComponents.Arcs.PhysicalArc;
import Transit.Vehicles.VehicleFamily;

import java.util.*;
import java.util.stream.Collectors;

public class Road extends Arc<Crossing> {
    Set<TrafficWay> trafficWays;
    public Road(Crossing a, Crossing b) {
        super(a, b);
        trafficWays = new HashSet<>();
    }

    public Set<TrafficWay> getTrafficWays()
    {
        return trafficWays.stream().collect(Collectors.toSet());
    }
    public Set<TrafficWay> getTrafficWays(VehicleFamily vehicleFamily)
    {
        Set<TrafficWay> concerned = trafficWays.stream().filter(tw -> tw.getVehicleFamily().equals(vehicleFamily)).collect(Collectors.toSet());
        return concerned;
    }

    public PassageType getPassageType(VehicleFamily vehicleFamily)
    {
        Set<TrafficWay> selectedWays = getTrafficWays(vehicleFamily);
        boolean aAccessible = selectedWays.stream().anyMatch(tw-> tw.getFinish().equals(getA()));
        boolean bAccessible = selectedWays.stream().anyMatch(tw-> tw.getFinish().equals(getB()));
        if(aAccessible && bAccessible)
        {
            return PassageType.BIDIRECTIONAL;
        }
        if(aAccessible)
        {
            return PassageType.BLOCKED_B;
        }
        if(bAccessible)
        {
            return PassageType.BLOCKED_A;
        }
        return PassageType.BLOCKED;
    }
    public Set<VehicleFamily> getAuthorizedVehicles() {
        Set<VehicleFamily> authorized;
        authorized = getTrafficWays().stream().map(TrafficWay::getVehicleFamily).collect(Collectors.toSet());
        return authorized;
    }
    public Map<VehicleFamily, PassageType> getPassageTypes() {
        Set<VehicleFamily> authorizedVehicles = getAuthorizedVehicles();
        Map<VehicleFamily, PassageType> passageTypeMap;
        passageTypeMap = authorizedVehicles.stream().collect(Collectors.toMap(vf -> vf, vf -> getPassageType(vf)));
        return passageTypeMap;
    }
    public boolean isAuthorized(VehicleFamily vehicleFamily)
    {
        return getAuthorizedVehicles().contains(vehicleFamily);
    }

    public void authorizeVehicle(VehicleFamily vehicleFamily,PassageType passageType, double speed )
    {
        if(passageType.equals(PassageType.BLOCKED)) {
            unAuthorizeVehicle(vehicleFamily);
        } else if (passageType.equals(PassageType.BLOCKED_A)) {
            authorizeVehicleToB(vehicleFamily, speed);
            unAuthorizeVehicleToA(vehicleFamily);
        }else if (passageType.equals(PassageType.BLOCKED_B)) {
            authorizeVehicleToA(vehicleFamily, speed);
            unAuthorizeVehicleToB(vehicleFamily);
        }else if (passageType.equals(PassageType.BIDIRECTIONAL)) {
            authorizeVehicleToA(vehicleFamily, speed);
            authorizeVehicleToB(vehicleFamily, speed);
        }
    }
    public void unAuthorizeVehicle(VehicleFamily vehicleFamily)
    {
        Set<TrafficWay> existing = getTrafficWays(vehicleFamily);
        trafficWays.removeAll(existing);
    }

    public void unAuthorizeVehicleToA(VehicleFamily vehicleFamily)
    {
        Set<TrafficWay> existing;
        existing = getTrafficWays(vehicleFamily).stream().filter(tw->tw.getFinish().equals(getA())).collect(Collectors.toSet());
        trafficWays.removeAll(existing);
    }
    public void unAuthorizeVehicleToB(VehicleFamily vehicleFamily)
    {
        Set<TrafficWay> existing;
        existing = getTrafficWays(vehicleFamily).stream().filter(tw->tw.getFinish().equals(getB())).collect(Collectors.toSet());
        trafficWays.removeAll(existing);
    }
    public void authorizeVehicleToA(VehicleFamily vehicleFamily, double speed)
    {
        Set<TrafficWay> existing;
        existing = getTrafficWays(vehicleFamily).stream().filter(tw->tw.getFinish().equals(getA())).collect(Collectors.toSet());
        if(existing.isEmpty())
        {
            trafficWays.add(new TrafficWay(getB(),getA(),speed,vehicleFamily));
        }else {
            existing.forEach(tw->tw.setSpeed(speed));
        }
    }
    public void authorizeVehicleToB(VehicleFamily vehicleFamily, double speed)
    {
        Set<TrafficWay> existing;
        existing = getTrafficWays(vehicleFamily).stream().filter(tw->tw.getFinish().equals(getB())).collect(Collectors.toSet());
        if(existing.isEmpty())
        {
            trafficWays.add(new TrafficWay(getA(),getB(),speed,vehicleFamily));
        }else {
            existing.forEach(tw->tw.setSpeed(speed));
        }
    }


}
