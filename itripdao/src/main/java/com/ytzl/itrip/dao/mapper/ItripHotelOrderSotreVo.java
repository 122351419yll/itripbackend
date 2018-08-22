package com.ytzl.itrip.dao.mapper;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by l骆明 on 2018/8/20.
 */
public class ItripHotelOrderSotreVo {
    //入住时间
    private Date checkInDate;
    //退房时间
    private Date checkOutDate;
    //房间数量 默认值为1
    private Integer count;
    //酒店Id
    private  Integer hotelId;
    //商品id
    private Integer roomId;
    //记录时间
    private Date recordDate;
    //库存
    private Integer store;
    //酒店名称
    private String hotelName;
    //房型价格
    private Float hotelPrice;

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public Integer getStore() {
        return store;
    }

    public void setStore(Integer store) {
        this.store = store;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public Float getHotelPrice() {
        return hotelPrice;
    }

    public void setHotelPrice(Float hotelPrice) {
        this.hotelPrice = hotelPrice;
    }

    public ItripHotelOrderSotreVo() {
    }
}
