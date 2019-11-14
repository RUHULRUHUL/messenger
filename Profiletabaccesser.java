package com.example.finalapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class Profiletabaccesser extends FragmentPagerAdapter {
    public Profiletabaccesser(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
            {
                ProfileFragment profileFragment=new ProfileFragment();
                return profileFragment;
            }
            case 1:
            {
                ActivitesFragment activitesFragment=new ActivitesFragment();
                return activitesFragment;
            }
            default:
                return null;


        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {


        switch (position)
        {
            case 0:
            {

                return "profile information";
            }
            case 1:
            {

                return "Activites";
            }
            default:
                return super.getPageTitle(position);


        }
    }
}
