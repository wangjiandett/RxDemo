package com.moa.rxdemo.mvp.view.demons

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import com.moa.baselib.base.ui.BaseListFragment
import com.moa.baselib.base.ui.adapter.ViewHolder
import com.moa.rxdemo.R
import java.util.*

/**
 * CheckableLinearLayout 使用demo
 * 其他CheckableLinearRelativeLayout,CheckableFrameLayout使用方法类似
 *
 * Created by：wangjian on 2018/12/20 14:42
 */
class CheckAbleFragment : BaseListFragment<String>() {

    private lateinit var listView: ListView;


    override fun getLayoutId(): Int {
        return R.layout.tt_fragment_samples;
    }

    override fun initView(view: View) {
        super.initView(view)
        listView = view.findViewById(R.id.lv_list) as ListView;
        bindAdapter(listView)

        // ListView.CHOICE_MODE_SINGLE 此处使用多选或单选模式都可以
        listView.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        listView.setOnItemClickListener { parent, view, position, id ->
            getCheckItems();
        }
    }

    override fun initData() {
        super.initData()

        val list = arrayListOf(
                "item 00",
                "item 11",
                "item 22",
                "item 33",
                "item 44"
        )

        mHolderAdapter.setListAndNotify(list)
    }

    private fun getCheckItems(){
        // 获取到选择的item position
        val checkIds = listView.checkedItemIds

        // 获取到选中的item
//        for (postion in checkIds){
//            val it = adapter.getItem(postion.toInt());
//        }

        val ids = Arrays.toString(checkIds)
        showToast("选择position:${ids}")
    }

    override fun getViewHolder(): ViewHolder<String> {
        return SamplesHolder();
    }

    class SamplesHolder : ViewHolder<String>() {

        lateinit var tvText: TextView;

        override fun getLayoutId(): Int {
           return R.layout.tt_item_checkable
        }

        override fun initView(itemView: View, data: String?, context: Context?) {
            tvText = itemView.findViewById(R.id.tv_title);
        }

        override fun bindData(data: String?, position: Int, context: Context?) {
            tvText.text = data
        }
    }
}