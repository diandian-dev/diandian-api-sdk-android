/**
 * 
 */
package com.diandian.api.sdk.android.client;

import android.os.Bundle;

import com.diandian.api.sdk.android.util.PrintLog;

/**
 * 默认的DDListener的实现。不建议使用，因为这个什么都没做。
 * 
 * @author zhangdong zhangdong@diandian.com
 * 
 *         2012-4-18 下午2:25:17
 */
public class DefaultDDListener extends BaseDDListener {

    /* (non-Javadoc)
     * @see com.diandian.api.sdk.android.client.DDListener#onComplete(android.os.Bundle)
     */
    @Override
    public void onComplete(Bundle values) {
        PrintLog.d("DefaultDDListener", values.toString());
    }

}
