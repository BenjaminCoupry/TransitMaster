package Pathfinding;

import Pathfinding.Places.Place;

import java.util.*;

public class DistanceInfo<P extends Place>{
    Map<P,P> predecessors;
    Map<P,Double> distances;

    public DistanceInfo(Map<P, P> predecessors, Map<P, Double> distances) {
        this.predecessors = predecessors;
        this.distances = distances;
    }

    public DistanceInfo(Collection<P> places) {
        predecessors = new Hashtable<>();
        distances = new Hashtable<>();
        for(P place : places)
        {
            distances.put(place,Double.POSITIVE_INFINITY);
        }
    }

    public Map<P, P> getPredecessors() {
        return predecessors;
    }

    public void setPredecessor(P key, P predecessor)
    {
        getPredecessors().put(key,predecessor);
    }
    public Map<P, Double> getDistances() {
        return distances;
    }

    public double getDistance(P place)
    {
        return distances.get(place);
    }

    public void setDistance(P place, double distance)
    {
        distances.put(place, distance);
    }

    public List<P> getPlacesChain(P goalPlace)
    {
        List<P> path = new LinkedList<>();
        path.add(goalPlace);
        while((getPredecessors().containsKey(path.get(0)))&&(!path.contains(getPredecessors().get(path.get(0)))))
        {
            path.add(0,getPredecessors().get(path.get(0)));
        }
        return path;
    }
}
