package com.example.apptest1.activity

import com.example.apptest1.R
import com.example.apptest1.base.BaseActivity
import com.example.apptest1.databinding.ActivityTest4Binding

/**
 * Kotlin
 */
class Test4Activity : BaseActivity<ActivityTest4Binding?>() {
    override fun getResourceId(): Int {
        return R.layout.activity_test4
    }

    override fun initView() {}
    override fun initData() {
        val a = testM1(1, 2)
        val b = testM2(1, 2)
    }

    override fun initListener() {}

    //定义一个变量
    var aa: Int = 1
    var aaa = 1

    //定义一个常量
    val bb: Int = 0

    //定义一个方法
    private fun testM1(a: Int, b: Int): Int {
        return a + b
    }

    //同上的一个简写方法
    private fun testM2(a: Int, b: Int) = a + b

    //字符串模板
    private fun testM3(a: Int): String {
        var a1 = "a is $a"
        return "${a1.replace("is", "was")},but now is $a"
    }

    //条件表达式
    private fun maxOf(a: Int, b: Int): Int {
        return if (a > b) {
            a
        } else {
            b
        }
    }

    //同上方法
    private fun maxOf1(a: Int, b: Int): Int {
        return if (a > b) a else b
    }

    //同上方法
    private fun maxOf2(a: Int, b: Int) = if (a > b) a else b

    //当某个变量的值可以为 null 的时候，必须在声明处的类型后添加 ? 来标识该引用可为空。
    private fun test4(str: String): Int? {//表示返回值可为空
        return str.toInt()
    }

    //is 运算符检测一个表达式是否某类型的一个实例
    private fun test5(obj: Any): Int? {
        return if (obj is String) obj.length else null
    }

    //for /while 循环
    var testList1: ArrayList<String> = arrayListOf("abc", "bcd", "asd")
    var testList2 = arrayListOf("a", "b", "c", "d")
    private fun test6() {
        var testList3 = arrayListOf<String>("a", "b", "c", "d")
        for (str in testList3) {

        }
        for (str in testList3.indices) {

        }
        testList3.forEach {
            if (it is String) {
                var str = "$it aa"
            }
        }
        var index = 0;
        while (index < testList3.size) {
            index++
        }

        //迭代
        for (x in 1..5) {
            print(x)//12345
        }
        for (x in 10 downTo 0 step 3) {
            print(x)//9630
        }
        for (x in 0..testList3.lastIndex) {
            var str = testList3[x]
        }

        //过滤
        testList3.filter { it.startsWith("a") }
                //排序
                .sortedBy { it }
                //映射
                .map { it.toUpperCase() }//转大写
                //循环
                .forEach { its -> print(its) }
    }

    //when 表达式(相当于switch)
    private fun test7(obj: Any): String = when (obj) {
        obj is Long -> "long"
        obj is String -> "string"
        obj is Int -> "int"
        else -> "unKnown"
    }

}