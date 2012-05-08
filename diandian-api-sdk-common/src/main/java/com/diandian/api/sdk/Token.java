/**
 * 
 */
package com.diandian.api.sdk;

import java.util.Arrays;

/**
 * Token
 * 
 * @author zhangdong <zhangdong@diandian.com>
 *         2012-4-9上午11:14:47
 */
public class Token {

    private String accessToken;

    private String refreshToken;

    private String tokenType;

    private long expiresIn;

    private long lastUpdateTime;

    private String[] scope;

    public Token() {

    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String[] getScope() {
        return scope;
    }

    public void setScope(String[] scope) {
        this.scope = scope;
    }

    /**
     * @return the lastUpdateTime
     */
    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * @param lastUpdateTime the lastUpdateTime to set
     */
    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Token [accessToken=" + this.accessToken + ", refreshToken=" + this.refreshToken
                + ", tokenType=" + this.tokenType + ", expiresIn=" + this.expiresIn
                + ", lastUpdateTime=" + this.lastUpdateTime + ", scope="
                + Arrays.toString(this.scope) + "]";
    }

}
