package Pathfinding.Cost;

public enum DistanceElements {
    TIME("Time"),
    DISTANCE("Distance"),
    COST("Cost");

    public final String label;

    private DistanceElements(String label) {
        this.label = label;
    }
}
