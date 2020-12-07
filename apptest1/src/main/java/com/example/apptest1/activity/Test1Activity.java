package com.example.apptest1.activity;

import com.example.apptest1.R;
import com.example.apptest1.base.BaseActivity;
import com.example.apptest1.databinding.ActivityTest1Binding;
import com.example.apptest1.util.ScreenUtil;

/**
 * 旋转动画
 */
public class Test1Activity extends BaseActivity<ActivityTest1Binding> {

    @Override
    protected int getResourceId() {
        return R.layout.activity_test1;
    }

    @Override
    protected void initView() {
        int width = ScreenUtil.getScreenWidth(this);
        binding.carrousel.setR((float) width / 3)//设置R的大小
                .setAutoRotation(true)//是否自动切换
                .setAutoRotationTime(1500);//自动切换的时间  单位毫秒
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

}
