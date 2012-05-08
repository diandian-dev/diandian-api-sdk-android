/**
 * 
 */
package com.diandian.api.sdk.android.util;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog.Builder;
import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import com.diandian.api.sdk.android.client.DDParameters;
import com.diandian.api.sdk.android.configuration.DDAPIConstants;
import com.diandian.api.sdk.exception.DDAPIException;

/**
 * android的基本工具
 * 
 * @author zhangdong zhangdong@diandian.com
 * 
 *         2012-4-23 上午10:42:39
 */
public class AndroidUtil {

    //***********************网络连接方式*************************//
    /**
     * 网络连接：-1 UNKNOWN
     */
    public static final int NETWORK_UNKNOWN = -1;

    /**
     * 网络连接：-1 UNKNOWN
     */
    public static final int NETWORK_NONE = 0;

    /**
     * 网络连接：0 WIFI
     */
    public static final int NETWORK_WIFI = 1;

    /**
     * 网络连接：1 MOBILE
     */
    public static final int NETWORK_MOBILE = 2;

    /**
     * 网络连接：2 2G
     */
    public static final int NETWORK_2G = 3;

    /**
     * 网络连接：3 3G
     */
    public static final int NETWORK_3G = 4;

    /**
     * 将url转化为Bundle。主要用于http://www.diandian.com?a=xxx&b=xxx的情况
     * 
     * @param url
     * @return
     */
    public static Bundle parseUrl(String url) {
        try {
            URL u = new URL(url);
            Bundle b = decodeUrl(u.getQuery());
            b.putAll(decodeUrl(u.getRef()));
            return b;
        } catch (MalformedURLException e) {
            return new Bundle();
        }
    }

    public static Bundle decodeUrl(String s) {
        Bundle params = new Bundle();
        if (s != null) {
            String array[] = s.split("&");
            for (String parameter : array) {
                String v[] = parameter.split("=");
                try {
                    params.putString(URLDecoder.decode(v[0], "UTF-8"),
                            URLDecoder.decode(v[1], "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    throw new DDAPIException(DDAPIConstants.DEFAULT_ERR_CODE, e.getMessage(), e);
                }
            }
        }
        return params;
    }

    /**
     * 警告栏
     * 
     * @param context
     * @param title
     * @param text
     */
    public static void showAlert(Context context, String title, String text) {
        Builder alertBuilder = new Builder(context);
        alertBuilder.setTitle(title);
        alertBuilder.setMessage(text);
        alertBuilder.create().show();
    }

    /**
     * 将DDParameters转化为Map。方便低层调用
     * 
     * @param param
     * @return
     */
    public static Map<String, String> ddParameters2Map(DDParameters param) {
        if (param == null) {
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < param.size(); i++) {
            map.put(param.getKey(i), param.getValue(i));
        }
        return map;
    }

    /**
     * 获取网页图片
     * 
     * @param url
     * @return BitMap
     */
    public static Bitmap getHttpBitmap(String url) {
        URL myFileURL;
        Bitmap bitmap = null;
        try {
            myFileURL = new URL(url);
            //获得连接
            HttpURLConnection conn = (HttpURLConnection) myFileURL.openConnection();
            //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout(6000);
            //连接设置获得数据流
            conn.setDoInput(true);
            //不使用缓存
            conn.setUseCaches(false);
            //这句可有可无，没有影响
            conn.connect();
            //得到数据流
            InputStream is = conn.getInputStream();
            String location = conn.getHeaderField("Location");
            if (location != null) {
                return getHttpBitmap(conn.getHeaderField("Location"));
            }
            //解析得到图片
            bitmap = BitmapFactory.decodeStream(is);
            //关闭数据流
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 获取网络类型：-1、未知，0、无网络，1、WiFi，2、移动网络，3、2G（移动网络），4、3G（移动网络）
     * 
     * */

    public static int getNetworkType(Context context) {

        int networkType = NETWORK_UNKNOWN;

        ConnectivityManager mConnectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // 区分是WIFI网络还是移动手机网络
        NetworkInfo info = mConnectivity.getActiveNetworkInfo();

        if ((info != null) && info.isAvailable()) {
            if (info.getTypeName().toLowerCase().equals("wifi")) {
                networkType = NETWORK_WIFI;
            } else if (info.getTypeName().toLowerCase().equals("mobile")) {
                networkType = NETWORK_MOBILE;
                // 然后根据TelephonyManager来获取网络类型，判断当前是2G还是3G网络
                TelephonyManager mTelephonyManager = (TelephonyManager) context
                        .getSystemService(Service.TELEPHONY_SERVICE);
                int netType = mTelephonyManager.getNetworkType();
                if (netType == TelephonyManager.NETWORK_TYPE_UNKNOWN) {
                    networkType = NETWORK_UNKNOWN;
                }
                if (netType == TelephonyManager.NETWORK_TYPE_GPRS
                        || netType == TelephonyManager.NETWORK_TYPE_EDGE) {
                    networkType = NETWORK_2G;
                } else {
                    networkType = NETWORK_3G;
                }
            }
        } else {
            networkType = NETWORK_NONE;
        }
        return networkType;
    }

}
