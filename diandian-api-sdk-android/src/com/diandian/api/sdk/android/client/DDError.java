package com.diandian.api.sdk.android.client;

/**
 * 
 * @author zhangdong zhangdong@diandian.com
 * 
 *         2012-5-2 下午3:48:13
 */
public class DDError extends Throwable {

    private static final long serialVersionUID = 1L;

    private final int mErrorCode;

    private final String mFailingUrl;

    public DDError(String message, int errorCode, String failingUrl) {
        super(message);
        mErrorCode = errorCode;
        mFailingUrl = failingUrl;
    }

    int getErrorCode() {
        return mErrorCode;
    }

    String getFailingUrl() {
        return mFailingUrl;
    }

}
