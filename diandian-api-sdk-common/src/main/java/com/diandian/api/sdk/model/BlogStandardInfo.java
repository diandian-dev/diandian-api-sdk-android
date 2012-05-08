/**
 * 
 */
package com.diandian.api.sdk.model;

/**
 * @author zhangdong <zhangdong@diandian.com>
 *         2012-2-17下午4:53:19
 */
public class BlogStandardInfo {

    private String title;

    private String name;

    private String blogCName;

    private int followers;

    private boolean primary;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBlogCName() {
        return blogCName;
    }

    public void setBlogCName(String blogCName) {
        this.blogCName = blogCName;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    @Override
    public String toString() {
        return "BlogStandardInfo [title=" + title + ", name=" + name + ", blogCName=" + blogCName
                + ", followers=" + followers + ", primary=" + primary + "]";
    }

}
