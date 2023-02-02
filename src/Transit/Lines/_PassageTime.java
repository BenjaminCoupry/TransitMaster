package Transit.Lines;

import Transit.Time.Hour;

public class _PassageTime {
    Hour hour;
    String transitStopName;
    String lineName;
    int itineraryProgress;

    public _PassageTime(Hour hour, _TransitStop transitStop, _Line line, int itineraryProgress) {
        this.hour = hour;
        this.transitStopName = transitStop.getName();
        this.lineName = line.getName();
        this.itineraryProgress = itineraryProgress;
    }

    public Hour getHour() {
        return hour;
    }

    public String getTransitStopName() {
        return transitStopName;
    }
}
