/**
 * 
 */
package com.diandian.api.sdk.model;

/**
 * @author zhangdong zhangdong@diandian.com
 * 
 *         2012-4-16 下午1:26:53
 */
public class ImageDetail {

    private int width;

    private int height;

    private String url;

    public ImageDetail(int width, int height, String url) {
        this.setWidth(width);
        this.height = height;
        this.url = url;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
