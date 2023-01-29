package Pathfinding.Cost.Evaluators;

import Pathfinding.Cost.Cost;
import Pathfinding.Cost.DistanceElements;

public class ElementaryCostEvaluator implements CostEvaluator{
    String name;

    public ElementaryCostEvaluator(String name) {
        this.name = name;
    }

    public ElementaryCostEvaluator(DistanceElements name) {
        this.name = name.label;
    }

    @Override
    public double evaluate(Cost cost) {
        return cost.getDistanceElement(name);
    }
}
