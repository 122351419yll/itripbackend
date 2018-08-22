package com.ytzl.itrip.dao.mapper;

import java.util.Date;

/**
 * Created by l骆明 on 2018/8/20.
 */
public class ValidateRoomStoreVo {
    private Date checkInDate;
    private Date checkOutDate;
    private Integer count;
    private Long hotelId;
    private Long roomId;

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

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public ValidateRoomStoreVo() {
    }
}
