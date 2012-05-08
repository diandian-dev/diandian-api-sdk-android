/**
 * 
 */
package com.diandian.api.sdk.android.client;

import android.os.Bundle;

import com.diandian.api.sdk.exception.DDAPIException;

/**
 * DDListener。用于回调
 * 
 * @author zhangdong <zhangdong@diandian.com>
 *         2012-4-10下午4:25:53
 */
public interface DDListener {

    /**
     * 操作成功是调用。数据以String格式存在于values中。
     * 获取方法：
     * String result = values.getString("result");
     * 
     * @param values
     */
    public void onComplete(Bundle values);

    /**
     * 当发生异常时调用
     * 
     * @param e
     */
    public void onDDAPIException(DDAPIException e);

    /**
     * 当发生error时调用。
     */
    public void onError(DDError e);

    /**
     * 当用户取消操作时调用。
     */
    public void onCancel();

}
