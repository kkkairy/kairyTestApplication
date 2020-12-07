package com.example.apptest1.activity;


import com.example.apptest1.R;
import com.example.apptest1.base.BaseActivity;
import com.example.apptest1.databinding.ActivityTest2Binding;

/**
 * Activity过渡动画之ActivityOptionsCompat
 */
public class Test2Activity extends BaseActivity<ActivityTest2Binding> {

    @Override
    protected int getResourceId() {
        return R.layout.activity_test2;
    }

    @Override
    protected void initView() {
//        ViewCompat.setTransitionName(binding.ivContent, getString(R.string.image));
//        ViewCompat.setTransitionName(binding.tvGo2, TEXT);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        binding.ivContent.setOnClickListener(view -> onBackPressed());
        binding.tvGo2.setOnClickListener(view -> onBackPressed());
    }

}
