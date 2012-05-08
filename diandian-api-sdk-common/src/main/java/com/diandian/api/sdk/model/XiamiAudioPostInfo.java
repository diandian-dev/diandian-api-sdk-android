/**
 * 
 */
package com.diandian.api.sdk.model;

/**
 * @author zhangdong <zhangdong@diandian.com>
 *         2012-2-23下午2:23:36
 */
public class XiamiAudioPostInfo extends AudioPostInfo {

    private String songId;

    private String musicName;

    private String cover;

    private String albumName;

    private String ablumId;

    private String musicSinger;

    private String artistId;

    private String caption;

    private String coverNormal;

    private String coverLarge;

    private String coverSmall;

    private String playerUrl;

    @Override
    public String getAudioType() {
        return "xiamiaudio";
    }

    /**
     * @return the songId
     */
    public String getSongId() {
        return this.songId;
    }

    /**
     * @param songId the songId to set
     */
    public void setSongId(String songId) {
        this.songId = songId;
    }

    /**
     * @return the musicName
     */
    @Override
    public String getMusicName() {
        return this.musicName;
    }

    /**
     * @param musicName the musicName to set
     */
    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    /**
     * @return the cover
     */
    @Override
    public String getCover() {
        return this.cover;
    }

    /**
     * @param cover the cover to set
     */
    public void setCover(String cover) {
        this.cover = cover;
    }

    /**
     * @return the albumName
     */
    public String getAlbumName() {
        return this.albumName;
    }

    /**
     * @param albumName the albumName to set
     */
    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    /**
     * @return the ablumId
     */
    public String getAblumId() {
        return this.ablumId;
    }

    /**
     * @param ablumId the ablumId to set
     */
    public void setAblumId(String ablumId) {
        this.ablumId = ablumId;
    }

    /**
     * @return the musicSinger
     */
    @Override
    public String getMusicSinger() {
        return this.musicSinger;
    }

    /**
     * @param musicSinger the musicSinger to set
     */
    public void setMusicSinger(String musicSinger) {
        this.musicSinger = musicSinger;
    }

    /**
     * @return the artistId
     */
    public String getArtistId() {
        return this.artistId;
    }

    /**
     * @param artistId the artistId to set
     */
    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    /**
     * @return the caption
     */
    @Override
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
     * @return the coverLarge
     */
    @Override
    public String getCoverLarge() {
        return this.coverLarge;
    }

    /**
     * @param coverLarge the coverLarge to set
     */
    public void setCoverLarge(String coverLarge) {
        this.coverLarge = coverLarge;
    }

    /**
     * @return the coverSmall
     */
    @Override
    public String getCoverSmall() {
        return this.coverSmall;
    }

    /**
     * @param coverSmall the coverSmall to set
     */
    public void setCoverSmall(String coverSmall) {
        this.coverSmall = coverSmall;
    }

    /**
     * @return the playerUrl
     */
    @Override
    public String getPlayerUrl() {
        return this.playerUrl;
    }

    /**
     * @param playerUrl the playerUrl to set
     */
    public void setPlayerUrl(String playerUrl) {
        this.playerUrl = playerUrl;
    }

    /* (non-Javadoc)
     * @see com.diandian.api.sdk.model.AudioPostInfo#getCoverNormal()
     */
    @Override
    public String getCoverNormal() {
        return coverNormal;
    }

    /**
     * @param coverNormal the coverNormal to set
     */
    public void setCoverNormal(String coverNormal) {
        this.coverNormal = coverNormal;
    }

}
