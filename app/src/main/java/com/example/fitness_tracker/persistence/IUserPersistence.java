package com.example.fitness_tracker.persistence;

import com.example.fitness_tracker.domain_specific_objects.User;

import java.util.List;

public interface IUserPersistence {
    List<User> getUserSequential();

    List<User> getUserRandom(final User currentUser);

    User insertUser(final User currentUser);

    User updateUser(final User currentUser);

    void deleteUser(final User currentUser);

    int userSize();
}
