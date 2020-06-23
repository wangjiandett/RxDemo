/*
 * Copyright (c) 2018.  For more infomation visit https://github.com/wangjiandett/RxDemo
 */
package com.moa.baselib.base.net.mvvm;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * viewModel基类,封装网络请求
 */
public class BaseViewModel<T> extends ViewModel {

    protected T mRepository;

    protected BaseViewModel() {
        try {
            //使用反射创建mRepository对象
            //1.获取子类类型
            //this:当前运行时的实例
            Class clz = this.getClass();//this指的是当前运行的实例（子类实例）

            //2.获取类的泛型父类
            //Type: 是Java里面所有类型的父接口
            //获取泛型父类，必须用该方法，此处的泛型父类不是指当前的类，而是具体继承的BaseAction<Standard>，当前类为BaseAction<T>泛型尚未确定
            Type type = clz.getGenericSuperclass();

            //3.把Type转换为具体的类型
            //将泛型父类转换为具体的那种类型
            ParameterizedType pt = (ParameterizedType) type;

            //4.从具体类型中获取泛
            Class modelClass = (Class) pt.getActualTypeArguments()[0];//获取具体泛型类Action中的泛型

            //5.创建泛型类的的对象
            mRepository = (T) modelClass.getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public T getRepository() {
        return mRepository;
    }

    /**
     * 发送请求
     *
     * @param requestParams 请求参数
     */
    public <D> LiveData<ResponseData<D>> sendRequest(Object requestParams, final LiveData<ResponseData<D>> dataLiveData) {

        // 设置请求参数
        MutableLiveData<Object> requestLiveData = new MutableLiveData<>();

        // 设置响应
        LiveData<ResponseData<D>> userEntityLiveData = Transformations.switchMap(requestLiveData, new Function<Object, LiveData<ResponseData<D>>>() {
            @Override
            public LiveData<ResponseData<D>> apply(Object input) {
                return dataLiveData;
            }
        });

        requestLiveData.postValue(requestParams);
        return userEntityLiveData;
    }


}
