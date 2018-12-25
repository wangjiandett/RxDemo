package com.moa.rxdemo.mvp.view.demons

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.navigation.Navigation
import com.moa.rxdemo.R
import com.moa.rxdemo.base.ui.BaseListFragment
import com.moa.rxdemo.base.ui.adapter.ViewHolder
import com.moa.rxdemo.mvvm.view.ViewModeFragment

/**
 * 类或文件描述
 *
 * Created by：wangjian on 2018/12/20 14:42
 */
class SamplesFragment : BaseListFragment<SamplesFragment.SampleItem>() {

    private lateinit var listView: ListView;


    override fun getLayoutId(): Int {
        return R.layout.tt_fragment_samples;
    }

    override fun initView(view: View?) {
        super.initView(view)
        listView = view?.findViewById(R.id.lv_list)!!;
        bindAdapter(listView)

        listView.setOnItemClickListener({ parent, view, position, id ->
            val item = parent.adapter.getItem(position) as SampleItem;
            item.actionId?.let { Navigation.findNavController(view).navigate(it) }
        })
    }

    override fun initData() {
        super.initData()

        val list = arrayListOf(
                SampleItem(RoomFragment::class.java.simpleName, R.id.action_2_room),
                SampleItem(DispatcherFragment::class.java.simpleName, R.id.action_2_dispathcer),
                SampleItem(ViewsFragment::class.java.simpleName, R.id.action_2_views),
                SampleItem(SwipeRefreshFragment::class.java.simpleName, R.id.action_2_swipe),
                SampleItem(CheckAbleFragment::class.java.simpleName, R.id.action_2_checkable),
                SampleItem(ViewModeFragment::class.java.simpleName, R.id.action_2_view_model)

        )

        mHolderAdapter.setListAndNotify(list)
    }

    override fun getViewHolder(): ViewHolder<SampleItem> {
        return SamplesHolder();
    }

    class SamplesHolder : ViewHolder<SampleItem>() {

        lateinit var tvText: TextView;

        override fun init(data: SampleItem?, viewGroup: ViewGroup?, context: Context?): View {
            val view = View.inflate(context, android.R.layout.simple_list_item_1, null)
            tvText = view as TextView;
            return view;
        }

        override fun bind(data: SampleItem?, position: Int, context: Context?) {
            tvText.setText(data!!.name)
        }
    }

    class SampleItem(name: String, actionId: Int) {
        var name: String? = name;
        var actionId: Int? = actionId;

    }

}