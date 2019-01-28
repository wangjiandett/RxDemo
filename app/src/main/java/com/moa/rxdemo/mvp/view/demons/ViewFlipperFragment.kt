package com.moa.rxdemo.mvp.view.demons

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterViewFlipper
import android.widget.TextView
import android.widget.ViewFlipper
import com.moa.rxdemo.R
import com.moa.rxdemo.base.ui.BaseFragment
import com.moa.rxdemo.base.ui.adapter.HolderAdapter
import com.moa.rxdemo.base.ui.adapter.ViewHolder
import com.moa.rxdemo.utils.Randoms

class ViewFlipperFragment : BaseFragment() {

    lateinit var viewFlipper: ViewFlipper

    lateinit var adapterViewFlipper: AdapterViewFlipper

    override fun getLayoutId(): Int {
        return R.layout.tt_fragment_view_flipper
    }

    override fun initView(view: View) {
        super.initView(view)

        //1 viewFlipper 使用
        viewFlipper = view.findViewById<ViewFlipper>(R.id.view_flipper)

        for (i in 0..5){
            val view = View.inflate(context, R.layout.tt_item_flipper, null)
            val textView = view.findViewById<TextView>(R.id.tv_text)
            textView.text = "第${i}个数据，${Randoms.randomInt()}获得了一等奖"
            viewFlipper.addView(view)
        }


        //2 adapterViewFlipper使用
        // 一定要用属性动画 若使用Tween动画会报异常unknown animator name translater
        adapterViewFlipper = view.findViewById<AdapterViewFlipper>(R.id.view_adapter_flipper)


        // 设置动画
        adapterViewFlipper.setInAnimation(context, R.animator.anim_flipper_left_in)
        adapterViewFlipper.setOutAnimation(context, R.animator.anim_flipper_right_out)

        val adapter = MyFlipperAdapter(context!!)
        adapterViewFlipper.adapter = adapter

        val list = arrayListOf<String>()
        for (i in 0..6){
            list.add("第${i}个数据，${Randoms.randomInt()}获得了一等奖")
        }

        adapter.list = list
        adapter.notifyDataSetChanged()
    }

    class MyFlipperAdapter(context: Context) : HolderAdapter<String>(context) {

        override fun createHolder(position: Int, obj: String?): ViewHolder<String> {
            return FlipperHolder()
        }
    }

    class FlipperHolder : ViewHolder<String>(){

        private lateinit var textView: TextView

        override fun init(data: String?, viewGroup: ViewGroup?, context: Context?): View {
            val view = getLayout(context, R.layout.tt_item_flipper)
            textView = view.findViewById<TextView>(R.id.tv_text)
            return view
        }

        override fun bind(data: String?, position: Int, context: Context?) {
            textView.text = data
        }
    }
}