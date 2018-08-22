package com.ytzl.itrip.dao.mapper;

import io.swagger.models.auth.In;

/**
 * Created by l骆明 on 2018/8/17.
 */
public class ItripSearchOrderVO {
    private String endDate;//预定时间
    private String linkUserName;//联系人
    private String orderNo;//订单号
    private Integer orderStatus;//订单状态
    private Integer orderType;//订单类型
    private Integer pageNo;//页码
    private Integer pageSize;//页面容量
    private Integer startDate;//预定时间

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getLinkUserName() {
        return linkUserName;
    }

    public void setLinkUserName(String linkUserName) {
        this.linkUserName = linkUserName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
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

    public Integer getStartDate() {
        return startDate;
    }

    public void setStartDate(Integer startDate) {
        this.startDate = startDate;
    }

    public ItripSearchOrderVO() {
    }
}
