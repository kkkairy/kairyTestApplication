package com.example.apptest1.base;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.gyf.immersionbar.ImmersionBar;

public abstract class BaseActivity<V extends ViewDataBinding> extends AppCompatActivity {
    public V binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(getResourceId());
        binding = DataBindingUtil.setContentView(this, getResourceId());
        //状态栏设置
        ImmersionBar.with(this).statusBarDarkFont(statusBarDark()).keyboardEnable(true).init();
        initView();
        initData();
        initListener();
    }

    /**
     * 状态栏字体深色或亮色
     * true 深色
     */
    protected boolean statusBarDark() {
        return true;
    }

    protected abstract int getResourceId();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initListener();
}
