package com.ytzl.itrip.dao.mapper;

/**
 * Created by l骆明 on 2018/8/15.
 */
public class ItripgethoteldescVo {
    private long  hotelId;
    private Integer hotelLevel;
    private String hotelName;

    public ItripgethoteldescVo() {
    }

    public long getHotelId() {
        return hotelId;
    }

    public void setHotelId(long hotelId) {
        this.hotelId = hotelId;
    }

    public Integer getHotelLevel() {
        return hotelLevel;
    }

    public void setHotelLevel(Integer hotelLevel) {
        this.hotelLevel = hotelLevel;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }
}
