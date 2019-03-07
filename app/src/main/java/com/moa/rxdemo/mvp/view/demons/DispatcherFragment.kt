package com.moa.rxdemo.mvp.view.demons

import android.view.View
import com.moa.baselib.base.dispatcher.Runtimes
import com.moa.baselib.base.ui.BaseFragment
import com.moa.rxdemo.R

/**
 * dispatcher 使用demo
 *
 * Created by：wangjian on 2018/12/21 15:58
 */
class DispatcherFragment : BaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.tt_fragment_dispatcher;
    }

    override fun initView(view: View) {
        super.initView(view)
        view.let {
            // handler异步执行
            it.findViewById<View>(R.id.btn_sync_event).setOnClickListener {
                Runtimes.dispatchNow {
                    postToMain("dispatchNow event")
                }
            }

            // handler异步delay执行
            it.findViewById<View>(R.id.btn_sync_delay_event).setOnClickListener {
                Runtimes.dispatchDelay({
                    postToMain("dispatchDelay 500ms event")
                },500)
            }

            // 线程池执行
            it.findViewById<View>(R.id.btn_pool_sync_event).setOnClickListener {
                Runtimes.execute {
                    postToMain("execute event")
                }
            }

            // 主线程执行
            it.findViewById<View>(R.id.btn_main_event).setOnClickListener {
                postToMain("main event");
            }
        }
    }

    private fun postToMain(text: String){
        Runtimes.postToMainThread {
            showToast(text)
        }
    }

}