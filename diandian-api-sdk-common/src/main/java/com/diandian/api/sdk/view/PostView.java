/**
 * 
 */
package com.diandian.api.sdk.view;

import java.util.List;

import com.diandian.api.sdk.model.BlogDetailInfo;
import com.diandian.api.sdk.model.PostBaseInfo;

/**
 * 文章视图
 * 
 * @author zhangdong <zhangdong@diandian.com>
 *         2012-2-23下午4:04:30
 */
public class PostView {

    private BlogDetailInfo blog;

    private long totalPosts;

    private List<PostBaseInfo> posts;

    public PostView() {
    }

    public BlogDetailInfo getBlog() {
        return blog;
    }

    public void setBlog(BlogDetailInfo blog) {
        this.blog = blog;
    }

    public long getTotalPosts() {
        return totalPosts;
    }

    public void setTotalPosts(long totalPosts) {
        this.totalPosts = totalPosts;
    }

    public List<PostBaseInfo> getPosts() {
        return posts;
    }

    public void setPosts(List<PostBaseInfo> posts) {
        this.posts = posts;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PostView [blog=" + this.blog + ", totalPosts=" + this.totalPosts + ", posts="
                + this.posts + "]";
    }

}
