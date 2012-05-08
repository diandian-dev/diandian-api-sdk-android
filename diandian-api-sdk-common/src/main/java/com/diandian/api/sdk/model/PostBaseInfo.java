/**
 * 
 */
package com.diandian.api.sdk.model;

import java.util.List;

/**
 * @author zhangdong zhangdong@diandian.com
 * 
 *         2012-4-16 上午11:54:52
 */
public abstract class PostBaseInfo {

    private String postId;

    private String postUrl;

    private String type;

    private String blogName;

    private String createTime;

    private List<String> tags;

    private int commentCount;

    private int likeCount;

    private int reblogCount;

    private List<NoteInfo> notes;

    private String reblogedFromUrl;

    private String reblogedFromName;

    private String reblogedFromTitle;

    private String reblogedRootUrl;

    private String reblogedRootName;

    private String reblogedRootTitle;

    /**
     * @return the postId
     */
    public String getPostId() {
        return this.postId;
    }

    /**
     * @param postId the postId to set
     */
    public void setPostId(String postId) {
        this.postId = postId;
    }

    /**
     * @return the postUrl
     */
    public String getPostUrl() {
        return this.postUrl;
    }

    /**
     * @param postUrl the postUrl to set
     */
    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
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
     * @return the createTime
     */
    public String getCreateTime() {
        return this.createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the tags
     */
    public List<String> getTags() {
        return this.tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    /**
     * @return the commentCount
     */
    public int getCommentCount() {
        return this.commentCount;
    }

    /**
     * @param commentCount the commentCount to set
     */
    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    /**
     * @return the likeCount
     */
    public int getLikeCount() {
        return this.likeCount;
    }

    /**
     * @param likeCount the likeCount to set
     */
    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    /**
     * @return the reblogCount
     */
    public int getReblogCount() {
        return this.reblogCount;
    }

    /**
     * @param reblogCount the reblogCount to set
     */
    public void setReblogCount(int reblogCount) {
        this.reblogCount = reblogCount;
    }

    /**
     * @return the notes
     */
    public List<NoteInfo> getNotes() {
        return this.notes;
    }

    /**
     * @param notes the notes to set
     */
    public void setNotes(List<NoteInfo> notes) {
        this.notes = notes;
    }

    /**
     * @return the reblogedFromUrl
     */
    public String getReblogedFromUrl() {
        return this.reblogedFromUrl;
    }

    /**
     * @param reblogedFromUrl the reblogedFromUrl to set
     */
    public void setReblogedFromUrl(String reblogedFromUrl) {
        this.reblogedFromUrl = reblogedFromUrl;
    }

    /**
     * @return the reblogedFromName
     */
    public String getReblogedFromName() {
        return this.reblogedFromName;
    }

    /**
     * @param reblogedFromName the reblogedFromName to set
     */
    public void setReblogedFromName(String reblogedFromName) {
        this.reblogedFromName = reblogedFromName;
    }

    /**
     * @return the reblogedFromTitle
     */
    public String getReblogedFromTitle() {
        return this.reblogedFromTitle;
    }

    /**
     * @param reblogedFromTitle the reblogedFromTitle to set
     */
    public void setReblogedFromTitle(String reblogedFromTitle) {
        this.reblogedFromTitle = reblogedFromTitle;
    }

    /**
     * @return the reblogedRootUrl
     */
    public String getReblogedRootUrl() {
        return this.reblogedRootUrl;
    }

    /**
     * @param reblogedRootUrl the reblogedRootUrl to set
     */
    public void setReblogedRootUrl(String reblogedRootUrl) {
        this.reblogedRootUrl = reblogedRootUrl;
    }

    /**
     * @return the reblogedRootName
     */
    public String getReblogedRootName() {
        return this.reblogedRootName;
    }

    /**
     * @param reblogedRootName the reblogedRootName to set
     */
    public void setReblogedRootName(String reblogedRootName) {
        this.reblogedRootName = reblogedRootName;
    }

    /**
     * @return the reblogedRootTitle
     */
    public String getReblogedRootTitle() {
        return this.reblogedRootTitle;
    }

    /**
     * @param reblogedRootTitle the reblogedRootTitle to set
     */
    public void setReblogedRootTitle(String reblogedRootTitle) {
        this.reblogedRootTitle = reblogedRootTitle;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PostBase [postId=" + this.postId + ", postUrl=" + this.postUrl + ", type="
                + this.type + ", blogName=" + this.blogName + ", createTime=" + this.createTime
                + ", tags=" + this.tags + ", commentCount=" + this.commentCount + ", likeCount="
                + this.likeCount + ", reblogCount=" + this.reblogCount + ", notes=" + this.notes
                + ", reblogedFromUrl=" + this.reblogedFromUrl + ", reblogedFromName="
                + this.reblogedFromName + ", reblogedFromTitle=" + this.reblogedFromTitle
                + ", reblogedRootUrl=" + this.reblogedRootUrl + ", reblogedRootName="
                + this.reblogedRootName + ", reblogedRootTitle=" + this.reblogedRootTitle + "]";
    }

}
