package com.ytzl.itrip.dao.mapper;

import io.swagger.models.auth.In;

/**
 * Created by l骆明 on 2018/8/14.
 */
public class itripSearchCommentVO {
    private String hotelId;
    private Integer isHavingImg;
    private Integer isOk;
    private Integer pageNo;
    private Integer pageSize;

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public Integer getIsHavingImg() {
        return isHavingImg;
    }

    public void setIsHavingImg(Integer isHavingImg) {
        this.isHavingImg = isHavingImg;
    }

    public Integer getIsOk() {
        return isOk;
    }

    public void setIsOk(Integer isOk) {
        this.isOk = isOk;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public itripSearchCommentVO() {
    }
}

