package Engine.Pathfinding.NetworkComponents.Places;

import java.util.Objects;

public class Place {
    static int UIDCount=0;
    int UID;
    public Place() {
        this.UID = UIDCount;
        UIDCount ++;
    }
    public int getUID()
    {
        return UID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return getUID() == place.getUID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUID());
    }

    @Override
    public String toString() {
        return "Place_"+UID;
    }
}
