package Engine.Pathfinding.NetworkComponents.Places;

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
        double ox =other.getX();
        double oy=other.getY();
        return distance(ox,oy);
    }

    public double distance(double x, double y)
    {
        double dx =getX()-x;
        double dy=getY()-y;
        return Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2));
    }

    @Override
    public String toString() {
        return "PhysicalPlace_" +UID+"("+x+";"+y+")";
    }
}
