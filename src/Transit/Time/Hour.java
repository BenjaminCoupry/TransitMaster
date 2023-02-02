package Transit.Time;

import java.util.Objects;

public class Hour implements Comparable<Hour>{
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

    @Override
    public int compareTo(Hour o) {
        double timeToOther = timeTo(o);
        if(timeToOther>0){
            return -1;
        } else if (timeToOther<0) {
            return 1;
        }else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hour hour = (Hour) o;
        return Double.compare(hour.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
