/**
 * 
 */
package com.diandian.api.sdk.android.client;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.diandian.api.sdk.DDJSONParser;
import com.diandian.api.sdk.android.util.PrintLog;
import com.diandian.api.sdk.exception.DDAPIException;
import com.diandian.api.sdk.model.LinkPostInfo;
import com.diandian.api.sdk.model.PhotoPostInfo;
import com.diandian.api.sdk.model.PostBaseInfo;
import com.diandian.api.sdk.model.TextPostInfo;
import com.diandian.api.sdk.model.UserAudioPostInfo;
import com.diandian.api.sdk.model.VideoPostInfo;
import com.diandian.api.sdk.model.XiamiAudioPostInfo;
import com.diandian.api.sdk.view.DashboardView;
import com.diandian.api.sdk.view.LikesPostView;
import com.diandian.api.sdk.view.PostView;

/**
 * 点点网专用的json to bean工厂。须传入DDJSONParser。
 * 
 * @author zhangdong zhangdong@diandian.com
 *         2012-4-18 下午3:12:16
 */
public class DDJSON2BeanFactory {

    /**
     * 将json串转化为相应的Bean。
     * 
     * @param json
     * @param valueType
     * @param parser
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T fromJson2Bean(String json, Class<T> valueType, DDJSONParser parser) {
        String response = getValue(json, "response");
        if (response == null || response.length() <= 0) {
            String meta = getValue(json, "meta");
            String code = getValue(meta, "status");
            String msg = getValue(meta, "msg");
            throw new DDAPIException(Integer.valueOf(code), msg, null);
        }
        T t = parser.fromJson2Bean(response, valueType);
        //如果是DashboardView,PostView,LikesPostView,则需要对分析数据进行转化
        if (t instanceof DashboardView) {
            return (T) fillPosts((DashboardView) t, response, parser);
        }
        if (t instanceof PostView) {
            return (T) fillPosts((PostView) t, response, parser);
        }
        if (t instanceof LikesPostView) {
            return (T) fillPosts((LikesPostView) t, response, parser);
        }
        return t;
    }

    private String getValue(String jsonStr, String valueName) {
        try {
            JSONObject json = new JSONObject(jsonStr);
            if (!json.isNull(valueName)) {
                return json.getString(valueName).toString();
            } else {
                return null;
            }
        } catch (JSONException e) {
            throw new DDAPIException(400, e.getMessage(), e);
        }
    }

    private DashboardView fillPosts(DashboardView d, String response, DDJSONParser parser) {
        d.setPosts(fillPostBase(getValue(response, "posts"), parser));
        return d;
    }

    private PostView fillPosts(PostView p, String response, DDJSONParser parser) {
        p.setPosts(fillPostBase(getValue(response, "posts"), parser));
        return p;
    }

    private LikesPostView fillPosts(LikesPostView l, String response, DDJSONParser parser) {
        l.setLikedPost(fillPostBase(getValue(response, "likedPost"), parser));
        return l;
    }

    private List<PostBaseInfo> fillPostBase(String posts, DDJSONParser parser) {
        List<PostBaseInfo> list = new ArrayList<PostBaseInfo>();
        try {
            JSONArray json = new JSONArray(posts);
            int length = json.length();
            for (int i = 0; i < length; i++) {
                JSONObject j = (JSONObject) json.get(i);
                PostBaseInfo p = null;
                String type = (String) j.get("type");
                if ("video".equalsIgnoreCase(type)) {
                    p = parser.fromJson2Bean(j.toString(), VideoPostInfo.class);
                } else if ("link".equalsIgnoreCase(type)) {
                    p = parser.fromJson2Bean(j.toString(), LinkPostInfo.class);
                } else if ("text".equalsIgnoreCase(type)) {
                    p = parser.fromJson2Bean(j.toString(), TextPostInfo.class);
                } else if ("photo".equalsIgnoreCase(type)) {
                    p = parser.fromJson2Bean(j.toString(), PhotoPostInfo.class);
                } else if ("audio".equalsIgnoreCase(type)) {
                    String audioType = (String) j.get("audioType");
                    if ("userAudio".equalsIgnoreCase(audioType)) {
                        p = parser.fromJson2Bean(j.toString(), UserAudioPostInfo.class);
                    } else {
                        p = parser.fromJson2Bean(j.toString(), XiamiAudioPostInfo.class);
                    }
                }
                if (p != null) {
                    PrintLog.d("DDJSON2BeanFactory", type);
                    list.add(p);
                }
            }
        } catch (JSONException e) {
            PrintLog.e("DDJSON2BeanFactory", e.getMessage());
            throw new DDAPIException(400, e.getMessage(), e);
        }
        return list;
    }
}
