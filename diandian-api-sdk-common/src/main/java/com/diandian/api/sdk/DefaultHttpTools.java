/**
 * 
 */
package com.diandian.api.sdk;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import com.diandian.api.sdk.exception.DDAPIException;
import com.diandian.api.sdk.util.BaseUtil;

/**
 * 默认的HttpTools的实现
 * 
 * @author zhangdong zhangdong@diandian.com
 * 
 *         2012-4-27 上午11:10:36
 */
public class DefaultHttpTools extends DDHttpTools {

    /* (non-Javadoc)
     * @see com.diandian.api.sdk.DDHttpTools#openUrl(java.lang.String, java.lang.String, java.util.Map, com.diandian.api.sdk.Token)
     */
    @Override
    public String openUrl(String url, String method, Map<String, String> params, Token token) {
        if (method.equals("GET") && params != null) {
            url = url + "?" + encodeUrl(params);
        }
        String response = "";
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestProperty("User-Agent", USER_AGENT);
            if (token != null) {
                conn.setRequestProperty("Authorization", "bearer " + token.getAccessToken());
            }
            if (!method.equals("GET")) {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.getOutputStream().write(encodeUrl(params).getBytes("UTF-8"));
            }

            InputStream is = null;
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                is = conn.getInputStream();
            } else {
                is = conn.getErrorStream();
            }
            response = BaseUtil.read(is);
            if (responseCode != 200) {
                throw new DDAPIException(responseCode, response, null);
            }
        } catch (Exception e) {
            if (e instanceof DDAPIException) {
                DDAPIException dd = (DDAPIException) e;
                throw new DDAPIException(dd.getCode(), dd.getMessage(), dd);
            } else {
                throw new DDAPIException(400, e.getMessage(), e);
            }
        }
        return response;
    }

    /* (non-Javadoc)
     * @see com.diandian.api.sdk.DDHttpTools#uploadFile(java.lang.String, java.util.Map, java.lang.String, java.lang.String, byte[], com.diandian.api.sdk.Token)
     */
    @Override
    public String uploadFile(String reqUrl, Map<String, String> parameters, String fileParamName,
            String filename, byte[] data, Token token) {
        HttpURLConnection urlConn = null;
        try {
            urlConn = sendFormdata(reqUrl, parameters, fileParamName, filename,
                    BaseUtil.parseContentType(filename), data, token);
            String responseContent = BaseUtil.read(urlConn.getInputStream());
            return responseContent.trim();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if (urlConn != null) {
                urlConn.disconnect();
            }
        }
    }

    public static HttpURLConnection sendFormdata(String reqUrl, Map<String, String> parameters,
            String fileParamName, String filename, String contentType, byte[] data, Token token) {
        HttpURLConnection urlConn = null;
        try {
            URL url = new URL(reqUrl);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("POST");
            urlConn.setConnectTimeout(5000);// （单位：毫秒）jdk
            urlConn.setReadTimeout(5000);// （单位：毫秒）jdk 1.5换成这个,读操作超时
            urlConn.setDoOutput(true);
            urlConn.setRequestProperty("connection", "keep-alive");
            urlConn.setRequestProperty("User-Agent", USER_AGENT);
            if (token == null) {
                throw new DDAPIException(401, "请先登陆", null);
            }
            urlConn.setRequestProperty("Authorization", "bearer " + token.getAccessToken());
            String boundary = "-----------------------------114975832116442893661388290519"; // 分隔符
            urlConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            boundary = "--" + boundary;
            StringBuffer params = new StringBuffer();
            if (parameters != null) {
                for (Entry<String, String> entry : parameters.entrySet()) {
                    String name = entry.getKey();
                    String value = entry.getValue();
                    params.append(boundary + "\r\n");
                    params.append("Content-Disposition: form-data; name=\"" + name + "\"\r\n\r\n");
                    // params.append(URLEncoder.encode(value, "UTF-8"));
                    params.append(value);
                    params.append("\r\n");
                }
            }

            StringBuilder sb = new StringBuilder();
            sb.append(boundary);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data; name=\"" + fileParamName + "\"; filename=\""
                    + filename + "\"\r\n");
            sb.append("Content-Type: " + contentType + "\r\n\r\n");
            byte[] fileDiv = sb.toString().getBytes();
            byte[] endData = ("\r\n" + boundary + "--\r\n").getBytes();
            byte[] ps = params.toString().getBytes();

            OutputStream os = urlConn.getOutputStream();
            os.write(ps);
            os.write(fileDiv);
            os.write(data);
            os.write(endData);
            os.flush();
            os.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return urlConn;
    }

}
