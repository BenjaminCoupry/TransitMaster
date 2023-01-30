package Transit.Time;

public class Hour {
    double value;

    public Hour(double value) {
        this.value = value%24.0;
    }

    public void increment(double value)
    {
        this.value = (this.value+value)%24.0;
    }

    public Hour incremented(double value)
    {
        Hour result = new Hour(this.value);
        result.increment(value);
        return result;
    }

    public double timeTo(Hour other)
    {
        double delta = other.value-value;
        return delta%24.0;
    }
}
