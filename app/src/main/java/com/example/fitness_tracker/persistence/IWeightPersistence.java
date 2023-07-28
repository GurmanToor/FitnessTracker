package com.example.fitness_tracker.persistence;

import com.example.fitness_tracker.domain_specific_objects.User;
import com.example.fitness_tracker.domain_specific_objects.Weight;
import com.example.fitness_tracker.persistence.hsqldb.WeightPersistenceHSQLDB;

import java.util.List;

public interface IWeightPersistence {
        List<Weight> getWeightSequential();

        List<Weight> getWeightRandom(final Weight currentWeight);

        Weight insertWeight(final Weight currentWeight);

        Weight updateWeight(final Weight currentWeight);

        void deleteWeight(final Weight currentWeight);

        int weightSize();
}
