package Engine.Pathfinding.NetworkComponents.Cost.Evaluators;

import Engine.Pathfinding.NetworkComponents.Cost.Cost;

public interface CostEvaluator {
    double evaluate(Cost cost);
}
