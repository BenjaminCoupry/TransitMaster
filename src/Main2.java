import Engine.Pathfinding.Itinerary.ItineraryMode;
import Transit.CityComponents.City._TransitCity;
import Transit.Lines._Line;
import Transit.Lines._TransitStop;
import Transit.CityComponents.RoadElements.Crossing;
import Transit.CityComponents.RoadElements.PassageType;
import Transit.Time.Hour;
import Transit.Vehicles.VehicleFamily;

import java.util.Arrays;

public class Main2 {
    public static void main(String[] args) {
        _TransitCity network = new _TransitCity();

        Crossing p0 = network.addPlace(new Crossing(1,1));
        Crossing p1 = network.addPlace(new Crossing(1,2));
        Crossing p2 = network.addPlace(new Crossing(2,5));
        Crossing p3 = network.addPlace(new Crossing(4,1));
        Crossing p4 = network.addPlace(new Crossing(2,3));
        Crossing p5 = network.addPlace(new Crossing(12,3));
        Crossing p6 = network.addPlace(new Crossing(1,6));
        Crossing p7 = network.addPlace(new Crossing(2,6));
        Crossing p8 = network.addPlace(new Crossing(3,5));

        network.connect(p0,p1, VehicleFamily.BUS, PassageType.BIDIRECTIONAL,2);
        network.connect(p0,p3, VehicleFamily.BUS, PassageType.BLOCKED_A,3.5);
        network.connect(p1,p4, VehicleFamily.BUS, PassageType.BIDIRECTIONAL,2);
        network.connect(p3,p4, VehicleFamily.BUS, PassageType.BLOCKED_A,2);
        network.connect(p4,p2, VehicleFamily.BUS, PassageType.BIDIRECTIONAL,0.5);
        network.connect(p4,p5, VehicleFamily.BUS, PassageType.BIDIRECTIONAL,2);
        network.connect(p2,p6, VehicleFamily.BUS, PassageType.BIDIRECTIONAL,2);
        network.connect(p2,p7, VehicleFamily.BUS, PassageType.BLOCKED_A,2);
        network.connect(p7,p8, VehicleFamily.BUS, PassageType.BLOCKED_A,2);
        network.connect(p8,p2, VehicleFamily.BUS, PassageType.BLOCKED_A,2);

        _TransitStop t0 = network.addTransitStop(p0);
        _TransitStop t3 = network.addTransitStop(p3);
        _TransitStop t5 = network.addTransitStop(p5);
        _TransitStop t2 = network.addTransitStop(p2);
        _TransitStop t7 = network.addTransitStop(p7);
        _TransitStop t6 = network.addTransitStop(p6);

        _Line l1 = network.addLine(VehicleFamily.BUS,Arrays.asList(t0,t5,t3,t6,t7,t2),"B1");
        _Line l2 = network.addLineAuto(VehicleFamily.BUS,Arrays.asList(t0,t5,t3,t6,t7,t2),"B2", ItineraryMode.LINE);
        _Line l3 = network.addLineAuto(VehicleFamily.BUS,Arrays.asList(t0,t5,t3,t6,t7,t2),"B3", ItineraryMode.LOOP);
        _Line l4 = network.addLineAuto(VehicleFamily.BUS,Arrays.asList(t0,t5,t3,t6,t7,t2),"B4", ItineraryMode.ONE_WAY);

        l2.getPassageTimes(new Hour(12.0));
    }
}