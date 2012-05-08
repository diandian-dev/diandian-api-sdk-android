/**
 * 
 */
package com.diandian.api.sdk;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import com.diandian.api.sdk.exception.DDAPIException;

/**
 * http工具抽象类，
 * 
 * @author zhangdong zhangdong@diandian.com
 * 
 *         2012-4-27 上午11:06:35
 */
public abstract class DDHttpTools {

    public static final String USER_AGENT = System.getProperties().getProperty("http.agent")
            + " DDAndroidSDK";

    public abstract String openUrl(String url, String method, Map<String, String> params,
            Token token);

    public abstract String uploadFile(String reqUrl, Map<String, String> parameters,
            String fileParamName, String filename, byte[] data, Token token);

    public static String encodeUrl(Map<String, String> parameters) {
        if (parameters == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Entry<String, String> entry : parameters.entrySet()) {
            if (first) {
                first = false;
            } else {
                sb.append("&");
            }
            try {
                sb.append(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                        + URLEncoder.encode(entry.getValue(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new DDAPIException(400, e.getMessage(), e);
            }
        }
        return sb.toString();
    }

}
