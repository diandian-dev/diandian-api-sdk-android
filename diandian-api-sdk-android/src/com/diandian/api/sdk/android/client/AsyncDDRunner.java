/**
 * 
 */
package com.diandian.api.sdk.android.client;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import android.os.Bundle;

import com.diandian.api.sdk.Token;
import com.diandian.api.sdk.android.util.PrintLog;
import com.diandian.api.sdk.exception.DDAPIException;

/**
 * 
 * 异步执行器，功能为新建线程，后台执行并回调相应的接口
 * 2012-4-18 下午4:48:45
 * 
 * @author zhangdong zhangdong@diandian.com
 */
public class AsyncDDRunner {

    private final DDClient ddClient;

    private final Executor pool;

    private final static String LOG_TAG = "AsyncDDRunner";

    public AsyncDDRunner(DDClient ddClient) {
        this.ddClient = ddClient;
        this.pool = Executors.newFixedThreadPool(2);
    }

    /**
     * 异步执行get或post请求。正常完成后回调DDListener.onComplelte(Bundle bundle).
     * 取数据方法为
     * String result = bundle.getString("result");
     * 
     * @param url
     * @param params
     * @param ddListener
     * @param needToken 是否需要认证信息（token。可通过ddClent.getToken()获取）
     * @param method 请求方式。GET或POST
     */
    public void doRequest(final String url, final DDParameters params, final DDListener ddListener,
            final boolean needToken, final String method) {
        PrintLog.d(LOG_TAG, url);
        pool.execute(new Runnable() {

            @Override
            public void run() {
                try {
                    Token token = null;
                    String result;
                    if (needToken) {
                        token = ddClient.getToken();
                    }

                    if ("GET".equalsIgnoreCase(method)) {
                        result = ddClient.doGet(url, token, params);
                    } else {
                        result = ddClient.doPost(url, token, params);
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("result", result);
                    ddListener.onComplete(bundle);
                } catch (DDAPIException e) {
                    ddListener.onDDAPIException(e);
                }
            }
        });
    }

    /**
     * 异步执行上传功能。正常完成后回调DDListener.onComplelte(Bundle bundle).
     * 取数据方法为
     * String result = bundle.getString("result");
     * 
     * @param url
     * @param params
     * @param ddListener
     * @param fileParamName 文件参数名 为"data"
     * @param filename 文件名
     * @param data 文件数据
     * @param needToken
     */

    public void doUpload(final String url, final DDParameters params, final DDListener ddListener,
            final String fileParamName, final String filename, final byte[] data,
            final boolean needToken) {
        pool.execute(new Runnable() {

            @Override
            public void run() {
                try {
                    Token token = null;
                    String result;
                    if (needToken) {
                        token = ddClient.getToken();
                    }
                    result = ddClient.doUpload(url, params, fileParamName, filename, data, token);
                    Bundle bundle = new Bundle();
                    bundle.putString("result", result);
                    ddListener.onComplete(bundle);
                } catch (DDAPIException e) {
                    ddListener.onDDAPIException(e);
                }
            }
        });

    }

}
