package Transit.Lines;

import Transit.Time.Hour;

public class PassageTime {
    Hour hour;
    TransitStop transitStop;

    public PassageTime(Hour hour, TransitStop transitStop) {
        this.hour = hour;
        this.transitStop = transitStop;
    }

    public Hour getHour() {
        return hour;
    }

    public TransitStop getTransitStop() {
        return transitStop;
    }
}
