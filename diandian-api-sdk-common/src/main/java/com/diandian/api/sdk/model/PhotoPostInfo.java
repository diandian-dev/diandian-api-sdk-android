/**
 * 
 */
package com.diandian.api.sdk.model;

import java.util.Arrays;

/**
 * @author zhangdong <zhangdong@diandian.com>
 *         2012-2-20下午1:58:23
 */
public class PhotoPostInfo extends PostBaseInfo {

    private ImageInfo imageInfoArr[];

    private String caption;

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setPhotos(ImageInfo[] imageInfoArr) {
        this.imageInfoArr = imageInfoArr;
    }

    public String getCaption() {
        return caption;
    }

    public ImageInfo[] getPhotos() {
        return imageInfoArr;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PhotoPostInfo [imageInfoArr=" + Arrays.toString(this.imageInfoArr) + ", caption="
                + this.caption + "]";
    }

}
