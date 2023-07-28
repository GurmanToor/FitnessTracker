package com.example.fitness_tracker.business;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.fitness_tracker.presentation.BreakfastFragment;
import com.example.fitness_tracker.presentation.DinnerFragment;
import com.example.fitness_tracker.presentation.LunchFragment;

public class DailyCalorieAdapter extends FragmentStateAdapter {
    public DailyCalorieAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    /**
     *return the appropriate fragment for the selected option
     * @param position of the selected tab
     * @return Fragment that corresponds to that tab
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment selectedFragment ;

        if (position == 0) {
            selectedFragment = new BreakfastFragment();
        } else if (position == 1) {
            selectedFragment = new LunchFragment();
        }
        else
        {
            selectedFragment = new DinnerFragment();
        }

        return selectedFragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }


}
