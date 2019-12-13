package com.mangrovehotel.com;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabHolder extends FragmentPagerAdapter {
    public TabHolder(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                VisitUsFragment visitUsFragment = new VisitUsFragment();
                return visitUsFragment;

            case 1:
                BookingFragment bookingFragment = new BookingFragment();
                return bookingFragment;

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

        switch (position) {

            case 0:
                return "Visit Us";

            case 1:
                return "Booking";
        }

        return super.getPageTitle(position);
    }
}
