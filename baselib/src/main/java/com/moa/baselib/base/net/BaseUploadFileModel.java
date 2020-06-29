package com.moa.baselib.base.net;


import android.text.TextUtils;

import com.moa.baselib.base.net.mvp.BaseModel;

import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 文件上传
 * <p>
 * Created by：wangjian on 2017/12/21 11:00
 */
public abstract class BaseUploadFileModel<T> extends BaseModel<T> implements ProgressResultListener {

    /**
     * 默认上传文件参数名
     */
    private static final String DEFAULT_PART_NAME = "file";

    /**
     * 上传接口
     */
    public abstract Observable<BaseResponse<T>> getUploadObservable(RequestBody body);

    /**
     * 上传单个文件，使用默认上传文件参数名{@link #DEFAULT_PART_NAME}
     *
     * @param file 上传的文件
     */
    public void uploadFile(File file) {
        uploadFile(null, file, null);
    }

    /**
     * 上传单个文件
     *
     * @param partName 文件参数名
     * @param file 文件
     */
    public void uploadFile(String partName, File file) {
        uploadFile(partName, file, null);
    }

    /**
     * 上传单个文件
     *
     * @param partName 文件参数名
     * @param file 文件
     * @param params 附加参数
     */
    public void uploadFile(String partName, File file, Map<String, String> params) {
        // 文件
        List<File> files = new ArrayList<>();
        files.add(file);
        // 参数模块名
        List<String> filenames = new ArrayList<>();
        filenames.add(TextUtils.isEmpty(partName) ? DEFAULT_PART_NAME : partName);

        RequestBody body = getUploadRequestBody(filenames, files, params);
        request(getUploadObservable(body));
    }

    /**
     * 同时上传多个文件，使用默认上传文件参数名{@link #DEFAULT_PART_NAME}
     *
     * @param files 文件
     */
    public void uploadFiles(List<File> files) {
        uploadFiles(files, null);
    }

    /**
     * 同时上传多个文件
     *
     * @param partName 上传文件参数名,所有文件使用同一个参数名
     * @param files 文件
     * @param params 附加参数
     */
    public void uploadFiles(String partName, List<File> files, Map<String, String> params) {
        // 参数模块名
        List<String> filenames = new ArrayList<>();
        filenames.add(partName);
        RequestBody body = getUploadRequestBody(filenames, files, params);
        request(getUploadObservable(body));
    }

    /**
     * 同时上传多个文件
     *
     * @param partNames 上传文件参数名
     * @param files 文件
     * @param params 附加参数
     */
    public void uploadFiles(List<String> partNames, List<File> files, Map<String, String> params) {
        RequestBody body = getUploadRequestBody(partNames, files, params);
        request(getUploadObservable(body));
    }

    /**
     * 同时上传多个文件，使用默认上传文件参数名{@link #DEFAULT_PART_NAME}
     *
     * @param files 文件
     * @param params 附加参数
     */
    public void uploadFiles(List<File> files, Map<String, String> params) {
        RequestBody body = getUploadRequestBody(null, files, params);
        request(getUploadObservable(body));
    }

    /**
     * 上传文件RequestBody
     *
     * @param partNames 上传文件参数名
     * @param files 文件
     * @param params 附加参数
     * @return RequestBody
     */
    private RequestBody getUploadRequestBody(List<String> partNames, List<File> files, Map<String, String> params) {

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        // 添加文件
        if(files != null){
            for (int i = 0; i < files.size(); i++) {
                File file = files.get(i);
                RequestBody body = RequestBody.create(file, MediaType.parse(guessMimeType(file.getName())));
                String partName = DEFAULT_PART_NAME;
                if (partNames != null && partNames.size() > 0) {
                    if (partNames.size() == 1) {
                        partName = partNames.get(0);
                    } else {
                        partName = partNames.get(i);
                    }
                }

                builder.addFormDataPart(partName, file.getName(), body);
            }
        }

        // 添加额外参数
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                builder.addFormDataPart(key, value);
            }
        }

        return new ProgressRequestBody<>(builder.build(), this);
    }

    /**
     * 获取mime type
     *
     * @param filename 文件名
     * @return 根据文件名获取到的mime type
     */
    private String guessMimeType(String filename) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(filename);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }
}
