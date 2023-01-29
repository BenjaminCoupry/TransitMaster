package Pathfinding.Cost;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

public class Cost {
    double fillValue;
    Map<String,Double> distanceElements;

    public Cost(double fillValue) {
        this.fillValue = fillValue;
        distanceElements = new Hashtable<>();
    }

    public Cost(Map<String,Double> costElements,double fillValue) {
        this.fillValue = fillValue;
        distanceElements = costElements;
    }

    public Cost(String name, double value, double fillValue) {
        this.fillValue = fillValue;
        distanceElements = new Hashtable<>();
        distanceElements.put(name,value);
    }

    public double getDistanceElement(String name)
    {
        if(distanceElements.containsKey(name)) {
            return distanceElements.get(name);
        }else {
            return fillValue;
        }
    }

    public void setDistanceElement(String name, double value)
    {
        distanceElements.put(name,value);
    }

    public void addDistanceElement(String name, double value)
    {
        double sum = getDistanceElement(name)+value;
        distanceElements.put(name,sum);
    }

    public void addCost(Cost other)
    {
        Set<String> keys = new HashSet<>();
        keys.addAll(distanceElements.keySet());
        keys.addAll(other.getDistanceElements().keySet());
        for(String k:keys)
        {
            addDistanceElement(k,other.getDistanceElement(k));
        }
        fillValue  = fillValue + other.getFillValue();
    }

    public Cost sumWith(Cost other)
    {
        Cost result = new Cost(0);
        result.addCost(this);
        result.addCost(other);
        return result;
    }

    public double getFillValue() {
        return fillValue;
    }

    public Map<String, Double> getDistanceElements() {
        return distanceElements;
    }

    @Override
    public String toString() {
        String result = "Cost(";
        for(String k : getDistanceElements().keySet())
        {
            result+=k+":"+getDistanceElements().get(k)+";";
        }
        result += "Other:"+fillValue+")";
        return result;
    }
}
