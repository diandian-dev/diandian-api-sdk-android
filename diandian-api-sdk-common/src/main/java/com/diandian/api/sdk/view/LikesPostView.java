/**
 * 
 */
package com.diandian.api.sdk.view;

import java.util.List;

import com.diandian.api.sdk.model.PostBaseInfo;

/**
 * 用户喜欢的文章视图
 * 
 * @author zhangdong <zhangdong@diandian.com>
 *         2012-2-29下午5:17:23
 */
public class LikesPostView {

    private int likedCount;

    private List<PostBaseInfo> likedPost;

    /**
     * @param likedCount2
     * @param likedPost2
     */
    public LikesPostView() {
    }

    public int getLikedCount() {
        return likedCount;
    }

    public void setLikedCount(int likedCount) {
        this.likedCount = likedCount;
    }

    public List<PostBaseInfo> getLikedPost() {
        return likedPost;
    }

    public void setLikedPost(List<PostBaseInfo> likedPost) {
        this.likedPost = likedPost;
    }

    @Override
    public String toString() {
        return "LikesPostView [likedCount=" + likedCount + ", likedPost=" + likedPost + "]";
    }

}
