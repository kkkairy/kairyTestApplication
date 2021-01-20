package com.example.apptest1.activity

import android.util.TypedValue
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.apptest1.R
import com.example.apptest1.base.BaseActivity
import com.example.apptest1.databinding.ActivityTest5Binding
import com.example.apptest1.fragment.FirstFragment
import com.flyco.tablayout.listener.OnTabSelectListener

/**
 * 折叠布局
 */
class Test5Activity : BaseActivity<ActivityTest5Binding?>() {
    var titles = arrayListOf("1111", "2222", "3333")
    var fragmentList = arrayListOf<Fragment>()
    override fun getResourceId(): Int = R.layout.activity_test5
    override fun initView() {
        //初始化fragmentList
        titles.let {
            it.forEach { item ->
                fragmentList.add(FirstFragment(item))
            }
        }
        //初始化viewPager的adapter
        binding?.vpContent?.adapter = MyPagerViewAdapter(supportFragmentManager, fragmentList, titles)

        //tab绑定viewPager
        binding?.tabTitle?.setViewPager(binding?.vpContent)
        binding?.vpContent?.offscreenPageLimit = 2
    }

    override fun initData() = Unit

    override fun initListener() {
        //tab切换事件
        binding?.tabTitle?.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                setSelectPosition(position)
            }

            override fun onTabReselect(position: Int) {
                setSelectPosition(position)
            }
        })
        //viewPager切换事件
        binding?.vpContent?.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                setSelectPosition(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    class MyPagerViewAdapter(fm: FragmentManager?, private var fmF: ArrayList<Fragment>, private var fmT: ArrayList<String>) : FragmentPagerAdapter(fm!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int): Fragment {
            return fmF[position]
        }

        override fun getCount(): Int {
            return fmF.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return fmT[position]
        }
    }

    /**
     * 切换改变title颜色等
     *
     * @param position
     */
    fun setSelectPosition(position: Int) {
        for (i in 0 until binding?.tabTitle?.tabCount!!) {
            val tabTitleView: TextView = binding?.tabTitle!!.getTitleView(i)
            if (tabTitleView != null) {
                tabTitleView.setTextColor(ContextCompat.getColor(this, if (i == position) R.color.text_color_333 else R.color.text_color_ccc))
                tabTitleView.setTextAppearance(this, if (i == position) R.style.VipTopTabLayoutSelect else R.style.VipTopTabLayoutUnSelect)
                tabTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimensionPixelSize(if (i == position) R.dimen.dp17 else R.dimen.dp15).toFloat())
            }
        }
    }
}