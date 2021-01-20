package com.example.apptest1.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apptest1.R
import com.example.apptest1.adapter.Test5Adapter
import com.example.apptest1.base.BaseFragment
import com.example.apptest1.databinding.FragmentTest1Binding

class FirstFragment(private var tags: String) : BaseFragment<FragmentTest1Binding?>() {
    override fun getResourceId(): Int {
        return R.layout.fragment_test1
    }

    override fun initView() {}
    override fun initData() {
        val testList = arrayListOf<String>()
        for (n in 1..50) {
            testList.add("${tags}item${n}")
        }
        binding?.rvContent?.layoutManager = LinearLayoutManager(requireContext())
//        binding?.rvContent?.layoutManager = GridLayoutManager(this, 2)
        binding?.rvContent?.adapter = Test5Adapter(testList)
    }

    override fun initListener() {}

}