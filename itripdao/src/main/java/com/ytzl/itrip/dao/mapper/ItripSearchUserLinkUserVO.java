package com.ytzl.itrip.dao.mapper;

import io.swagger.models.auth.In;
import sun.rmi.server.InactiveGroupException;

/**
 * Created by l骆明 on 2018/8/16.
 */
public class ItripSearchUserLinkUserVO {
    private String linkUserName;

    public String getLinkUserName() {
        return linkUserName;
    }

    public void setLinkUserName(String linkUserName) {
        this.linkUserName = linkUserName;
    }

    public ItripSearchUserLinkUserVO() {
    }
}
