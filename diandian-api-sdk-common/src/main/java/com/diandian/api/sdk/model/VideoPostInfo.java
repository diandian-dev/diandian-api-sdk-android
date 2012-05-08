/**
 * 
 */
package com.diandian.api.sdk.model;

/**
 * @author zhangdong <zhangdong@diandian.com>
 *         2012-2-20下午4:26:16
 */
public class VideoPostInfo extends PostBaseInfo {

    private String sourceTitle;

    private String sourceUrl;

    private String videoId;

    private String videoType;

    private String caption;

    private String playerUrl;

    private String vieoImageUrl;

    /**
     * @return the sourceTitle
     */
    public String getSourceTitle() {
        return this.sourceTitle;
    }

    /**
     * @param sourceTitle the sourceTitle to set
     */
    public void setSourceTitle(String sourceTitle) {
        this.sourceTitle = sourceTitle;
    }

    /**
     * @return the sourceUrl
     */
    public String getSourceUrl() {
        return this.sourceUrl;
    }

    /**
     * @param sourceUrl the sourceUrl to set
     */
    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    /**
     * @return the videoId
     */
    public String getVideoId() {
        return this.videoId;
    }

    /**
     * @param videoId the videoId to set
     */
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    /**
     * @return the videoType
     */
    public String getVideoType() {
        return this.videoType;
    }

    /**
     * @param videoType the videoType to set
     */
    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    /**
     * @return the caption
     */
    public String getCaption() {
        return this.caption;
    }

    /**
     * @param caption the caption to set
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }

    /**
     * @return the playerUrl
     */
    public String getPlayerUrl() {
        return this.playerUrl;
    }

    /**
     * @param playerUrl the playerUrl to set
     */
    public void setPlayerUrl(String playerUrl) {
        this.playerUrl = playerUrl;
    }

    /**
     * @return the vieoImageUrl
     */
    public String getVieoImageUrl() {
        return this.vieoImageUrl;
    }

    /**
     * @param vieoImageUrl the vieoImageUrl to set
     */
    public void setVieoImageUrl(String vieoImageUrl) {
        this.vieoImageUrl = vieoImageUrl;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "VideoPostInfo [sourceTitle=" + this.sourceTitle + ", sourceUrl=" + this.sourceUrl
                + ", videoId=" + this.videoId + ", videoType=" + this.videoType + ", caption="
                + this.caption + ", playerUrl=" + this.playerUrl + ", vieoImageUrl="
                + this.vieoImageUrl + "]";
    }

}
