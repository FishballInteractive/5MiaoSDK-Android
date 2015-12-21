package com.wumiao.sdk.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wumiao.sdk.DefaultThirdParty;
import com.wumiao.sdk.IThirdParty;
import com.wumiao.sdk.WM;

public class WMActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wm);

        WM.getInstance().init(this, "9dc3cd7420baf4251823421ca1f0d163");

        mWebView = (WebView) findViewById(R.id.webview);
        setupWebView(mWebView);

        // 支持WebView日志打印
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.d("WMWebView", consoleMessage.message());
                return true;
            }
        });

        IThirdParty thirdParty = new DefaultThirdParty() {

            @Override
            public boolean isLoginSupported() {
                return true;
            }

            @Override
            public void login() {
                startActivity(new Intent(WMActivity.this, LoginActivity.class));
            }

            @Override
            public Bundle getProfile() {
                ProfileData.Profile profile = new ProfileData(WMActivity.this).getProfile();
                Bundle profileBundle = new Bundle();
                profileBundle.putString("uid", profile.uid);
                profileBundle.putString("name", profile.name);
                profileBundle.putString("avator", profile.avator);

                return profileBundle;
            }

            @Override
            public boolean share(final Bundle shareInfo) {
                Intent intent = new Intent(WMActivity.this, ShareActivity.class);
                intent.putExtra(ShareActivity.EXTRA_SHARE_CONTENT, shareInfo);
                startActivity(intent);

                return true;
            }

            @Override
            public Intent getWebViewActivityIntent() {
                return new Intent("wumiao.intent.action.sdk.WEB_VIEW_ACTIVITY");
            }
        };

        String url = getIntent().getStringExtra("wumiao.intent.extra.sdk.WEB_VIEW_URL");
        if (TextUtils.isEmpty(url)) {
            WM.getInstance().loadSite(mWebView, thirdParty);
        } else {
            WM.getInstance().loadSite(url, mWebView, thirdParty);
        }
    }

    private void setupWebView(WebView webView) {
        WebSettings webSettings = webView.getSettings();

        // 设置WebView支持JS
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        // 设置WebView支持LocalStorage
        webSettings.setDomStorageEnabled(true);

        // 设置WebView在内部跳转链接
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WM.getInstance().unloadSite();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mWebView.destroy();
    }
}