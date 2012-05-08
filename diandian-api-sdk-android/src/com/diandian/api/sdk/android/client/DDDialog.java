/**
 * 
 */
package com.diandian.api.sdk.android.client;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.diandian.api.sdk.android.R;
import com.diandian.api.sdk.android.util.AndroidUtil;
import com.diandian.api.sdk.android.util.PrintLog;
import com.diandian.api.sdk.exception.DDAPIException;

/**
 * 点点默认的code认证方式中，默认的dialog.
 * 
 * @author zhangdong <zhangdong@diandian.com>
 *         2012-4-10下午4:24:19
 */
public class DDDialog extends Dialog {

    static final FrameLayout.LayoutParams FILL = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    static final int MARGIN = 4;

    static final int PADDING = 2;

    private final DDClient ddClient;

    private final String mUrl;

    private final DDListener mListener;

    private ProgressDialog mSpinner;

    private WebView mWebView;

    private RelativeLayout webViewContainer;

    private RelativeLayout mContent;

    private final static String TAG = "DD-WebView";

    private final static int DEFAULT_OAUTH_ERR_CODE = 400;

    public DDDialog(final DDClient client, Context context, String url, DDListener listener) {
        super(context, R.style.ContentOverlay);
        this.mListener = listener;
        this.ddClient = client;
        this.mUrl = url;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSpinner = new ProgressDialog(getContext());
        mSpinner.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mSpinner.setMessage("Loading...");

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mContent = new RelativeLayout(getContext());
        setUpWebView();
        addContentView(mContent, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
    }

    private void setUpWebView() {
        webViewContainer = new RelativeLayout(getContext());
        mWebView = new WebView(getContext());
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new DDDialog.DDWebViewClient());
        mWebView.loadUrl(mUrl);
        mWebView.setLayoutParams(FILL);
        mWebView.setVisibility(View.INVISIBLE);
        webViewContainer.addView(mWebView);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        Resources resources = getContext().getResources();
        lp.leftMargin = resources.getDimensionPixelSize(R.dimen.dialog_left_margin);
        lp.topMargin = resources.getDimensionPixelSize(R.dimen.dialog_top_margin);
        lp.rightMargin = resources.getDimensionPixelSize(R.dimen.dialog_right_margin);
        lp.bottomMargin = resources.getDimensionPixelSize(R.dimen.dialog_bottom_margin);
        mContent.addView(webViewContainer, lp);
    }

    private class DDWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            PrintLog.d(TAG, "Redirect URL: " + url);
            // 待后台增加对默认重定向地址的支持后修改下面的逻辑
            if (url.startsWith(ddClient.getRedirectUri())) {
                // handleRedirectUrl(view, url);
                DDDialog.this.dismiss();
                return true;
            } else return false;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description,
                String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            mListener.onError(new DDError(description, errorCode, failingUrl));
            DDDialog.this.dismiss();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            PrintLog.d(TAG, "onPageStarted URL: " + url);
            if (url.startsWith(ddClient.getRedirectUri())) {
                handleRedirectUrl(view, url);
                view.stopLoading();
                DDDialog.this.dismiss();
                return;
            }
            super.onPageStarted(view, url, favicon);
            mSpinner.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            PrintLog.d(TAG, "onPageFinished URL: " + url);
            super.onPageFinished(view, url);
            mSpinner.dismiss();
            mContent.setBackgroundColor(Color.TRANSPARENT);
            mWebView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

    }

    private void handleRedirectUrl(WebView view, String url) {
        Bundle values = AndroidUtil.parseUrl(url);
        String error = values.getString("error");
        String error_code = values.getString("error_code");
        if (error == null && error_code == null) {
            mListener.onComplete(values);
        } else if (error.equals("access_denied")) {
            // 用户或授权服务器拒绝授予数据访问权限
            mListener.onCancel();
        } else {
            mListener.onDDAPIException(new DDAPIException(DEFAULT_OAUTH_ERR_CODE,
                    "undefine err.url:" + url, null));
        }
    }

}
