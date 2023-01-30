package Transit.Lines;

import Transit.CityComponents.RoadElements.Crossing;

import java.util.HashSet;
import java.util.Set;

public class TransitStop {
    Crossing position;
    Set<Line> lines;

    public TransitStop(Crossing position) {
        this.position = position;
        this.lines = new HashSet<>();
    }

    public void addLine(Line line)
    {
        lines.add(line);
    }

    public void removeLine(Line line)
    {
        lines.remove(line);
    }

    public Crossing getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "TransitStop["+position.toString()+"]";
    }
}
