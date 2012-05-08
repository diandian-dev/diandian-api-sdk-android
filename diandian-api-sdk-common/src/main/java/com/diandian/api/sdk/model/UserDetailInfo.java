/**
 * 
 */
package com.diandian.api.sdk.model;

import java.util.List;

/**
 * @author zhangdong <zhangdong@diandian.com>
 *         2012-2-17下午6:32:42
 */
public class UserDetailInfo {

    private int following;

    private int likes;

    private String name;

    private List<BlogStandardInfo> blogs;

    /**
     * @return the following
     */
    public int getFollowing() {
        return this.following;
    }

    /**
     * @param following the following to set
     */
    public void setFollowing(int following) {
        this.following = following;
    }

    /**
     * @return the likes
     */
    public int getLikes() {
        return this.likes;
    }

    /**
     * @param likes the likes to set
     */
    public void setLikes(int likes) {
        this.likes = likes;
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the blogs
     */
    public List<BlogStandardInfo> getBlogs() {
        return this.blogs;
    }

    /**
     * @param blogs the blogs to set
     */
    public void setBlogs(List<BlogStandardInfo> blogs) {
        this.blogs = blogs;
    }

    @Override
    public String toString() {
        return "UserDetailInfo [following=" + following + ", likes=" + likes + ", name=" + name
                + ", blogs=" + blogs + "]";
    }

}
