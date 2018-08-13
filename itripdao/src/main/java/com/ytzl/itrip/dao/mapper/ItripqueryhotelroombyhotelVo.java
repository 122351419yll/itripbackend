package com.ytzl.itrip.dao.mapper;

import io.swagger.models.auth.In;

/**
 * Created by l骆明 on 2018/8/13.
 */
public class ItripqueryhotelroombyhotelVo {
    private String endDate;//退房日期
    private long hotelId;//酒店id
    private Integer isBook;//是否预定（1.预定 0.为预定）
    private Integer isCancel;//是否可以取消 0.不可以 1.可以
    private Integer isHavingBreakfast;//是否有早餐0.没有1.有
    private Integer isTimelyResponse;//是否及时响应 0.不及时1.及时
    private Integer payType;//1.在线支付 2.到店支付 3.不限
    private long roomBedTypeId;//床型id
    private String startDate;//入住日期

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public long getHotelId() {
        return hotelId;
    }

    public void setHotelId(long hotelId) {
        this.hotelId = hotelId;
    }

    public Integer getIsBook() {
        return isBook;
    }

    public void setIsBook(Integer isBook) {
        this.isBook = isBook;
    }

    public Integer getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(Integer isCancel) {
        this.isCancel = isCancel;
    }

    public Integer getIsHavingBreakfast() {
        return isHavingBreakfast;
    }

    public void setIsHavingBreakfast(Integer isHavingBreakfast) {
        this.isHavingBreakfast = isHavingBreakfast;
    }

    public Integer getIsTimelyResponse() {
        return isTimelyResponse;
    }

    public void setIsTimelyResponse(Integer isTimelyResponse) {
        this.isTimelyResponse = isTimelyResponse;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public long getRoomBedTypeId() {
        return roomBedTypeId;
    }

    public void setRoomBedTypeId(long roomBedTypeId) {
        this.roomBedTypeId = roomBedTypeId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public ItripqueryhotelroombyhotelVo() {
    }
}