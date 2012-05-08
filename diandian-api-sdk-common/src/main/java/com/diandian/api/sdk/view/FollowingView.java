/**
 * 
 */
package com.diandian.api.sdk.view;

import java.util.List;

import com.diandian.api.sdk.model.BlogSimpleInfo;

/**
 * 用户关注的博客视图
 * 
 * @author zhangdong <zhangdong@diandian.com>
 *         2012-2-27下午3:02:32
 */
public class FollowingView {

    private int totalBlogs;

    private List<BlogSimpleInfo> blogs;

    public FollowingView() {
    }

    public int getTotalBlogs() {
        return totalBlogs;
    }

    public void setTotalBlogs(int totalBlogs) {
        this.totalBlogs = totalBlogs;
    }

    public List<BlogSimpleInfo> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<BlogSimpleInfo> blogs) {
        this.blogs = blogs;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "FollowingView [totalBlogs=" + this.totalBlogs + ", blogs=" + this.blogs + "]";
    }

}
