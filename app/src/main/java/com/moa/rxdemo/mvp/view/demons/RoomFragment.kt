package com.moa.rxdemo.mvp.view.demons

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import com.moa.baselib.base.ui.BaseFragment
import com.moa.baselib.base.ui.adapter.HolderAdapter
import com.moa.baselib.base.ui.adapter.ViewHolder
import com.moa.rxdemo.App
import com.moa.rxdemo.R
import com.moa.rxdemo.db.TestData
import com.moa.rxdemo.db.entity.Student

/**
 * 数据库操作实例
 *
 * Created by：wangjian on 2018/12/20 14:24
 */
class RoomFragment: BaseFragment(){

    private lateinit var samplesAdapter: SamplesAdapter;
    private lateinit var listView: ListView;

    override fun getLayoutId(): Int {
        return R.layout.tt_fragment_room
    }


    override fun initView(view: View) {
        super.initView(view)

        listView = view.findViewById<ListView>(R.id.lv_list);
        samplesAdapter = SamplesAdapter(activity as Context?);
        listView.adapter = samplesAdapter;

        val etInfo = view.findViewById<TextView>(R.id.etInfo)

        view.findViewById<View>(R.id.btnInsert).setOnClickListener {

            etInfo.text?.toString()?.let {
                val book = TestData.getStudent(it);
                // 插入数据
                App.getDataRepository().insertStudent(book);
                etInfo.text = ""
            }
        }

        listView.setOnItemClickListener { parent, view, position, id ->
            val item = parent.adapter.getItem(position) as Student;
            AlertDialog.Builder(activity)
                    .setMessage("是否删除item?")
                    .setPositiveButton("确定") { dialog, which ->
                        // 删除数据
                        App.getDataRepository().deleteStudent(item)
                    }
                    .create().show()
        }
    }

    override fun initData() {
        super.initData()

        // 查询数据，监听数据库中students列表的变化，更新显示列表
        App.getDataRepository().loadStudents().observe(this, Observer {
            samplesAdapter.list = it;
            samplesAdapter.notifyDataSetChanged()
        })
    }

    class SamplesAdapter(context: Context?) : HolderAdapter<Student>(context) {

        override fun createHolder(position: Int, obj: Student?): ViewHolder<Student> {
            return SamplesHolder();
        }
    }

    class SamplesHolder : ViewHolder<Student>() {

        lateinit var tvText: TextView;

        override fun getLayoutId(): Int {
            return android.R.layout.simple_list_item_1
        }

        override fun initView(itemView: View?, data: Student?, context: Context?) {
            tvText = findView(android.R.id.text1)
        }

        override fun bindData(data: Student?, position: Int, context: Context?) {
            tvText.text = data!!.toString()
        }
    }
}