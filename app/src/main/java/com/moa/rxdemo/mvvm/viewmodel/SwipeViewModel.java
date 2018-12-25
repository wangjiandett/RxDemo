package com.moa.rxdemo.mvvm.viewmodel;

import com.moa.rxdemo.mvp.bean.SwipeItem;
import com.moa.rxdemo.mvp.model.SwipeModelImpl;
import com.moa.rxdemo.mvvm.base.BaseViewModel;

import java.util.List;

/**
 * 加载swipe data的viewModel
 * <p>
 * Created by：wangjian on 2018/8/21 15:11
 */
public class SwipeViewModel extends BaseViewModel<List<SwipeItem>>{
    
    public SwipeViewModel(){
        super();
    }
    
    public void loadData(int page){
        // 此处的回调方法写在了父类中
        new SwipeModelImpl().loadSwipeList(page, this);
    }
    
    @Override
    public void onSuccess(List<SwipeItem> value) {
        super.onSuccess(value);// optional 调用父类的success状态方法
        mDataLiveData.setValue(value);
    }
}
