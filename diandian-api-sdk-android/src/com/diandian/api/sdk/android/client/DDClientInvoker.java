/**
 * 
 */
package com.diandian.api.sdk.android.client;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.diandian.api.sdk.DDJSONParser;
import com.diandian.api.sdk.Token;
import com.diandian.api.sdk.android.configuration.DDAPIConstants;
import com.diandian.api.sdk.android.util.PrintLog;
import com.diandian.api.sdk.exception.DDAPIException;
import com.diandian.api.sdk.model.BlogDetailInfo;
import com.diandian.api.sdk.model.UserDetailInfo;
import com.diandian.api.sdk.util.BaseUtil;
import com.diandian.api.sdk.view.DashboardView;
import com.diandian.api.sdk.view.FollowersView;
import com.diandian.api.sdk.view.FollowingView;
import com.diandian.api.sdk.view.LikesPostView;
import com.diandian.api.sdk.view.PostView;

/**
 * 对各个业务的封装。通过调用client,anyncDDRunner等底层类实现。
 * 
 * @author zhangdong zhangdong@diandian.com
 *         2012-4-19 上午11:32:09
 * 
 */
public class DDClientInvoker {

    private final DDClient ddClient;

    private final DDJSONParser parser;

    private final AsyncDDRunner asyncDDRunner;

    private final DDJSON2BeanFactory json2BeanFactory;

    private static DDClientInvoker instance;

    private static String LOG_TAG = "DDClientInvoker";

    private static final String GET_HOME_URL = DDAPIConstants.HOST + "v1/user/home";

    private static final String GET_FOLLOWERS_FORMAT = DDAPIConstants.HOST + "v1/blog/%s/followers";

    private static final String GET_BLOG_INFO_FORMAT = DDAPIConstants.HOST + "v1/blog/%s/info";

    private static final String GET_ALL_TYPE_POST_FORMAT = DDAPIConstants.HOST + "v1/blog/%s/posts";

    private static final String GET_ONE_TYPE_POST_FORMAT = GET_ALL_TYPE_POST_FORMAT + "/%s";

    private static final String POST_POST_FORMAT = DDAPIConstants.HOST + "v1/blog/%s/post";

    private static final String EDIT_POST_FORMAT = DDAPIConstants.HOST + "v1/blog/%s/post/edit";

    private static final String REBLOG_POST_FORMAT = DDAPIConstants.HOST + "v1/blog/%s/post/reblog";

    private static final String DELETE_POST_FORMAT = DDAPIConstants.HOST + "v1/blog/%s/post/delete";

    private static final String GET_QUEUE_FORMAT = DDAPIConstants.HOST + "v1/blog/%s/queue";

    private static final String GET_DRAFT_FORMAT = DDAPIConstants.HOST + "v1/blog/%s/draft";

    private static final String GET_SUBMISSION_FORMAT = DDAPIConstants.HOST
            + "v1/blog/%s/submission";

    private static final String GET_USER_INFO_URL = DDAPIConstants.HOST + "v1/user/info";

    private static final String GET_LIKES_URL = DDAPIConstants.HOST + "v1/user/likes";

    private static final String POST_LIKE_URL = DDAPIConstants.HOST + "v1/user/like";

    private static final String DELETE_LIKE_URL = DDAPIConstants.HOST + "v1/user/unlike";

    private static final String GET_FOLLOWING_URL = DDAPIConstants.HOST + "v1/user/following";

    private static final String POST_FOLLOW_URL = DDAPIConstants.HOST + "v1/user/follow";

    private static final String DELETE_FOLLOW_URL = DDAPIConstants.HOST + "v1/user/unfollow";

    /**
     * 初始化。需要DDClient和parser。二者皆不能为空
     * 
     * @param ddClient
     * @param parser
     * @return
     */
    public synchronized static DDClientInvoker init(DDClient ddClient, DDJSONParser parser) {
        instance = new DDClientInvoker(ddClient, parser);
        return instance;
    }

    public synchronized static DDClientInvoker getInstance() {
        if (instance == null) {
            throw new DDAPIException(DDAPIConstants.DDCLIENT_INVOKER_NOT_INIT, "invoker not init",
                    null);
        }
        return instance;
    }

    /**
     * 
     * @param ddClient
     * @param parser
     */
    private DDClientInvoker(DDClient ddClient, DDJSONParser parser) {
        this.ddClient = ddClient;
        this.parser = parser;
        asyncDDRunner = new AsyncDDRunner(ddClient);
        this.json2BeanFactory = new DDJSON2BeanFactory();
    }

    /**
     * 同步获取博客信息
     * 
     * @param blogCName
     * @return BlogDetailInfo
     */
    public BlogDetailInfo getBlogInfo(String blogCName) {
        String url = String.format(GET_BLOG_INFO_FORMAT, blogCName);
        PrintLog.d(LOG_TAG, url);
        return getInfo(url, null, BlogDetailInfo.class, ddClient.getToken());
    }

    /**
     * 异步获取博客信息
     * 
     * @param blogCName
     * @param ddListener
     */
    public void getBlogInfoSync(String blogCName, DDListener ddListener) {
        String url = String.format(GET_BLOG_INFO_FORMAT, blogCName);
        PrintLog.d(LOG_TAG, url);
        asyncDDRunner.doRequest(url, null, ddListener, true, DDAPIConstants.HTTP_GET);
    }

    private DDParameters getParamForLimitAndOffset(int limit, int offset) {
        limit = Math.max(0, limit);
        DDParameters param = new DDParameters();
        param.add("limit", Math.min(limit, DDAPIConstants.MAX_ITEM_PERPAGE) + "");
        param.add("offset", Math.max(0, offset) + "");
        return param;

    }

    /**
     * 获取博客关注者信息
     * 
     * @param blogCName
     * @param limit
     * @param offset
     * @return FollowersView
     */
    public FollowersView getFollowers(String blogCName, int limit, int offset) {
        String url = String.format(GET_FOLLOWERS_FORMAT, blogCName);
        PrintLog.d(LOG_TAG, url);
        return getInfo(url, getParamForLimitAndOffset(limit, offset), FollowersView.class,
                ddClient.getToken());
    }

    /**
     * 异步获取关注者信息。
     * 
     * @param blogCName
     * @param limit
     * @param offset
     * @param ddListener
     */
    public void getFollowers(String blogCName, int limit, int offset, DDListener ddListener) {
        String url = String.format(GET_FOLLOWERS_FORMAT, blogCName);
        limit = Math.max(0, limit);
        PrintLog.d(LOG_TAG, url);
        asyncDDRunner.doRequest(url, getParamForLimitAndOffset(limit, offset), ddListener, true,
                DDAPIConstants.HTTP_GET);
    }

    private DDParameters getParamForPosts(String type, int limit, int offset, String tag,
            boolean reblogInfo, boolean notesInfo, String postId) {
        DDParameters param = new DDParameters();
        if (!TextUtils.isEmpty(postId)) {
            param.add("id", postId);
        } else {
            limit = Math.max(0, limit);
            param.add("limit", Math.min(limit, DDAPIConstants.MAX_ITEM_PERPAGE) + "");
            param.add("offset", Math.max(0, offset) + "");
            param.add("tag", tag);
        }
        param.add("reblogInfo", String.valueOf(reblogInfo));
        param.add("notesInfo", String.valueOf(notesInfo));
        return param;
    }

    private String getPostUrlByType(String type, String blogCName) {
        if (TextUtils.isEmpty(type)) {
            return String.format(GET_ALL_TYPE_POST_FORMAT, blogCName);
        } else {
            return String.format(GET_ONE_TYPE_POST_FORMAT, blogCName, type);
        }
    }

    /**
     * 获取post。
     * 注：postId不为空，则取一post
     * 
     * @param blogCName
     * @param type
     * @param limit
     * @param offset
     * @param tag
     * @param reblogInfo
     * @param notesInfo
     * @param postId
     * @return PostView
     */
    public PostView getPosts(String blogCName, String type, int limit, int offset, String tag,
            boolean reblogInfo, boolean notesInfo, String postId) {
        String url = getPostUrlByType(type, blogCName);
        PrintLog.d(LOG_TAG, url);
        return getInfo(url,
                getParamForPosts(type, limit, offset, tag, notesInfo, notesInfo, postId),
                PostView.class, ddClient.getToken());
    }

    /**
     * 异步获取post.获取后会调用listener.onComplete方法。
     * 可通过bundle.get("result")的方式获取结果
     * 例：
     * public void onComplete(Bundle values) {
     * String result = values.getString("result");
     * Toast.makeText(getApplicationContext(), result,
     * Toast.LENGTH_LONG).show();
     * }
     * 
     * @param blogCName
     * @param type
     * @param limit
     * @param offset
     * @param tag
     * @param reblogInfo
     * @param notesInfo
     * @param postId
     * @param listener
     */

    public void getPosts(String blogCName, String type, int limit, int offset, String tag,
            boolean reblogInfo, boolean notesInfo, String postId, DDListener listener) {
        String url = getPostUrlByType(type, blogCName);
        PrintLog.d(LOG_TAG, url);
        asyncDDRunner.doRequest(url,
                getParamForPosts(type, limit, offset, tag, notesInfo, notesInfo, postId), listener,
                true, DDAPIConstants.HTTP_GET);
    }

    private DDParameters getParamForHome(String type, int limit, int offset, String sinceId,
            boolean reblogInfo, boolean notesInfo) {
        DDParameters param = new DDParameters();
        limit = Math.max(0, limit);
        if (!TextUtils.isEmpty(type)) {
            param.add("type", type);
        }
        param.add("limit", Math.min(limit, DDAPIConstants.MAX_ITEM_PERPAGE) + "");
        param.add("offset", Math.max(offset, 0) + "");
        if (!TextUtils.isEmpty(sinceId)) {
            param.add("sinceId", sinceId);
        }
        param.add("reblogInfo", String.valueOf(reblogInfo));
        param.add("notesInfo", String.valueOf(notesInfo));
        return param;
    }

    /**
     * 获取当前用户的着页信息
     * 
     * @param type
     * @param limit
     * @param offset
     * @param sinceId
     * @param reblogInfo
     * @param notesInfo
     * @return
     */
    public DashboardView getHome(String type, int limit, int offset, String sinceId,
            boolean reblogInfo, boolean notesInfo) {
        PrintLog.d(LOG_TAG, GET_HOME_URL);
        return getInfo(GET_HOME_URL,
                getParamForHome(type, limit, offset, sinceId, reblogInfo, notesInfo),
                DashboardView.class, ddClient.getToken());
    }

    /**
     * 异步获取首页动态
     * 
     * @param type
     * @param limit
     * @param offset
     * @param sinceId
     * @param reblogInfo
     * @param notesInfo
     * @param listener
     */

    public void getHome(String type, int limit, int offset, String sinceId, boolean reblogInfo,
            boolean notesInfo, DDListener listener) {
        asyncDDRunner.doRequest(GET_HOME_URL,
                getParamForHome(type, limit, offset, sinceId, reblogInfo, notesInfo), listener,
                true, DDAPIConstants.HTTP_GET);
    }

    private DDParameters getBaseParamForPost(String type, String state, String tag, String slug) {
        DDParameters param = new DDParameters();
        if (!TextUtils.isEmpty(type)) {
            param.add("type", type);
        }
        if (!TextUtils.isEmpty(state)) {
            param.add("state", state);
        }
        if (!TextUtils.isEmpty(tag)) {
            param.add("tag", tag);
        }
        if (!TextUtils.isEmpty(slug)) {
            param.add("slug", slug);
        }
        return param;
    }

    private DDParameters getTextParamForPost(String type, String state, String tag, String slug,
            String title, String body) {
        DDParameters param = getBaseParamForPost(type, state, tag, slug);
        if (TextUtils.isEmpty(title) && TextUtils.isEmpty(body)) {
            throw new DDAPIException(DDAPIConstants.INVALID_PARAMATERS,
                    "title and body are both null", null);
        }
        if (!TextUtils.isEmpty(title)) {
            param.add("title", title);
        }
        if (!TextUtils.isEmpty(body)) {
            param.add("bode", body);
        }
        return param;
    }

    /**
     * 发布文字
     * 
     * @param blogCName
     * @param state
     * @param tag
     * @param slug
     * @param title
     * @param body
     */
    public void postText(String blogCName, String state, String tag, String slug, String title,
            String body) {
        String url = String.format(POST_POST_FORMAT, blogCName);
        this.doPost(url,
                getTextParamForPost(DDAPIConstants.POST_TEXT, state, tag, slug, title, body),
                ddClient.getToken());

    }

    /**
     * 异步发布文字
     * 
     * @param blogCName
     * @param state
     * @param tag
     * @param slug
     * @param title
     * @param body
     * @param listener
     */
    public void postText(String blogCName, String state, String tag, String slug, String title,
            String body, DDListener listener) {
        String url = String.format(POST_POST_FORMAT, blogCName);
        asyncDDRunner.doRequest(url,
                getTextParamForPost(DDAPIConstants.POST_TEXT, state, tag, slug, title, body),
                listener, true, DDAPIConstants.HTTP_POST);

    }

    private DDParameters getLinkParamForPost(String type, String state, String tag, String slug,
            String title, String url, String description) {
        DDParameters param = getBaseParamForPost(type, state, tag, slug);
        if (TextUtils.isEmpty(url)) {
            throw new DDAPIException(DDAPIConstants.INVALID_PARAMATERS, "url can not be null", null);
        }
        param.add("url", url);
        if (!TextUtils.isEmpty(title)) {
            param.add("title", title);
        }
        if (!TextUtils.isEmpty(description)) {
            param.add("description", description);
        }
        return param;
    }

    /**
     * 发布链接
     * 
     * @param blogCName
     * @param state
     * @param tag
     * @param slug
     * @param title
     * @param url
     * @param description
     */
    public void postLink(String blogCName, String state, String tag, String slug, String title,
            String url, String description) {
        String postUrl = String.format(POST_POST_FORMAT, blogCName);
        this.doPost(postUrl, this.getLinkParamForPost(DDAPIConstants.POST_LINK, state, tag, slug,
                title, postUrl, description), ddClient.getToken());

    }

    /**
     * 异步发布链接
     * 
     * @param blogCName
     * @param state
     * @param tag
     * @param slug
     * @param title
     * @param url
     * @param description
     * @param listener
     */
    public void postLink(String blogCName, String state, String tag, String slug, String title,
            String url, String description, DDListener listener) {
        String postUrl = String.format(POST_POST_FORMAT, blogCName);
        asyncDDRunner.doRequest(postUrl, this.getLinkParamForPost(DDAPIConstants.POST_LINK, state,
                tag, slug, title, postUrl, description), listener, true, DDAPIConstants.HTTP_POST);
    }

    private DDParameters getVideoParamForPost(String type, String state, String tag, String slug,
            String caption, String sourceUrl) {
        DDParameters param = this.getBaseParamForPost(type, state, tag, slug);
        if (TextUtils.isEmpty(sourceUrl)) {
            throw new DDAPIException(DDAPIConstants.INVALID_PARAMATERS,
                    "sourceUrl can not by null", null);
        }
        param.add("sourceUrl", sourceUrl);
        if (!TextUtils.isEmpty(caption)) {
            param.add("caption", caption);
        }
        return param;

    }

    /**
     * 发布视频
     * 
     * @param blogCName
     * @param state
     * @param tag
     * @param slug
     * @param caption
     * @param sourceUrl
     */
    public void postVideo(String blogCName, String state, String tag, String slug, String caption,
            String sourceUrl) {
        String url = String.format(POST_POST_FORMAT, blogCName);
        this.doPost(url, this.getVideoParamForPost(DDAPIConstants.POST_VIDEO, state, tag, slug,
                caption, sourceUrl), ddClient.getToken());

    }

    /**
     * 异步发布视频
     * 
     * @param blogCName
     * @param state
     * @param tag
     * @param slug
     * @param caption
     * @param sourceUrl
     * @param listener
     */
    public void postVideo(String blogCName, String state, String tag, String slug, String caption,
            String sourceUrl, DDListener listener) {
        String url = String.format(POST_POST_FORMAT, blogCName);
        asyncDDRunner.doRequest(url, this.getVideoParamForPost(DDAPIConstants.POST_VIDEO, state,
                tag, slug, caption, sourceUrl), listener, true, DDAPIConstants.HTTP_POST);
    }

    private DDParameters getAudioParamForPost(String type, String state, String tag, String slug,
            String caption, String musicName, String musicSinger) {
        DDParameters param = this.getBaseParamForPost(type, state, tag, slug);
        if (TextUtils.isEmpty(musicName) || TextUtils.isEmpty(musicSinger)) {
            throw new DDAPIException(DDAPIConstants.INVALID_PARAMATERS,
                    "musicName or musicSinger is null.musicName:" + musicName + "  musicSinger:"
                            + musicSinger, null);
        }
        param.add("musicName", musicName);
        param.add("musicSinger", musicSinger);
        if (!TextUtils.isEmpty(caption)) {
            param.add("caption", caption);
        }
        return param;
    }

    /**
     * 发布音乐
     * 
     * @param blogCName
     * @param state
     * @param tag
     * @param slug
     * @param caption
     * @param filePath
     * @param musicName
     * @param musicSinger
     */
    public void postAudio(String blogCName, String state, String tag, String slug, String caption,
            String filePath, String musicName, String musicSinger) {
        byte[] data = BaseUtil.getFileData(new File(filePath));
        String url = String.format(POST_POST_FORMAT, blogCName);
        ddClient.doUpload(url, this.getAudioParamForPost(DDAPIConstants.POST_AUDIO, state, tag,
                slug, caption, musicName, musicSinger), "data", filePath, data, ddClient.getToken());
    }

    /**
     * 异步发布音乐
     * 
     * @param blogCName
     * @param state
     * @param tag
     * @param slug
     * @param caption
     * @param filePath
     * @param musicName
     * @param musicSinger
     * @param listener
     */
    public void postAudio(String blogCName, String state, String tag, String slug, String caption,
            String filePath, String musicName, String musicSinger, DDListener listener) {
        byte[] data = BaseUtil.getFileData(new File(filePath));
        String url = String.format(POST_POST_FORMAT, blogCName);
        asyncDDRunner
                .doUpload(url, this.getAudioParamForPost(DDAPIConstants.POST_AUDIO, state, tag,
                        slug, caption, musicName, musicSinger), listener, "data", filePath, data,
                        true);
    }

    private DDParameters getPhotoParamForPost(String type, String state, String tag, String slug,
            String caption) {
        DDParameters param = this.getBaseParamForPost(type, state, tag, slug);
        if (!TextUtils.isEmpty(caption)) {
            param.add("caption", caption);
        }
        return param;
    }

    /**
     * 发布照片
     * 
     * @param blogCName
     * @param state
     * @param tag
     * @param slug
     * @param caption
     * @param filePath
     */
    public void postPhoto(String blogCName, String state, String tag, String slug, String caption,
            String filePath) {
        byte[] data = BaseUtil.getFileData(new File(filePath));
        String url = String.format(POST_POST_FORMAT, blogCName);
        ddClient.doUpload(url,
                this.getPhotoParamForPost(DDAPIConstants.POST_PHOTO, state, tag, slug, caption),
                "data", filePath, data, ddClient.getToken());

    }

    /**
     * 异步发布照片
     * 
     * @param blogCName
     * @param state
     * @param tag
     * @param slug
     * @param caption
     * @param filePath
     * @param listener
     */
    public void postPhoto(String blogCName, String state, String tag, String slug, String caption,
            String filePath, DDListener listener) {
        byte[] data = BaseUtil.getFileData(new File(filePath));
        String url = String.format(POST_POST_FORMAT, blogCName);
        asyncDDRunner.doUpload(url,
                this.getPhotoParamForPost(DDAPIConstants.POST_PHOTO, state, tag, slug, caption),
                listener, "data", filePath, data, true);
    }

    /**
     * 获取信息
     * 
     * @param url
     * @param params
     * @param classType
     * @param token
     * @return
     */
    public <T> T getInfo(String url, DDParameters params, Class<T> classType, Token token) {
        String result = ddClient.doGet(url, token, params);
        return json2BeanFactory.fromJson2Bean(result, classType, parser);

    }

    /**
     * 发布信息。
     * 异常：DDAPIException
     * 
     * @param url
     * @param params
     * @param token
     */
    public void doPost(String url, DDParameters params, Token token) {
        String result = ddClient.doPost(url, token, params);
        try {
            JSONObject meta = new JSONObject(result).getJSONObject("meta");
            int code = meta.getInt("status");
            if (code != DDAPIConstants.RESULT_OK) {
                throw new DDAPIException(code, meta.getString("msg"), null);
            }
        } catch (JSONException e) {
            PrintLog.e(LOG_TAG, e.getMessage(), e);
            throw new DDAPIException(DDAPIConstants.DEFAULT_ERR_CODE, e.getMessage(), e);
        }

    }

    private DDParameters getEditParam(String type, String state, String tag, String slug,
            String title, String body, String url, String description, String caption,
            String musicName, String musicSinger, String sourceUrl, String id) {

        DDParameters param = null;
        if (DDAPIConstants.POST_AUDIO.equalsIgnoreCase(type)) {
            param = this.getAudioParamForPost(type, state, tag, slug, caption, musicName,
                    musicSinger);
        } else if (DDAPIConstants.POST_LINK.equalsIgnoreCase(type)) {
            param = this.getLinkParamForPost(type, state, tag, slug, title, url, description);
        } else if (DDAPIConstants.POST_PHOTO.equalsIgnoreCase(type)) {
            param = this.getPhotoParamForPost(type, state, tag, slug, caption);
        } else if (DDAPIConstants.POST_TEXT.equalsIgnoreCase(type)) {
            param = this.getTextParamForPost(type, state, tag, slug, title, body);
        } else if (DDAPIConstants.POST_VIDEO.equalsIgnoreCase(type)) {
            param = this.getVideoParamForPost(type, state, tag, slug, caption, sourceUrl);
        }

        if (param == null) {
            throw new DDAPIException(DDAPIConstants.INVALID_PARAMATERS, "invalid type type" + type,
                    null);
        }
        if (TextUtils.isEmpty(id)) {
            throw new DDAPIException(DDAPIConstants.INVALID_PARAMATERS, "id can not be empty"
                    + type, null);
        }
        param.add("id", id);
        return param;
    }

    /**
     * 编辑文字
     * 
     * @param blogCName
     * @param state
     * @param tag
     * @param slug
     * @param title
     * @param body
     * @param id
     */
    public void editText(String blogCName, String state, String tag, String slug, String title,
            String body, String id) {
        String url = String.format(EDIT_POST_FORMAT, blogCName);
        this.doPost(url, this.getEditParam(DDAPIConstants.POST_TEXT, state, tag, slug, title, body,
                url, null, null, null, null, null, id), ddClient.getToken());
    }

    /**
     * 异步编辑文字
     * 
     * @param blogCName
     * @param state
     * @param tag
     * @param slug
     * @param title
     * @param body
     * @param id
     * @param listener
     */
    public void editText(String blogCName, String state, String tag, String slug, String title,
            String body, String id, DDListener listener) {
        String url = String.format(EDIT_POST_FORMAT, blogCName);
        asyncDDRunner.doRequest(url, this.getEditParam(DDAPIConstants.POST_TEXT, state, tag, slug,
                title, body, url, null, null, null, null, null, id), listener, true,
                DDAPIConstants.HTTP_POST);
    }

    /**
     * 编辑链接
     * 
     * @param blogCName
     * @param state
     * @param tag
     * @param slug
     * @param title
     * @param url
     * @param description
     * @param id
     */
    public void editLink(String blogCName, String state, String tag, String slug, String title,
            String url, String description, String id) {
        this.doPost(String.format(EDIT_POST_FORMAT, blogCName), this.getEditParam(
                DDAPIConstants.POST_LINK, state, tag, slug, title, null, url, description, null,
                null, null, null, id), ddClient.getToken());

    }

    /**
     * 异步编辑链接
     * 
     * @param blogCName
     * @param state
     * @param tag
     * @param slug
     * @param title
     * @param url
     * @param description
     * @param id
     * @param listener
     */
    public void editLink(String blogCName, String state, String tag, String slug, String title,
            String url, String description, String id, DDListener listener) {
        asyncDDRunner.doRequest(String.format(EDIT_POST_FORMAT, blogCName), this.getEditParam(
                DDAPIConstants.POST_LINK, state, tag, slug, title, null, url, description, null,
                null, null, null, id), listener, true, DDAPIConstants.HTTP_POST);
    }

    /**
     * 编辑视频
     * 
     * @param blogCName
     * @param state
     * @param tag
     * @param slug
     * @param caption
     * @param sourceUrl
     * @param id
     */
    public void editVideo(String blogCName, String state, String tag, String slug, String caption,
            String sourceUrl, String id) {
        this.doPost(String.format(EDIT_POST_FORMAT, blogCName), this.getEditParam(
                DDAPIConstants.POST_VIDEO, state, tag, slug, null, null, null, null, caption, null,
                null, sourceUrl, id), ddClient.getToken());
    }

    /**
     * 异步编辑视频
     * 
     * @param blogCName
     * @param state
     * @param tag
     * @param slug
     * @param caption
     * @param sourceUrl
     * @param id
     * @param listener
     */
    public void editVideo(String blogCName, String state, String tag, String slug, String caption,
            String sourceUrl, String id, DDListener listener) {
        asyncDDRunner.doRequest(String.format(EDIT_POST_FORMAT, blogCName), this.getEditParam(
                DDAPIConstants.POST_VIDEO, state, tag, slug, null, null, null, null, caption, null,
                null, sourceUrl, id), listener, true, DDAPIConstants.HTTP_POST);
    }

    /**
     * 编辑音乐
     * 
     * @param blogCName
     * @param state
     * @param tag
     * @param slug
     * @param caption
     * @param filePath
     * @param musicName
     * @param musicSinger
     * @param id
     * @param listener
     */
    public void editAudio(String blogCName, String state, String tag, String slug, String caption,
            String filePath, String musicName, String musicSinger, String id, DDListener listener) {
        byte[] data = BaseUtil.getFileData(new File(filePath));
        asyncDDRunner.doUpload(String.format(EDIT_POST_FORMAT, blogCName), this.getEditParam(
                DDAPIConstants.POST_AUDIO, state, tag, slug, null, null, null, null, caption,
                musicName, musicSinger, null, id), listener, "data", filePath, data, true);
    }

    /**
     * 异步编辑音乐
     * 
     * @param blogCName
     * @param state
     * @param tag
     * @param slug
     * @param caption
     * @param filePath
     * @param musicName
     * @param musicSinger
     * @param id
     */
    public void editAudio(String blogCName, String state, String tag, String slug, String caption,
            String filePath, String musicName, String musicSinger, String id) {
        byte[] data = BaseUtil.getFileData(new File(filePath));
        ddClient.doUpload(String.format(EDIT_POST_FORMAT, blogCName), this.getEditParam(
                DDAPIConstants.POST_AUDIO, state, tag, slug, null, null, null, null, caption,
                musicName, musicSinger, null, id), "data", filePath, data, ddClient.getToken());

    }

    /**
     * 编辑图片
     * 
     * @param blogCName
     * @param state
     * @param tag
     * @param slug
     * @param caption
     * @param filePath
     * @param id
     */
    public void editPhoto(String blogCName, String state, String tag, String slug, String caption,
            String filePath, String id) {
        byte[] data = BaseUtil.getFileData(new File(filePath));
        ddClient.doUpload(String.format(EDIT_POST_FORMAT, blogCName), this.getEditParam(
                DDAPIConstants.POST_PHOTO, state, tag, slug, null, null, null, null, caption, null,
                null, null, id), "data", filePath, data, ddClient.getToken());
    }

    /**
     * 异步编辑图片
     * 
     * @param blogCName
     * @param state
     * @param tag
     * @param slug
     * @param caption
     * @param filePath
     * @param id
     * @param listener
     */
    public void editPhoto(String blogCName, String state, String tag, String slug, String caption,
            String filePath, String id, DDListener listener) {
        byte[] data = BaseUtil.getFileData(new File(filePath));
        asyncDDRunner.doUpload(String.format(EDIT_POST_FORMAT, blogCName), this.getEditParam(
                DDAPIConstants.POST_PHOTO, state, tag, slug, null, null, null, null, caption, null,
                null, null, id), listener, "data", filePath, data, true);
    }

    private DDParameters getParamFromReblogPost(String id, String tag, String comment) {
        DDParameters param = new DDParameters();
        if (TextUtils.isEmpty(id)) {
            throw new DDAPIException(DDAPIConstants.INVALID_PARAMATERS, "post id can not by null",
                    null);
        }
        param.add("id", id);
        if (!TextUtils.isEmpty(tag)) {
            param.add("tag", tag);
        }
        if (!TextUtils.isEmpty(comment)) {
            param.add("comment", comment);
        }
        return param;
    }

    /**
     * 转载
     * 
     * @param blogCName
     * @param id
     * @param tag
     * @param comment
     */
    public void reblogPost(String blogCName, String id, String tag, String comment) {
        this.doPost(String.format(REBLOG_POST_FORMAT, blogCName),
                this.getParamFromReblogPost(id, tag, comment), ddClient.getToken());
    }

    /**
     * 异步转载
     * 
     * @param blogCName
     * @param id
     * @param tag
     * @param comment
     * @param listener
     */
    public void reblogPost(String blogCName, String id, String tag, String comment,
            DDListener listener) {
        asyncDDRunner.doRequest(String.format(REBLOG_POST_FORMAT, blogCName),
                this.getParamFromReblogPost(id, tag, comment), listener, true,
                DDAPIConstants.HTTP_POST);
    }

    private DDParameters getParamForId(String id) {
        DDParameters param = new DDParameters();
        if (TextUtils.isEmpty(id)) {
            throw new DDAPIException(DDAPIConstants.INVALID_PARAMATERS, "id can not by null", null);
        }
        param.add("id", id);
        return param;

    }

    /**
     * 删除文章
     * 
     * @param blogCName
     * @param id
     */
    public void deletePost(String blogCName, String id) {
        this.doPost(String.format(DELETE_POST_FORMAT, blogCName), this.getParamForId(id),
                ddClient.getToken());
    }

    /**
     * 异步删除文章
     * 
     * @param blogCName
     * @param id
     * @param listener
     */
    public void deletePost(String blogCName, String id, DDListener listener) {
        asyncDDRunner.doRequest(String.format(DELETE_POST_FORMAT, blogCName),
                this.getParamForId(id), listener, true, DDAPIConstants.HTTP_POST);
    }

    /**
     * 获取发布队列
     * 
     * @param blogCName
     * @return PostView
     */
    public PostView getQueue(String blogCName) {
        return this.getInfo(String.format(GET_QUEUE_FORMAT, blogCName), null, PostView.class,
                ddClient.getToken());
    }

    /**
     * 获取获取发布队列
     * 
     * @param blogCName
     * @param listener
     */
    public void getQueue(String blogCName, DDListener listener) {
        this.asyncDDRunner.doRequest(String.format(GET_QUEUE_FORMAT, blogCName), null, listener,
                true, DDAPIConstants.HTTP_GET);
    }

    /**
     * 获取草稿队列
     * 
     * @param blogCName
     * @return PostView
     */
    public PostView getDraft(String blogCName) {
        return this.getInfo(String.format(GET_DRAFT_FORMAT, blogCName), null, PostView.class,
                ddClient.getToken());
    }

    /**
     * 异步获取草稿队列
     * 
     * @param blogCName
     * @param listener
     */
    public void getDraft(String blogCName, DDListener listener) {
        this.asyncDDRunner.doRequest(String.format(GET_DRAFT_FORMAT, blogCName), null, listener,
                true, DDAPIConstants.HTTP_GET);
    }

    /**
     * 获取投稿
     * 
     * @param blogCName
     * @return PostView
     */
    public PostView getSubmission(String blogCName) {
        return this.getInfo(String.format(GET_SUBMISSION_FORMAT, blogCName), null, PostView.class,
                ddClient.getToken());
    }

    /**
     * 异步获取投稿
     * 
     * @param blogCName
     * @param listener
     */
    public void getSubmission(String blogCName, DDListener listener) {
        this.asyncDDRunner.doRequest(String.format(GET_SUBMISSION_FORMAT, blogCName), null,
                listener, true, DDAPIConstants.HTTP_GET);
    }

    /**
     * 获取用户信息
     * 
     * @return UserDetailInfo
     */
    public UserDetailInfo getUserInfo() {
        return this.getInfo(GET_USER_INFO_URL, null, UserDetailInfo.class, ddClient.getToken());
    }

    /**
     * 异步获取用户信息
     * 
     * @param listener
     */
    public void getUserInfo(DDListener listener) {
        asyncDDRunner.doRequest(GET_USER_INFO_URL, null, listener, true, DDAPIConstants.HTTP_GET);
    }

    /**
     * 获取我的喜欢
     * 
     * @param limit
     * @param offset
     * @return LikesPostView
     */
    public LikesPostView getLikes(int limit, int offset) {
        return this.getInfo(GET_LIKES_URL, getParamForLimitAndOffset(limit, offset),
                LikesPostView.class, ddClient.getToken());
    }

    /**
     * 异步获取我的喜欢
     * 
     * @param limit
     * @param offset
     * @param listener
     */
    public void getLikes(int limit, int offset, DDListener listener) {
        asyncDDRunner.doRequest(GET_LIKES_URL, getParamForLimitAndOffset(limit, offset), listener,
                true, DDAPIConstants.HTTP_GET);
    }

    /**
     * 获取我关注的博客列表
     * 
     * @param limit
     * @param offset
     * @return FollowView
     */
    public FollowingView getFollowing(int limit, int offset) {
        return this.getInfo(GET_FOLLOWING_URL, getParamForLimitAndOffset(limit, offset),
                FollowingView.class, ddClient.getToken());
    }

    /**
     * 异步获取我关注的博客列表
     * 
     * @param limit
     * @param offset
     * @param listener
     */

    public void getFollowing(int limit, int offset, DDListener listener) {
        asyncDDRunner.doRequest(GET_FOLLOWING_URL, getParamForLimitAndOffset(limit, offset),
                listener, true, DDAPIConstants.HTTP_GET);
    }

    /**
     * 喜欢
     * 
     * @param id
     */
    public void like(String id) {
        this.doPost(POST_LIKE_URL, getParamForId(id), ddClient.getToken());
    }

    /**
     * 异步喜欢
     * 
     * @param id
     * @param listener
     */
    public void like(String id, DDListener listener) {
        asyncDDRunner.doRequest(POST_LIKE_URL, getParamForId(id), listener, true,
                DDAPIConstants.HTTP_POST);
    }

    /**
     * 取消喜欢
     * 
     * @param id
     */
    public void unLike(String id) {
        this.doPost(DELETE_LIKE_URL, getParamForId(id), ddClient.getToken());
    }

    /**
     * 异步取消喜欢
     * 
     * @param id
     * @param listener
     */
    public void unLike(String id, DDListener listener) {
        asyncDDRunner.doRequest(DELETE_LIKE_URL, getParamForId(id), listener, true,
                DDAPIConstants.HTTP_POST);
    }

    private DDParameters getBlogCNameParam(String blogCName) {
        DDParameters param = new DDParameters();
        if (TextUtils.isEmpty(blogCName)) {
            throw new DDAPIException(DDAPIConstants.INVALID_PARAMATERS,
                    "blogCName can not be null", null);
        }
        param.add("blogCName", blogCName);
        return param;
    }

    /**
     * 关注
     * 
     * @param blogCName
     */
    public void follow(String blogCName) {
        this.doPost(POST_FOLLOW_URL, this.getBlogCNameParam(blogCName), ddClient.getToken());
    }

    /**
     * 异步关注
     * 
     * @param blogCName
     * @param listener
     */
    public void follow(String blogCName, DDListener listener) {
        asyncDDRunner.doRequest(POST_FOLLOW_URL, this.getBlogCNameParam(blogCName), listener, true,
                DDAPIConstants.HTTP_POST);
    }

    /**
     * 取消关注
     * 
     * @param blogCName
     */
    public void unFollow(String blogCName) {
        this.doPost(DELETE_FOLLOW_URL, this.getBlogCNameParam(blogCName), ddClient.getToken());
    }

    /**
     * 异步取消关注
     * 
     * @param blogCName
     * @param listener
     */
    public void unFollow(String blogCName, DDListener listener) {
        this.asyncDDRunner.doRequest(DELETE_FOLLOW_URL, this.getBlogCNameParam(blogCName),
                listener, true, DDAPIConstants.HTTP_POST);
    }
}
