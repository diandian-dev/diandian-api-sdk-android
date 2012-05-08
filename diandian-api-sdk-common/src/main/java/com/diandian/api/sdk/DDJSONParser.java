/**
 * 
 */
package com.diandian.api.sdk;

/**
 * 
 * @author zhangdong zhangdong@diandian.com
 * 
 *         2012-4-18 下午3:10:33
 */
public interface DDJSONParser {

    /**
     * 点点的专用转化器，只转化json中的response部份。
     * 默认实现用的公用包，比较大，可自己实现
     * 
     * @param url
     * @param valueType
     * @return
     */
    public <T> T fromJson2Bean(String json, Class<T> valueType);
}
