package com.ytzl.itrip.dao.mapper;

import java.nio.channels.Pipe;

/**
 * Created by l骆明 on 2018/8/15.
 */
public class ItripCountComment {
    private Integer improve;
    private Integer isok;
    private Integer havingimg;
    private Integer allcomment;

    public Integer getImprove() {
        return improve;
    }

    public void setImprove(Integer improve) {
        this.improve = improve;
    }

    public Integer getIsOk() {
        return isok;
    }

    public void setIsOk(Integer isOk) {
        this.isok = isok;
    }

    public Integer getHavingimg() {
        return havingimg;
    }

    public void setHavingimg(Integer havingimg) {
        this.havingimg = havingimg;
    }

    public Integer getAllcomment() {
        return allcomment;
    }

    public void setAllcomment(Integer allcomment) {
        this.allcomment = allcomment;
    }

    public ItripCountComment() {
    }

    public ItripCountComment(Integer improve, Integer isOk, Integer havingimg, Integer allcomment) {
        this.improve = improve;
        this.isok = isOk;
        this.havingimg = havingimg;
        this.allcomment = allcomment;
    }
}
