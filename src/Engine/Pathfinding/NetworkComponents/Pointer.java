package Engine.Pathfinding.NetworkComponents;

import Engine.Pathfinding.NetworkComponents.Places.Place;

public interface Pointer<P extends Place> {
    P getPointed();
}
