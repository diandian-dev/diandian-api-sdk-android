/**
 * 
 */
package com.diandian.api.sdk.model;

/**
 * @author zhangdong <zhangdong@diandian.com>
 *         2012-2-17下午4:52:37
 */
public class BlogDetailInfo {

    private String title;

    private String name;

    private String blogCName;

    private String posts;

    private String description;

    private long updated;

    private int likes;

    public int getLikes() {
        return likes;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public long getUpdated() {
        return this.updated;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

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

    public String getPosts() {
        return posts;
    }

    public void setPosts(String posts) {
        this.posts = posts;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "BlogDetailInfo [title=" + title + ", name=" + name + ", blogCName=" + blogCName
                + ", posts=" + posts + ", description=" + description + ", updated=" + updated
                + ", likes=" + likes + ", getLikes()=" + getLikes() + ", getUpdated()="
                + getUpdated() + ", getTitle()=" + getTitle() + ", getName()=" + getName()
                + ", getBlogCName()=" + getBlogCName() + ", getPosts()=" + getPosts()
                + ", getDescription()=" + getDescription() + ", getClass()=" + getClass()
                + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
    }

}
