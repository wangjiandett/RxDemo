package com.moa.rxdemo.mvp.view.demons.recycler

import com.moa.rxdemo.view.recycler.IData

class Data(var data: String) : IData {

    override fun getType(): Int {
        return 0
    }
}
