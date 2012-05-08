/**
 * 
 */
package com.diandian.api.sdk.model;

/**
 * 
 * @author zhangdong zhangdong@diandian.com
 * 
 *         2012-4-16 下午1:33:16
 */
public abstract class AudioPostInfo extends PostBaseInfo {

    public abstract String getAudioType();

    /**
     * audio具体类型由其子类决定。这里type统一为audio类型
     */
    @Override
    public String getType() {
        return "audio";
    }

    public abstract String getCaption();

    public abstract String getMusicName();

    public abstract String getMusicSinger();

    public abstract String getPlayerUrl();

    public abstract String getCover();

    public abstract String getCoverLarge();

    public abstract String getCoverNormal();

    public abstract String getCoverSmall();

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AudioPostInfo [getAudioType()=" + this.getAudioType() + ", getType()="
                + this.getType() + ", getCaption()=" + this.getCaption() + ", getMusicName()="
                + this.getMusicName() + ", getMusicSinger()=" + this.getMusicSinger()
                + ", getPlayerUrl()=" + this.getPlayerUrl() + ", getCover()=" + this.getCover()
                + ", getCoverLarge()=" + this.getCoverLarge() + ", getCoverNormal()="
                + this.getCoverNormal() + ", getCoverSmall()=" + this.getCoverSmall() + "]";
    }

}
