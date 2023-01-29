package Pathfinding.Places;

public class PhysicalPlace extends Place{
    float x;
    float y;

    public PhysicalPlace(float x, float y) {
        super();
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public double distance(PhysicalPlace other)
    {
        double dx =getX()-other.getX();
        double dy=getY()-other.getY();
        return Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2));
    }
}
