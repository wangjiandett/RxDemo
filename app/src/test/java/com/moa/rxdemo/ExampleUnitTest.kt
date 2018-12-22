package com.moa.rxdemo

import android.content.Intent
import android.view.View
import org.junit.Test
import kotlin.properties.Delegates

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    // 让代码中尽量少的使用!!


    // 1.使用val 声明变量
    private val name: String? = null;

    // 2.使用lateinit var 声明变量，不支持基本数据类型
    private lateinit var adapter: View
    // 3.基本数据类型的声明方式
    private var number: Int by Delegates.notNull<Int>()

    @Test
    fun test() {
        adapter = View(null);

        val a: String? = null;
        // 4.使用let形式, a不为空的时候执行let中的代码
        a?.let {
            System.out.println("=============${it}")
        }

        // a不为空的时候获取length，不论length是否为空都执行let中代码
        a?.length.let {
            System.out.println("=============${it}")
        }

        // 5.使用 Elvis 运算符，类似java中的三目运算符
        val aa = a ?: "";

        val intent: Intent? = null;

        // 6.使用requireNotNull或checkNotNull检测是否为空，并自定义异常信息

        checkNotNull(intent, { "参数为空" })

        uploadClicked(
                requireNotNull(
                        intent!!.getStringExtra("PHOTO_URL"),
                        { "Activity parameter 'PHOTO_URL' is missing" })
        )
    }

    private var mPhotoUrl: String? = null

    fun uploadClicked(url: String) {
        if (mPhotoUrl != null) println(mPhotoUrl!!)
    }
}
