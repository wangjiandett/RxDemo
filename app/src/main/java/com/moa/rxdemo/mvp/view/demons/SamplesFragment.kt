package com.moa.rxdemo.mvp.view.demons

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.navigation.Navigation
import com.moa.rxdemo.R
import com.moa.rxdemo.base.ui.BaseListFragment
import com.moa.rxdemo.base.ui.H5Activity
import com.moa.rxdemo.base.ui.adapter.ViewHolder
import com.moa.rxdemo.mvp.view.demons.recycler.RecyclerViewFragment
import com.moa.rxdemo.mvvm.view.ViewModeFragment
import com.moa.rxdemo.utils.PermissionHelper

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
        listView = view?.findViewById<ListView>(R.id.lv_list) as ListView;
        bindAdapter(listView)

        listView.setOnItemClickListener { parent, view, position, id ->
            val item = parent.adapter.getItem(position) as SampleItem
            item.actionId?.let {

                val bundle = Bundle()

                if (it == R.id.action_2_scan) {
                    // 扫一扫需要震动和相机访问权
                    var hasPerNeedReq = PermissionHelper.checkAndRequestPermissions(this, activity, 1,
                            arrayOf(Manifest.permission.VIBRATE, Manifest.permission.CAMERA))
                    if (hasPerNeedReq) {
                        Navigation.findNavController(view).navigate(it)
                    }
                }
                else {
                    if(it == R.id.action_2_h5){
                        bundle.putSerializable(H5Activity.EXTRA_DATA, H5Activity.H5Request("快递查询", "https://m.kuaidi100" +
                                ".com/index_all.html"))
                    }
                    Navigation.findNavController(view).navigate(it ,bundle)
                }

            }
        }
    }

    override fun initData() {
        super.initData()

        val list = arrayListOf(
                SampleItem(RoomFragment::class.java.simpleName, R.id.action_2_room),
                SampleItem(DispatcherFragment::class.java.simpleName, R.id.action_2_dispathcer),
                SampleItem(ViewsFragment::class.java.simpleName, R.id.action_2_views),
                SampleItem(SwipeRefreshFragment::class.java.simpleName, R.id.action_2_swipe),
                SampleItem(CheckAbleFragment::class.java.simpleName, R.id.action_2_checkable),
                SampleItem(ViewModeFragment::class.java.simpleName, R.id.action_2_view_model),
                SampleItem(RecyclerViewFragment::class.java.simpleName, R.id.action_2_recycler),
                // activity
                SampleItem(CropImageActivity::class.java.simpleName, R.id.action_2_crop),
                SampleItem(ScanActivity::class.java.simpleName, R.id.action_2_scan),
                SampleItem(H5Activity::class.java.simpleName, R.id.action_2_h5)
        )

        mHolderAdapter.setListAndNotify(list)
    }

    override fun getViewHolder(): ViewHolder<SampleItem> {
        return SamplesHolder()
    }

    class SamplesHolder : ViewHolder<SampleItem>() {

        lateinit var tvText: TextView

        override fun init(data: SampleItem?, viewGroup: ViewGroup?, context: Context?): View {
            val view = View.inflate(context, android.R.layout.simple_list_item_1, null)
            tvText = view as TextView
            return view
        }

        override fun bind(data: SampleItem?, position: Int, context: Context?) {
            tvText.text = data!!.name
        }
    }

    class SampleItem(name: String, actionId: Int) {
        var name: String? = name;
        var actionId: Int? = actionId;

    }

}