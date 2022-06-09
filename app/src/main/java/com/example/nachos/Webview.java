package com.example.nachos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Webview extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        Intent intentWebView = getIntent();
        String url = intentWebView.getStringExtra("url");

        webView = findViewById(R.id.webView);
        webView.setBackgroundColor(0x00000000);
        webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        webView.getSettings().setJavaScriptEnabled(true); // 자바스크립트 허용
        webView.getSettings().setSupportMultipleWindows(false); // 여러 창 또는 탭 열리는 것 비허용
        webView.getSettings().setLoadWithOverviewMode(true); // 페이지 내에서만 이동하게끔
        webView.getSettings().setUseWideViewPort(true); // 페이지를 웹뷰 width에 맞춤
        webView.getSettings().setSupportZoom(false); // 확대 비활성화
        webView.getSettings().setBuiltInZoomControls(false); // 확대 비활성화
        webView.getSettings().setCacheMode(webView.getSettings().LOAD_NO_CACHE); // 캐시 사용안함 (매번 새로 로딩)
        webView.getSettings().setDomStorageEnabled(true); // 로컬스토리지 사용 허용
        webView.setWebChromeClient(new WebChromeClient()); // 웹뷰에 크롬 사용 허용 // 이 부분이 없으면 크롬에서 alert가 뜨지 않음
        // 새창열기 없이 웹뷰 내에서 다시 열기 // 페이지 이동 원활히 하기위해 사용
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        System.out.println("주소입니다 : " + url);
        webView.loadDataWithBaseURL(null, url, "text/html; charset=utf-8", "utf-8",null);
        webView.loadUrl(url); // 웹뷰 실행
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 뒤로가기 키를 누를 때 & 웹뷰가 뒤로 갈 수 있는 상태일 때 뒤로 가라는 명령
        if((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return true;
        }
    }
}