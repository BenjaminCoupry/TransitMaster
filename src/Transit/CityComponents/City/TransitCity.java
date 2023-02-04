package Transit.CityComponents.City;

import Transit.CityComponents.RoadElements.Crossing;
import Transit.Lines.Line;
import Transit.Lines.TransitStop;
import Transit.Vehicles.VehicleFamily;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TransitCity extends City{
    Collection<TransitStop> transitStops;
    Collection<Line> lines;

    public Optional<Line> getLine(String name)
    {
        Optional<Line> line = getLines().stream().filter(l -> l.getName().equals(name)).findFirst();
        return line;
    }

    public List<TransitStop> getTransitStops(Crossing crossing)
    {
        List<TransitStop> transitStops = getTransitStops().stream()
                .filter(ts -> ts.getLocation().equals(crossing)).collect(Collectors.toList());
        return transitStops;
    }

    public Optional<TransitStop> getTransitStop(Crossing crossing, VehicleFamily family)
    {
        List<TransitStop> crossingStops = getTransitStops(crossing);
        Optional<TransitStop> stop = crossingStops.stream().filter(ts -> ts.getVehicleFamily().equals(family)).findFirst();
        return stop;
    }

    public void addTransitStop(TransitStop transitStop)
    {
        Optional<TransitStop> present = getTransitStop(transitStop.getLocation(),transitStop.getVehicleFamily());
        if(present.isEmpty()) {
            transitStops.add(transitStop);
        }
    }


    public Collection<TransitStop> getTransitStops() {
        return transitStops;
    }

    public Collection<Line> getLines() {
        return lines;
    }


}
