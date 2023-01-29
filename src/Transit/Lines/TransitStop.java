package Transit.Lines;

import Transit.RoadElements.Crossing;

public class TransitStop {
    Crossing position;

    public TransitStop(Crossing position) {
        this.position = position;
    }

    public Crossing getPosition() {
        return position;
    }
}
