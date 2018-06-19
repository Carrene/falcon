package de.netalic.falcon.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.presenter.AuthenticationDefinitionContract;
import de.netalic.falcon.util.NonSwipeAbleViewPager;

public class AuthenticationDefinitionFragment extends Fragment implements AuthenticationDefinitionContract.View {

    private View mRoot;
    private NonSwipeAbleViewPager mViewPager;
    private TabLayout mTabLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_authenticationdefinition, container, false);
        setHasOptionsMenu(true);
        initUiComponents();
        setupViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);
        return mRoot;
    }

    public static AuthenticationDefinitionFragment newInstance() {

        return new AuthenticationDefinitionFragment();
    }

    @Override
    public void setPresenter(AuthenticationDefinitionContract.Presenter presenter) {

    }


    public static class SectionsPageAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        public SectionsPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getFragmentManager());
        adapter.addFragment(new AuthenticationDefinitionPasswordTab(), getContext().getString(R.string.athenticationdefinition_password));
        adapter.addFragment(new AuthenticationDefinitionPatternTab(), getContext().getString(R.string.authenticationdefinition_pattern));
        viewPager.setAdapter(adapter);
    }

    private void initUiComponents() {

        mViewPager = mRoot.findViewById(R.id.viewpager_authentication_definition);
        mTabLayout = mRoot.findViewById(R.id.tablayout_authentication_definition);
    }

}