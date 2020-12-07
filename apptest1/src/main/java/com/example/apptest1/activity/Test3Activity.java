package com.example.apptest1.activity;

import android.util.TypedValue;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.apptest1.R;
import com.example.apptest1.base.BaseActivity;
import com.example.apptest1.databinding.ActivityTest3Binding;
import com.example.apptest1.fragment.FirstFragment;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Kotlin例子
 */
public class Test3Activity extends BaseActivity<ActivityTest3Binding> {
    private String[] titles = {"1111", "2222", "3333"};
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected int getResourceId() {
        return R.layout.activity_test3;
    }

    @Override
    protected void initView() {
        //初始化fragmentList
        for (String title : titles) {
            fragmentList.add(new FirstFragment(title));
        }
//        初始化viewPager的adapter
        binding.vpContent.setAdapter(new MyPagerViewAdapter(getSupportFragmentManager()));

        //tab绑定viewPager
        binding.tabTitle.setViewPager(binding.vpContent);
        binding.vpContent.setOffscreenPageLimit(2);
    }

    public class MyPagerViewAdapter extends FragmentPagerAdapter {
        public MyPagerViewAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        //tab切换事件
        binding.tabTitle.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                setSelectPosition(position);
            }

            @Override
            public void onTabReselect(int position) {
                setSelectPosition(position);
            }
        });
        //viewPager切换事件
        binding.vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setSelectPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 切换改变title颜色等
     *
     * @param position
     */
    public void setSelectPosition(int position) {
        for (int i = 0; i < binding.tabTitle.getTabCount(); i++) {
            TextView tabTitleView = binding.tabTitle.getTitleView(i);
            if (tabTitleView != null) {
                tabTitleView.setTextColor(ContextCompat.getColor(this, i == position ? R.color.text_color_333 : R.color.text_color_ccc));
                tabTitleView.setTextAppearance(this, i == position ? R.style.VipTopTabLayoutSelect : R.style.VipTopTabLayoutUnSelect);
                tabTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(i == position ? R.dimen.dp17 : R.dimen.dp15));
            }
        }
    }


}
