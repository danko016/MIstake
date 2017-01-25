package com.example.dev.mistakeerror.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.dev.mistakeerror.error_component.ErrorFragment;
import com.example.dev.mistakeerror.mistake.MistakeFragment;

/**
 * Created by dev on 19.01.17..
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter{


    public ViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new ErrorFragment();
        } else {
            return new MistakeFragment();
        }

    }

    @Override
    public int getCount() {
        return 2;
    }
}