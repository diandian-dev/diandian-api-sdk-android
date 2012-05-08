/**
 * 
 */
package com.diandian.api.sdk.model;

/**
 * @author zhangdong <zhangdong@diandian.com>
 *         2012-2-20下午4:06:54
 */
public class LinkPostInfo extends PostBaseInfo {

    private String title;

    private String url;

    private String description;

    /**
     * @return the title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "LinkPostInfo [title=" + this.title + ", url=" + this.url + ", description="
                + this.description + "]";
    }

}
