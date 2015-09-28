# 5秒SDK（Android）接入说明文档

#### 1. 快速接入流程
* 只需4步，快速完成接入
![快速接入流程][quick-import]

#### 2. 接入指南
##### 2.1. 导入5秒轻游戏SDK Jar包
  * 下载[5MiaoSDK Jar][sdk-jar];
  * 在IDE中导入Jar包。各IDE导入Jar包请点击如下连接
    * [Android Studio][android-studio]
    * [Eclipse][eclipse]

##### 2.2. AndroidManifest配置

* AppId和分发渠道配置

```xml
<application>
    <!--在application标签内插入WM_APPID、WM_CHANNEL两个meta-data标签-->
    <!--通过开放平台创建应用获取-->
    <meta-data
        android:name="WM_APPID"
        android:value="54866deefd98c55332000cc7" />
    <!--分发渠道号-->
    <meta-data
        android:name="WM_CHANNEL"
        android:value="wumiao01" />
</application>
```

* 权限配置

```xml
<manifest>
    <!--在manifest标签内插入5秒轻游戏SDK使用到的必要权限-->
    <!--允许访问网络状态-->
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <!--允许使用网络-->
    <uses-permission android:name="android.permission.INTERNET" />
    <!--允许读取设备状态-->
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
</manifest>
```

##### 2.3. SDK初始化

调用初始化接口是调用其他接口的前置条件，否则调用其他接口时将抛出RuntimException。  
初始化接口建议在程序入口处调用，例如：Application或者入口Activity的onCreate。  
接入的示例代码如下：

```java
  /***** 调用示例 *****/
  public class MainActivity extends Activity {

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.acitvity_main);

          String appKey = ...  // appKey通过开放平台创建应用获取。为保证安全，建议将appKey存到合作商服务器。
          WM.getInstance().init(this, appKey); /*** 接入处 ***/
      }
  }
```

##### 2.4. WebView载入“5秒轻游戏”网站

* WebView配置建议

```java

public class SampleActivity extends Activity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 一些合作商App代码
        mWebView = (WebView) findViewById(R.id.web_view);

        setupWebView(mWebView);
    }

    /********** 以下是WebView的建议配置 **********/
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
                return true;
            }
        });
    }

    /********** 为了更好的体验，请让WebView支持后退，拦截BACK键 **********/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mWebView.destroy(); // 销毁WebView，避免一些线程仍然在后台运行
    }
}

```

* 载入网站

```java

/***** 调用示例 *****/
public class SampleActivity extends Activity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 一些合作商App代码
        mWebView = (WebView) findViewById(R.id.web_view);

        setupWebView(mWebView); // 见上述“WebView配置建议”

        // 载入“5秒轻游戏”网站 - 情况1：采用默认配置
        WM.getInstance().loadSite(mWebView, new DefaultThirdParty());

        // 载入“5秒轻游戏”网站 - 情况2：根据合作方应用的需求进行定制，选择覆盖DefaultThirdParty的对应方法即可
        WM.getInstance().loadSite(mWebView, new DefaultThirdParty() {

            /**
             * 查询是否支持调用合作商应用的登录页面，帐号绑定的前提。
             *
             * @return 是否支持登录。如果支持，请覆盖login和getProfile方法。
             */
            @Override
            public boolean isLoginSupported() {
                return true;
            }

            /**
             * 调用第合作商应用的登录页面，前提是isLoginSupported返回true。
             * 注意：用户进行登录后，其结果请调用WM.getInstance().notifyLoginResult()通知SDK，以便刷新5秒轻游戏的登录状态
             *
             */
            @Override
            public void login() {

            }

   			/**
   			 * 获取合作商的账户信息。
   			 *
   			 * @return 账户信息的对象，返回的对象中必须包含一下字段
   			 *   uid[string]: 当返回的整个对象为null或该字段为空时，视为未登录
   			 *   name[string]: 用户名字或昵称
   			 *   avator[string]: 用户头像url
   			 *   未登录情况下，返回null或空Bundle即可。
   			 *   注意：合作商的用户登出账户后，请确认确保此处返回null或空Bundle。
   			 */
            @Override
            public Bundle getProfile() {
                return null;
            }

            /**
             * 分享。游戏内分享时，支持调用合作商的分享页面
             * @param shareInfo 分享的信息
             * @return 是否处理了分享。
             */
            @Override
            public boolean share(final Bundle shareInfo) {

                return false;
            }
        });
    }
}

```

* 退出网站

```java
    @Override
    protected void onDestroy() {
        super.onDestroy();
        WM.getInstance().unloadSite(); // 在WebView所在的Activity销毁后，退出网站
    }
```

##### 2.5. 混淆配置
如果要混淆java代码，请不要混淆5秒轻游戏中的类。可以添加以下类到proguard配置，排除在混淆之外：
```java
-keep class com.wumiao.sdk.** {*;}
```

[quick-import]: README/quick-import.png "快速接入"
[sdk-jar]: https://github.com/FishballInteractive/5MiaoSDK-Android/tree/master/lib/
[android-studio]: http://www.cnblogs.com/neozhu/p/3458759.html
[eclipse]: http://jingyan.baidu.com/article/466506580baf2ef549e5f8e8.html