/**
 * 
 */
package com.diandian.api.sdk;

import com.alibaba.fastjson.JSON;

/**
 * 默认的DDJSONParser的实现。用的fastJson实现的
 * 
 * @author zhangdong zhangdong@diandian.com
 * 
 *         2012-4-18 下午4:05:53
 */
public class DefaultJsonParser implements DDJSONParser {

    /* (non-Javadoc)
     * @see com.diandian.api.sdk.android.util.DDJSONParser#fromJson2Bean(java.lang.String, java.lang.Class)
     */
    @Override
    public <T> T fromJson2Bean(String json, Class<T> valueType) {
        return JSON.parseObject(json, valueType);
    }

}
