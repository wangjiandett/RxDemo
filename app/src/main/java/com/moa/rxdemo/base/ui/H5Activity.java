package com.moa.rxdemo.base.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.moa.rxdemo.R;
import com.moa.rxdemo.utils.LogUtils;

import java.io.Serializable;

/**
 * 更新说明:
 *
 * @author wangjian
 * @version 1.0.0
 * @create 2016/10/31
 */
public class H5Activity extends BaseActiivty implements View.OnClickListener {
    
    public static final String EXTRA_DATA = "EXTRA_DATA";
    private static String TAG = H5Activity.class.getSimpleName();
    
    public static final String EXTRA_TITLE = "title";
    // interface name
    private static String JAVA_INTERFACE_NAME = "android";
    
    private ProgressBar mPb;
    private WebView mWebView;
    
    private String mUrl = "";
    private H5Request mRequest;
    
    private static final int PB_FAKE_MAX = 85;
    
    private Handler mPbHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (mPb.getProgress() < PB_FAKE_MAX) {
                mPb.setProgress(mPb.getProgress() + 1);
                mPbHandler.sendEmptyMessageDelayed(0, 100);
            }
            return true;
        }
    });
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mRequest != null) {
            outState.putSerializable(EXTRA_DATA, mRequest);
        }
        super.onSaveInstanceState(outState);
    }
    
    @Override
    protected void getSavedData(Bundle bundle) {
        mRequest = (H5Request) bundle.getSerializable(EXTRA_DATA);
    }
    
    @Override
    protected int getLayoutId() {
        return R.layout.tt_activity_h5;
    }
    
    
    @Override
    protected void initView() {
        mPb = (ProgressBar) findViewById(R.id.pb);
        mWebView = (WebView) findViewById(R.id.wv_h5);
    }
    
    @Override
    protected void initData() {
        if (mRequest != null) {
            LogUtils.d(TAG, "title:" + mRequest.title + " ,url:" + mRequest.url);
            mUrl = mRequest.url;
            String title = TextUtils.isEmpty(mRequest.title) ? " " : mRequest.title;
            setCustomTitle(title);
        }
        
        // 能使用JavaScript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // 优先使用缓存
        // 不是用缓存（mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);）
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDomStorageEnabled(true);
        
        
        // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        mWebView.setWebViewClient(new TencentWebViewClient());
        
        // 设置setWebChromeClient对象
        mWebView.setWebChromeClient(new TencentWebChromeClient());
        
        // 设置自定义interface
        // mWebView.addJavascriptInterface(new JumpJavaScriptInterface(), JAVA_INTERFACE_NAME);
        
        // WebView加载web资源
        if (mUrl.startsWith("http")) {
            mWebView.loadUrl(mUrl);
        }
        else {
            // 加载文字资源
            mWebView.loadDataWithBaseURL(null, mUrl, "text/html", "UTF-8", null);
        }
    }
    
    protected boolean shouldOverrideUrlLoading(WebView mWebView, String url) {
        mWebView.loadUrl(url);
        return false;
    }
    
    private class TencentWebChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView mWebView, String title) {
            super.onReceivedTitle(mWebView, title);
            if (!TextUtils.isEmpty(title)) {
                setCustomTitle(title);
            }
        }
        
        @Override
        public void onProgressChanged(WebView mWebView, int i) {
            mPb.setProgress(i);
            if (i >= 100) {
                mPb.setVisibility(View.GONE);
            }
            else {
                mPb.setVisibility(View.VISIBLE);
            }
            super.onProgressChanged(mWebView, i);
        }
        
        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            return super.onConsoleMessage(consoleMessage);
        }
    }
    
    private class TencentWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView mWebView, String url) {
            return H5Activity.this.shouldOverrideUrlLoading(mWebView, url);
        }
        
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }
    
    /**
     * 覆盖titlebar返回按钮返回WebView的上一页面
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
            return;
        }
        super.onClick(v);
    }
    
    /**
     * 覆盖手机返回按钮，返回WebView的上一页面
     */
    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        }
        else {
            super.onBackPressed();
        }
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        mWebView.onResume();
    }
    
    @Override
    public void onPause() {
        super.onPause();
        mWebView.onPause();
    }
    
    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            ViewGroup parent = (ViewGroup) mWebView.getParent();
            if (parent != null) {
                parent.removeView(mWebView);
            }
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }
    
    public static class H5Request implements Serializable {
        
        public String title;
        public String url;
        
        public H5Request() {
        
        }
        
        public H5Request(String title, String url) {
            this.title = title;
            this.url = url;
        }
    }
    
    public static void go(Context context, H5Request request) {
        Intent intent = new Intent(context, H5Activity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_DATA, request);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}