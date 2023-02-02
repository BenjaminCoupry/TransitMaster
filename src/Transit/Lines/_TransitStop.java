package Transit.Lines;

import Transit.CityComponents.RoadElements.Crossing;

import java.util.HashSet;
import java.util.Set;

public class _TransitStop {
    Crossing position;
    String name;
    Set<_PassageTime> passages;

    public _TransitStop(Crossing position, String name) {
        this.position = position;
        this.passages = new HashSet<>();
        this.name = name;
    }
    public void addPassage(_PassageTime passage)
    {
        passages.add(passage);
    }

    public void removePassage(_PassageTime passage)
    {
        passages.remove(passage);
    }

    public Crossing getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "TransitStop["+position.toString()+"] : "+name;
    }

    public String getName() {
        return name;
    }
}
