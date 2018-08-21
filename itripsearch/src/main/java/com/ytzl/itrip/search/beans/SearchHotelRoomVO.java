package com.ytzl.itrip.search.beans;

/**
 * Created by Administrator on 2018/7/30.
 */
public class SearchHotelRoomVO {

    private String endDate;
    private Integer hotelId;
    private Integer isBook;
    private Integer isCancel;
    private Integer isHavingBreakfast;
    private Integer isTimelyResponse;
    private Integer payType;
    private Integer roomBedTypeId;
    private String startDate;

    public SearchHotelRoomVO() {
    }

    public SearchHotelRoomVO(String endDate, Integer hotelId, Integer isBook, Integer isCancel, Integer isHavingBreakfast, Integer isTimelyResponse, Integer payType, Integer roomBedTypeId, String startDate) {
        this.endDate = endDate;
        this.hotelId = hotelId;
        this.isBook = isBook;
        this.isCancel = isCancel;
        this.isHavingBreakfast = isHavingBreakfast;
        this.isTimelyResponse = isTimelyResponse;
        this.payType = payType;
        this.roomBedTypeId = roomBedTypeId;
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
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

    public Integer getRoomBedTypeId() {
        return roomBedTypeId;
    }

    public void setRoomBedTypeId(Integer roomBedTypeId) {
        this.roomBedTypeId = roomBedTypeId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
