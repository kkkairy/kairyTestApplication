package com.example.apptest1.activity;

import android.content.Intent;

import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import android.view.View;

import com.example.apptest1.R;
import com.example.apptest1.base.BaseActivity;
import com.example.apptest1.databinding.ActivityMainBinding;
import com.example.mylibrary.MyUtils1;


public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected int getResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        String str1 = MyUtils1.getS1();
    }

    @Override
    protected void initListener() {
        //旋转
        binding.tvGo1.setOnClickListener(view -> startActivity(new Intent(this, Test1Activity.class)));
        //图片跳转
        binding.ivImage.setOnClickListener(view -> {
            ActivityOptionsCompat compat1 = ActivityOptionsCompat.makeSceneTransitionAnimation(this, binding.ivImage, getString(R.string.image));
            ActivityCompat.startActivity(this, new Intent(this, Test2Activity.class), compat1.toBundle());
        });
        //图片文字
        binding.tvGo2.setOnClickListener(view -> {
            Pair<View, String> imagePair = Pair.create(binding.ivImage, getString(R.string.image));
            Pair<View, String> textPair = Pair.create(binding.tvGo2, getString(R.string.text));
            ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, imagePair, textPair);
            ActivityCompat.startActivity(this, new Intent(this, Test2Activity.class), compat.toBundle());
        });
        //SlidingTabLayout+viewPager切换
        binding.llGo3.setOnClickListener(view -> {
            ActivityOptionsCompat compat1 = ActivityOptionsCompat.makeSceneTransitionAnimation(this, binding.llGo3, getString(R.string.parent_view));
            ActivityCompat.startActivity(this, new Intent(this, Test3Activity.class), compat1.toBundle());
        });
        //Kotlinshi使用
        binding.tvGo4.setOnClickListener(view -> startActivity(new Intent(this, Test4Activity.class)));
    }
}
