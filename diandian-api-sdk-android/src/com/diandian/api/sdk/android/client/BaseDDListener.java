/**
 * 
 */
package com.diandian.api.sdk.android.client;

import com.diandian.api.sdk.android.util.PrintLog;
import com.diandian.api.sdk.exception.DDAPIException;

/**
 * 
 * 此类implements DDListener并实现了简单的onError,onCancel和onDDAPIException。
 * 继承此类可以只关注onComplete方法。
 * 
 * @author zhangdong zhangdong@diandian.com
 *         2012-4-16 下午1:55:14
 */
public abstract class BaseDDListener implements DDListener {

    private static final String LOG_TAG = "BaseDDListener";

    /**
     * 简单的onError，只把信息写到log中去了。
     */
    @Override
    public void onError(DDError e) {
        PrintLog.e(LOG_TAG, e.getMessage(), e);
    }

    /**
     * 简单的onCancel，只把信息写到log中去了。
     */

    @Override
    public void onCancel() {
        PrintLog.e(LOG_TAG, "cancel");
    }

    /**
     * 简单的onDDAPIException，只把信息写到log中去了。
     */

    @Override
    public void onDDAPIException(DDAPIException e) {
        PrintLog.d(LOG_TAG, e.getMessage(), e);
    }

}
