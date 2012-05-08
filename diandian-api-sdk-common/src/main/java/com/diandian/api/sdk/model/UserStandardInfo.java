/**
 * 
 */
package com.diandian.api.sdk.model;

/**
 * @author zhangdong <zhangdong@diandian.com>
 *         2012-2-17下午6:33:00
 */
public class UserStandardInfo {

    private long updated;

    private String name;

    private String blogCName;

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
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

    @Override
    public String toString() {
        return "UserStandardInfo [updated=" + updated + ", name=" + name + ", blogCName="
                + blogCName + "]";
    }

}
