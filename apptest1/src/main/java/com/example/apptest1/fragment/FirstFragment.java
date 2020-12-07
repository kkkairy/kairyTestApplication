package com.example.apptest1.fragment;

import com.example.apptest1.R;
import com.example.apptest1.base.BaseFragment;
import com.example.apptest1.databinding.FragmentTest1Binding;

public class FirstFragment extends BaseFragment<FragmentTest1Binding> {
    private String tag;

    public FirstFragment(String tag) {
        this.tag = tag;
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_test1;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        binding.textview1.setText(tag);
    }

    @Override
    protected void initListener() {

    }

}
