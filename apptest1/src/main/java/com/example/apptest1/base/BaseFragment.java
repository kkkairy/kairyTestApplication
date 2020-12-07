package com.example.apptest1.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.ImmersionOwner;
import com.gyf.immersionbar.components.ImmersionProxy;

public abstract class BaseFragment<V extends ViewDataBinding> extends Fragment implements ImmersionOwner {
    public V binding;
    /**
     * ImmersionBar代理类
     */
    private ImmersionProxy mImmersionProxy = new ImmersionProxy(this);

    /*
     * 返回一个需要展示的View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getResourceId(), null, false);
        //状态栏设置
        ImmersionBar.with(this).statusBarDarkFont(statusBarDark()).keyboardEnable(true).init();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mImmersionProxy.onActivityCreated(savedInstanceState);
        initView();
        initData();
        initListener();
    }


    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).statusBarDarkFont(statusBarDark()).init();
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

    @Override
    public void onLazyBeforeView() {

    }

    @Override
    public void onLazyAfterView() {

    }

    @Override
    public void onVisible() {

    }

    @Override
    public void onInvisible() {

    }

    @Override
    public boolean immersionBarEnabled() {
        return true;
    }
}
