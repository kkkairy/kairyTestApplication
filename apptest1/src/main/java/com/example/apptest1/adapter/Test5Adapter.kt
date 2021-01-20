package com.example.apptest1.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.apptest1.R

class Test5Adapter(data: List<String>) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.adapter_test5, data) {
    override fun convert(helper: BaseViewHolder, item: String) {
        helper.setText(R.id.tv_content, item)
    }
}