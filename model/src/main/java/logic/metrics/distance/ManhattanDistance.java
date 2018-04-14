package logic.metrics.distance;

import javafx.util.Pair;
import logic.metrics.Distance;
import logic.model.entity.WordVector;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.lang.Math.abs;

public class ManhattanDistance implements Distance<WordVector>
{
    
    @Override
    public Comparator<Pair<Integer, Double>> getComparator()
    {
        return Comparator.comparing(Pair<Integer, Double>::getValue);
    }
    
    @Override
    public double distance(WordVector wv1, WordVector wv2)
    {
        double sum = 0d;
        Map<Integer, Double> firstValues = wv1.getContent();
        Map<Integer, Double> secondValues = wv2.getContent();
        Set<Integer> allKeys = new HashSet<>();
        allKeys.addAll(firstValues.keySet());
        allKeys.addAll(secondValues.keySet());
        for(Integer key : allKeys)
        {
            double firstValue = firstValues.getOrDefault(key, 0d);
            double secondValue = secondValues.getOrDefault(key, 0d);
            sum += abs(firstValue - secondValue);
        }
        return sum;
    }
}