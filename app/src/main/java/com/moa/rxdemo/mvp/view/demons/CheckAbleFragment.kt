package com.moa.rxdemo.mvp.view.demons

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import com.moa.rxdemo.R
import com.moa.rxdemo.base.ui.BaseListFragment
import com.moa.rxdemo.base.ui.adapter.ViewHolder

/**
 * 类或文件描述
 *
 * Created by：wangjian on 2018/12/20 14:42
 */
class CheckAbleFragment : BaseListFragment<String>() {

    private lateinit var listView: ListView;


    override fun getLayoutId(): Int {
        return R.layout.tt_fragment_samples;
    }

    override fun initView(view: View?) {
        super.initView(view)
        listView = view?.findViewById(R.id.lv_list) as ListView;
        bindAdapter(listView)

        listView.choiceMode = ListView.CHOICE_MODE_SINGLE

        listView.setOnItemClickListener({ parent, view, position, id ->
            getCheckItems();
        })
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

    fun getCheckItems(){
        val checkIds = listView.checkedItemIds
        showToast("选择position:${checkIds.get(0)}")
    }

    override fun getViewHolder(): ViewHolder<String> {
        return SamplesHolder();
    }

    class SamplesHolder : ViewHolder<String>() {

        lateinit var tvText: TextView;

        override fun init(data: String?, viewGroup: ViewGroup?, context: Context?): View {
            val view = View.inflate(context, R.layout.tt_item_checkable, null)
            tvText = view.findViewById(R.id.tv_title);
            return view;
        }

        override fun bind(data: String?, position: Int, context: Context?) {
            tvText.setText(data)
        }
    }
}