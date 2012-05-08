/**
 * 
 */
package com.diandian.api.sdk.view;

import java.util.List;

import com.diandian.api.sdk.model.PostBaseInfo;

/**
 * 个人首页视图
 * 
 * @author zhangdong <zhangdong@diandian.com>
 *         2012-2-24上午10:38:27
 */
public class DashboardView {

    private List<PostBaseInfo> posts;

    public DashboardView() {
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
        return "DashboardView [posts=" + this.posts + "]";
    }

}
