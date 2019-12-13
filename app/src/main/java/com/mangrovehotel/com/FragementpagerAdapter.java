package com.mangrovehotel.com;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class FragementpagerAdapter extends FragmentPagerAdapter {
    public FragementpagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                Fragement_one fragement_one = new Fragement_one();
                return fragement_one;

            case 1:
                Fragement_two fragement_two = new Fragement_two();
                return fragement_two;

            case 2:
                Fragement_three fragement_three = new Fragement_three();
                return fragement_three;

            case 3:
                Fragement_four fragement_four = new Fragement_four();
                return fragement_four;

            case 4:
                Fragement_five fragement_five = new Fragement_five();
                return fragement_five;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
