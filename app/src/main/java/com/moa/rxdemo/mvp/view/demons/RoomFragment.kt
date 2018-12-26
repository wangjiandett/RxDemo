package com.moa.rxdemo.mvp.view.demons

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import com.moa.rxdemo.MyApplication
import com.moa.rxdemo.R
import com.moa.rxdemo.base.db.TestData
import com.moa.rxdemo.base.db.entity.Student
import com.moa.rxdemo.base.ui.BaseFragment
import com.moa.rxdemo.base.ui.adapter.HolderAdapter
import com.moa.rxdemo.base.ui.adapter.ViewHolder

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


    override fun initView(view: View?) {
        super.initView(view)

        listView = view!!.findViewById<ListView>(R.id.lv_list);
        samplesAdapter = SamplesAdapter(activity as Context?);
        listView.adapter = samplesAdapter;

        val etInfo = view.findViewById<TextView>(R.id.etInfo)

        view.findViewById<View>(R.id.btnInsert).setOnClickListener {

            etInfo.text?.toString()?.let {
                val book = TestData.getStudent(it);
                // 插入数据
                MyApplication.getDataRepository().insertStudent(book);
                etInfo.text = ""
            }
        }

        listView.setOnItemClickListener { parent, view, position, id ->
            val item = parent.adapter.getItem(position) as Student;
            AlertDialog.Builder(activity)
                    .setMessage("是否删除item?")
                    .setPositiveButton("确定") { dialog, which ->
                        // 删除数据
                        MyApplication.getDataRepository().deleteStudent(item)
                    }
                    .create().show()
        }
    }

    override fun initData() {
        super.initData()

        // 查询数据，监听数据库中students列表的变化，更新显示列表
        MyApplication.getDataRepository().loadStudents().observe(this, Observer {
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

        override fun init(data: Student?, viewGroup: ViewGroup?, context: Context?): View {
            val view = View.inflate(context, android.R.layout.simple_list_item_1, null)
            tvText = view as TextView;
            return view;
        }

        override fun bind(data: Student?, position: Int, context: Context?) {
            tvText.text = data!!.toString()
        }
    }
}