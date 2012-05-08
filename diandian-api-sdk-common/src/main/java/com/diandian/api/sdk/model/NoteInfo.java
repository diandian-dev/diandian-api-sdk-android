/**
 * 
 */
package com.diandian.api.sdk.model;

import java.util.Date;

/**
 * @author zhangdong <zhangdong@diandian.com>
 *         2012-3-7下午11:25:35
 */
public class NoteInfo {

    private Date createTime;

    private String blogName;

    private String blogCName;

    private String type;

    /**
     * @return the createTime
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(long createTime) {
        this.createTime = new Date(createTime);
    }

    /**
     * @return the blogName
     */
    public String getBlogName() {
        return this.blogName;
    }

    /**
     * @param blogName the blogName to set
     */
    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    /**
     * @return the blogCName
     */
    public String getBlogCName() {
        return this.blogCName;
    }

    /**
     * @param blogCName the blogCName to set
     */
    public void setBlogCName(String blogCName) {
        this.blogCName = blogCName;
    }

    /**
     * @return the type
     */
    public String getType() {
        return this.type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "NoteInfo [createTime=" + this.createTime + ", blogName=" + this.blogName
                + ", blogCName=" + this.blogCName + ", type=" + this.type + "]";
    }

}
