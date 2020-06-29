package com.moa.baselib.base.net;

import com.moa.baselib.utils.FileUtil;
import com.moa.baselib.utils.LogUtils;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * 文件下载 base model
 */
public abstract class BaseDownloadFileModel implements ProgressResultListener {

    /**
     * 监听下载进度，需要设置拦截器
     *
     * @param listener 监听下载进度
     */
    protected abstract Observable<ResponseBody> getDownloadObservable(ProgressResultListener listener);

    /**
     * 下载文件
     *
     * @param destFile 文件保存位置
     */
    public void download(final File destFile) {
        getDownloadObservable(this)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new ResultFilter(destFile))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<File>(){

                    @Override
                    public void onNext(File file) {
                        LogUtils.d("download file success: "+file.getAbsoluteFile());
                        onSuccess(file);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d(e);
                        onFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {}
                });
    }

    private static class ResultFilter implements Function<ResponseBody, File> {

        private File desFile;

        public ResultFilter(File desFile) {
            this.desFile = desFile;
        }

        @Override
        public File apply(ResponseBody responseBody) throws Exception {
            if(desFile != null){
                FileUtil.writeInputStream2File(responseBody.byteStream(), desFile.getAbsolutePath());
            }
            return desFile;
        }
    }


    /**
     * the success callback
     *
     * @param value the success value
     */
    protected abstract void onSuccess(File value);

    /**
     * the fail callback
     *
     * @param msg the fail message
     */
    protected abstract void onFail(String msg);

}
