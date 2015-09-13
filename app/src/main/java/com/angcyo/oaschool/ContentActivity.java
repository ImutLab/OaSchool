package com.angcyo.oaschool;

import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by angcyo on 15-09-13-013.
 */
public class ContentActivity extends BaseActivity {
    public static String KEY_ID = "key_id";
    WebView webView;
    private int id = 1;//文章的id

    @Override
    protected void initView() {
        id = Integer.valueOf(getIntent().getStringExtra(KEY_ID));
        getWindow().setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);

//        webView = createWebView();
//        webView.setFitsSystemWindows(true);
        setContentView(R.layout.activity_content);
        webView = (WebView) findViewById(R.id.webview);
        initWebView(webView);
        initWindow(R.color.action_bar_bg);
    }

    @Override
    protected void initAfter() {
        webView.loadUrl(getContentUrl());
        webView.zoomOut();
    }

    private WebView initWebView(WebView webView) {
//        WebView webView = new WebView(this);
        webView.getSettings().setDefaultTextEncodingName("gbk");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);

        webView.getSettings().setBuiltInZoomControls(true);//支持缩放手势
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setDisplayZoomControls(false);//不显示缩放控件
//        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        //缩放网页,以便显示整个网页
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
//        webView.setInitialScale(1);

        webView.getSettings().setUserAgentString("Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_4; zh-tw) AppleWebKit/533.16 (KHTML, like Gecko) Version/5.0 Safari/533.16");

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                setTitle("Loading...");
                setProgress(progress * 100); //Make the bar disappear after URL is loaded

                if (progress == 100)
                    setTitle(R.string.app_name);
            }
        });

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                setTitle(view.getTitle());
                setTitle("用户:" + OaApplication.getUserInfo().tname);
            }

        });
        return webView;
    }

    private String getContentUrl() {
        String url = String.format("http://120.197.25.113:8123/APP/TongZhiview.asp?APPID=%s&id=%d", OaApplication.getUserInfo().appid, id);
        return url;
    }
}
