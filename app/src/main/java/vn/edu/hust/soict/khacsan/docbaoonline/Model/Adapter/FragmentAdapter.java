package vn.edu.hust.soict.khacsan.docbaoonline.Model.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import vn.edu.hust.soict.khacsan.docbaoonline.Model.Fragments.CustomFragment;


/**
 * Created by San on 11/16/2017.
 */

public class FragmentAdapter extends FragmentStatePagerAdapter {

    private ArrayList<CustomFragment> fragments;
    public FragmentAdapter(FragmentManager fm, ArrayList<CustomFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getTitle();
    }

}
