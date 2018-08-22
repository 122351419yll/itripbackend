package com.ytzl.itrip.dao.mapper;

import io.swagger.models.auth.In;

/**
 * Created by l骆明 on 2018/8/16.
 */
public class ItripAddUserLinkUserVO {
    private String linkIdCard;
    private Integer linkIdCardType;
    private String linkPhone;
    private String linkUserName;
    private long userId;

    public ItripAddUserLinkUserVO() {
    }

    public String getLinkIdCard() {
        return linkIdCard;
    }

    public void setLinkIdCard(String linkIdCard) {
        this.linkIdCard = linkIdCard;
    }

    public Integer getLinkIdCardType() {
        return linkIdCardType;
    }

    public void setLinkIdCardType(Integer linkIdCardType) {
        this.linkIdCardType = linkIdCardType;
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

    public String getLinkUserName() {
        return linkUserName;
    }

    public void setLinkUserName(String linkUserName) {
        this.linkUserName = linkUserName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
