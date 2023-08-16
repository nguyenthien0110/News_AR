package com.qnu.news;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.qnu.news.fragment.CategoryFragment;
import com.qnu.news.fragment.FavoriteFragment;
import com.qnu.news.fragment.HomeFragment;
import com.qnu.news.fragment.PersonFragment;
import com.qnu.news.fragment.SearchFragment;

public class MainActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            if (item.getItemId() == R.id.menu_home) {
                selectedFragment = new HomeFragment();
            } else if (item.getItemId() == R.id.menu_category) {
                selectedFragment = new CategoryFragment();
            } else if (item.getItemId() == R.id.menu_search) {
                selectedFragment = new SearchFragment();
            } else if (item.getItemId() == R.id.menu_favorite) {
                selectedFragment = new FavoriteFragment();
            } else if (item.getItemId() == R.id.menu_person) {
                selectedFragment = new PersonFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .addToBackStack(null)
                        .commit();
                return true;
            }

            return false;
        });

    }
}