package com.example.finalapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class Fragmentaccessserclass extends FragmentPagerAdapter {
    public Fragmentaccessserclass(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0: {
                Messege messege = new Messege();
                return messege;
            }
            case 1: {
                Active active = new Active();
                return active;
            }
            case 2: {
                Findfriend findfriend = new Findfriend();
                return findfriend;
            }
            case 3: {
                Settings settings = new Settings();
                return settings;
            }
            case 4: {
                RequestacceptFragment requestacceptFragment = new RequestacceptFragment();
                return requestacceptFragment;
            }
            default:
                return null;


        }
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0: {
                return "messege";
            }
            case 1: {

                return "onlie";
            }
            case 2: {
                return "findfriend";
            }
            case 3: {
                return "settings";
            }
            case 4: {
                return "request";
            }
            default:
                 return super.getPageTitle(position);



        }
    }
}
