package com.moa.baselib.base.net.mvvm;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.moa.baselib.utils.ToastUtils;

public abstract class SimpleCallback<Response> implements Observer<ResponseData<Response>> {

    private Context mContext;

    public  SimpleCallback(Fragment fragment){
        if(fragment != null){
            this.mContext = fragment.getActivity();
        }
    }

    public  SimpleCallback(Context Context){
        this.mContext = Context;
    }

    @Override
    public void onChanged(@Nullable ResponseData<Response> responseData) {
        if(responseData != null){
            switch (responseData.loadStatus){
                case LOADING:
                    onLoading();
                    break;
                case FAIL:
                    onFailure(responseData.loadStatus.getTipMsg());
                    break;
                case SUCCESS:
                    onSuccess(responseData.request, responseData.response);
                    break;
            }
        }
    }

    public void onLoading(){}

    public void onFailure(String tipMsg){
        ToastUtils.showToast(mContext, tipMsg);
    }

    public abstract void onSuccess(Object request, Response response);
}
