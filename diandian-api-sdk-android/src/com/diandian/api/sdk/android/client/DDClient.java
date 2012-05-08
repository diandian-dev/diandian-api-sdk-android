/**
 * 
 */
package com.diandian.api.sdk.android.client;

import org.json.JSONException;
import org.json.JSONObject;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieSyncManager;

import com.diandian.api.sdk.DDHttpTools;
import com.diandian.api.sdk.Token;
import com.diandian.api.sdk.android.configuration.DDAPIConstants;
import com.diandian.api.sdk.android.util.AndroidUtil;
import com.diandian.api.sdk.android.util.PrintLog;
import com.diandian.api.sdk.exception.DDAPIException;

/**
 * DDClient,负责基础功能。
 * 当DDClientInvoker中没有相应的业务支持时，可用此类实现
 * 
 * @author zhangdong <zhangdong@diandian.com>
 *         2012-4-9下午4:37:45
 */
public class DDClient {

    static {
        stick();
    }

    private Token token = null;

    private String appKey;

    private String appSecret;

    private String userName = null;

    private String userPassword = null;

    private final String URL_AUTHORIZE = DDAPIConstants.HOST + "oauth/authorize";

    private final String URL_OAUTH_TOKEN = DDAPIConstants.HOST + "oauth/token";

    private String REDIRECT_URI;

    /**
     * 默认权限
     */
    private final String[] DEFAULT_PERMISSIONS = { "read", "write" };

    private DDListener ddListener;

    private static String LOG_TAG = "DDClient";

    /**
     * HttpTools主要负责Http相关的操作。
     */
    private DDHttpTools ddHttpTools;

    /**
     * 
     * @param appKey
     * @param appSecret
     * @param redirectUri
     */
    public DDClient(String appKey, String appSecret, String redirectUri) {
        if (TextUtils.isEmpty(appKey)) {
            throw new DDAPIException(DDAPIConstants.INVALID_CLIENT_KEY, "invalid appKey", null);
        }
        if (TextUtils.isEmpty(appSecret)) {
            throw new DDAPIException(DDAPIConstants.INVALID_CLIENT_SECRET, "invalid appSecret",
                    null);
        }
        if (TextUtils.isEmpty(redirectUri)) {
            throw new DDAPIException(DDAPIConstants.INVALID_CLIENT_REDIRECT_URI,
                    "invalid redirectUri", null);
        }
        this.setAppKey(appKey);
        this.setAppSecret(appSecret);
        this.setRedirectUri(redirectUri);
    }

    /**
     * 获取code认证方式的url。
     * 
     * @return
     */
    public String getOauthUrl() {
        return URL_AUTHORIZE + "client_id=" + appKey + "&response_type=code";
    }

    /**
     * 默认的code认证方式。
     * 
     * @param code
     * @param listener
     */
    public void initAccessTokenByCode(String code, DDListener listener) {
        DDParameters param = new DDParameters();
        param.add("client_id", this.getAppKey());
        param.add("client_secret", this.getAppSecret());
        param.add("grant_type", "authorization_code");
        param.add("code", code);
        param.add("redirect_uri", REDIRECT_URI);
        this.initAccessToken(param, listener);
    }

    /**
     * 默认的password认证方式。
     * 
     * @param userName
     * @param userPassword
     * @param listener
     */

    public void initAccessTokenByPassword(String userName, String userPassword, DDListener listener) {
        DDParameters param = new DDParameters();
        param.add("client_id", this.getAppKey());
        param.add("client_secret", this.getAppSecret());
        param.add("grant_type", "password");
        param.add("username", userName);
        param.add("password", userPassword);
        param.add("scope", TextUtils.join(",", DEFAULT_PERMISSIONS));
        this.initAccessToken(param, listener);
    }

    /**
     * refreshToken
     * token过期后使用
     * 
     * @param listener
     */
    public void refreshToken(DDListener listener) {
        if (token == null) {
            throw new DDAPIException(401, "invalid token", null);
        }
        DDParameters param = new DDParameters();
        param.add("client_id", this.getAppKey());
        param.add("client_secret", this.getAppSecret());
        param.add("grant_type", "refresh_token");
        param.add("refresh_token", token.getRefreshToken());
        param.add("scope", TextUtils.join(",", DEFAULT_PERMISSIONS));
        if (listener == null) {
            listener = new DefaultDDListener();
        }
        this.initAccessToken(param, listener);

    }

    private synchronized void initAccessToken(DDParameters param, DDListener listener) {
        try {
            String url = URL_OAUTH_TOKEN + "?"
                    + DDHttpTools.encodeUrl(AndroidUtil.ddParameters2Map(param));
            //String result = this.getByHttps(url, null, null);
            String result = this.doGet(url, null, null);
            JSONObject json;
            json = new JSONObject(result);
            if (token == null) {
                token = new Token();
            }
            Log.i("result", result);
            token.setAccessToken(json.getString("access_token"));
            token.setRefreshToken(json.getString("refresh_token"));
            token.setExpiresIn(json.getLong("expires_in"));
            token.setScope(json.getString("scope").split(","));
            token.setTokenType(json.getString("token_type"));
            token.setLastUpdateTime(System.currentTimeMillis());
            Bundle bundle = new Bundle();
            bundle.putString("code", "succ");
            listener.onComplete(bundle);
        } catch (JSONException e) {
            e.printStackTrace();
            PrintLog.e("JSONEXCEPTION", e.getMessage(), e);
            listener.onDDAPIException(new DDAPIException(401, e.getMessage(), e));
        } catch (DDAPIException e) {
            PrintLog.e("DDAPIException", e.getMessage(), e);
            listener.onDDAPIException(e);
        }
    }

    /**
     * 判断当前session是否可用。
     * 过期和null皆为不可用
     * 
     * @return
     */
    public boolean isSessionValid() {
        if (token != null) {
            return (!TextUtils.isEmpty(token.getAccessToken()) && (System.currentTimeMillis() < token
                    .getExpiresIn() * 1000 + token.getLastUpdateTime()));
        }
        return false;
    }

    private static void stick() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads()
                .detectDiskWrites().detectNetwork() // or .detectAll() for all detectable problems       
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects()
                .penaltyLog().penaltyDeath().build());
    }

    /**
     * 获取有效的token.
     * 如果token无效，则
     * throw new DDAPIException(DDAPIConstants.INVALID_TOKEN,
     * "invalid token", null);
     * 
     * @return
     */
    public Token getToken() {
        if (this.isSessionValid()) {
            return token;
        } else {
            throw new DDAPIException(DDAPIConstants.INVALID_TOKEN, "invalid token", null);
        }
    }

    /**
     * 获取token.
     * 结果可能为空，可能过期，因为不能直接使用
     * 
     * @return
     */
    public Token getTokenWithOutCheck() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getRedirectUri() {
        return this.REDIRECT_URI;
    }

    public void setRedirectUri(String redirectUri) {
        this.REDIRECT_URI = redirectUri;
    }

    /**
     * User-Agent Flow
     * 
     * @param activity
     * 
     * @param listener
     *        授权结果监听器
     */
    public void authorize(Activity activity, final DDListener listener) {
        authorize(activity, DEFAULT_PERMISSIONS, listener);
    }

    private void authorize(Activity activity, String[] permissions, final DDListener listener) {
        ddListener = listener;
        startDialogAuth(activity, permissions);
    }

    private void startDialogAuth(Activity activity, String[] permissions) {
        DDParameters params = new DDParameters();
        if (permissions.length > 0) {
            params.add("scope", TextUtils.join(",", permissions));
        }
        CookieSyncManager.createInstance(activity);
        dialog(activity, params, new DDListener() {

            @Override
            public void onComplete(Bundle values) {
                CookieSyncManager.getInstance().sync();
                String code = values.getString("code");
                initAccessTokenByCode(code, ddListener);
            }

            @Override
            public void onDDAPIException(DDAPIException e) {
                ddListener.onDDAPIException(e);
            }

            @Override
            public void onError(DDError e) {
                ddListener.onError(e);
            }

            @Override
            public void onCancel() {
                ddListener.onCancel();
            }

        });
    }

    public void dialog(Context context, DDParameters parameters, final DDListener listener) {
        parameters.add("client_id", getAppKey());
        parameters.add("response_type", "code");
        String url = URL_AUTHORIZE + "?"
                + DDHttpTools.encodeUrl(AndroidUtil.ddParameters2Map(parameters));
        if (context.checkCallingOrSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            AndroidUtil.showAlert(context, "Error",
                    "Application requires permission to access the Internet");
        } else {
            new DDDialog(this, context, url, listener).show();
        }

    }

    public String doGet(String url, Token token, DDParameters ddParameters) {
        return ddHttpTools.openUrl(url, "GET", AndroidUtil.ddParameters2Map(ddParameters), token);
    }

    public String doPost(String url, Token token, DDParameters ddParameters) {
        return ddHttpTools.openUrl(url, "POST", AndroidUtil.ddParameters2Map(ddParameters), token);
    }

    public String doUpload(String reqUrl, DDParameters parameters, String fileParamName,
            String filename, byte[] data, Token token) {
        try {
            return ddHttpTools.uploadFile(reqUrl, AndroidUtil.ddParameters2Map(parameters),
                    fileParamName, filename, data, token);

        } catch (Exception e) {
            PrintLog.d(LOG_TAG, e.getMessage(), e);
            throw new DDAPIException(DDAPIConstants.UPLOAD_FAILED, e.getMessage(), e);
        }
    }

    /**
     * @return the ddhttpTools
     */
    public DDHttpTools getDdHttpTools() {
        return ddHttpTools;
    }

    /**
     * @param ddhttpTools the ddhttpTools to set
     */
    public void setDdHttpTools(DDHttpTools ddHttpTools) {
        this.ddHttpTools = ddHttpTools;
    }
}
