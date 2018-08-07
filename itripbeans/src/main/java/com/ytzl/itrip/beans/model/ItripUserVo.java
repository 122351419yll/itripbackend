package com.ytzl.itrip.beans.model;

/**
 * Created by Administrator on 2018/8/6.
 */
public class ItripUserVo {
    private String userCode;
    private String userName;
    private String userPassword;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public ItripUserVo() {
    }
}
