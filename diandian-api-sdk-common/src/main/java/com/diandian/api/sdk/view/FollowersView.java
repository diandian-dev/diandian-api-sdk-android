/**
 * 
 */
package com.diandian.api.sdk.view;

import java.util.List;

import com.diandian.api.sdk.model.UserStandardInfo;

/**
 * 博客的关注者视图
 * 
 * @author zhangdong <zhangdong@diandian.com>
 *         2012-2-23上午10:51:41
 */
public class FollowersView {

    private int totalUsers;

    private List<UserStandardInfo> users;

    public FollowersView() {
    }

    public int getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }

    public List<UserStandardInfo> getUsers() {
        return users;
    }

    public void setUsers(List<UserStandardInfo> users) {
        this.users = users;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "FollowersView [totalUsers=" + this.totalUsers + ", users=" + this.users + "]";
    }

}
