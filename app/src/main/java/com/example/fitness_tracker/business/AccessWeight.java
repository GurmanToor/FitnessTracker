package com.example.fitness_tracker.business;

import com.example.fitness_tracker.application.Services;
import com.example.fitness_tracker.domain_specific_objects.Weight;
import com.example.fitness_tracker.persistence.IWeightPersistence;


import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;


public class AccessWeight
{
    private IWeightPersistence weightPersistence;
    private List<Weight> weights;
    private Weight weight;
    private int currentWeight;

    public AccessWeight()
    {
        weightPersistence = Services.getWeightPersistence();
        weights = null;
        weight = null;
        currentWeight = 0;
    }

    public AccessWeight(final IWeightPersistence weightPersistence) {
        this();
        this.weightPersistence = weightPersistence;
    }

    public List<Weight> getWeights()
    {
        weights = weightPersistence.getWeightSequential();
        return Collections.unmodifiableList(weights);
    }

    public Weight getSequential()
    {
        if (weights == null)
        {
            weights = weightPersistence.getWeightSequential();
            currentWeight = 0;
        }
        if (currentWeight < weights.size())
        {
            weight = weights.get(currentWeight);
            currentWeight++;
        }
        else
        {
            weights = null;
            weight = null;
            currentWeight = 0;
        }
        return weight;
    }

    public Weight getRandom(int weightID)
    {
        weight = null;
        if (weightID == 0)
        {
            //System.out.println("*** Invalid student number");
        }
        else
        {
            weights = weightPersistence.getWeightRandom(new Weight(weightID));
            if (weights.size()==1)
            {
                weight = (Weight) weights.get(0);
            }
        }
        return weight;
    }

    public Weight insertWeight(Weight currentWeight)
    {
        return weightPersistence.insertWeight(currentWeight);
    }

    public Weight updateWeight(Weight currentWeight)
    {
        return weightPersistence.updateWeight(currentWeight);
    }

    public void deleteWeight(Weight currentWeight)
    {
        weightPersistence.deleteWeight(currentWeight);
    }

    public int weightSize() {
        return weightPersistence.weightSize();
    }
}
