/**
 * 
 */
package com.diandian.api.sdk.model;

import java.util.Map;

/**
 * @author zhangdong <zhangdong@diandian.com> 2012-2-20下午2:11:02
 */
public class ImageInfo {

    private String caption;

    private Map<String, ImageDetail> altSizes;

    public Map<String, ImageDetail> getAltSizes() {
        return altSizes;
    }

    public void setAltSize(Map<String, ImageDetail> altSizes) {
        this.altSizes = altSizes;
    }

    /**
     * @return the caption
     */
    public String getCaption() {
        return caption;
    }

    /**
     * @param caption the caption to set
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ImageInfo [caption=" + this.caption + ", altSizes=" + this.altSizes + "]";
    }

}
