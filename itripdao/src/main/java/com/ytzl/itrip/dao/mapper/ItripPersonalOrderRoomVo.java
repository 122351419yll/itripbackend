package com.ytzl.itrip.dao.mapper;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 根据订单ID查看个人订单详情-房型相关信息
 */
public class ItripPersonalOrderRoomVo {
    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    private Long id;//订单id itrip_Hotel_order
    private Integer count;//预定数量 itrip_Hotel_order
    private String checkOutDate;// 退房时间 itrip_Hotel_order
    private String checkInDate;// 入住时间 itrip_Hotel_order
    private Integer bookingDays;//预定天数 itrip_Hotel_order
    private String linkUserName;//入住人 itrip_Hotel_order
    private String specialRequirement;//特殊需求 itrip_Hotel_order
    private BigDecimal payAmount;//支付金额 itrip_Hotel_order
    private Integer checkInWeek;//入住星期 itrip_Hotel_order checkInDate计算
    private Integer checkOutWeek;//退房星期 itrip_Hotel_order checkOutDate计算

    private Long hotelId;//酒店id itrip_Hotel
    private String hotelName;//酒店名称 itrip_Hotel
    private Integer hotelLevel;//酒店星级 itrip_Hotel
    private String address;//酒店位置 itrip_Hotel
    private Long roomId;//  房型ID  Itrip_Hootel_room
    private String roomTitle;//房型名称 Itrip_Hootel_room
    private Long roomBedTypeId;//床型 Itrip_Hootel_room
    private Integer roomPayType;//房间支持支付类型 Itrip_Hootel_room
    private Integer isHavingBreakfast;//是否有早餐 Itrip_Hootel_room

    private String roomBedTypeName;//床型名称 Itrip_lable_dic

    public ItripPersonalOrderRoomVo() {
    }

    public ItripPersonalOrderRoomVo(Long id, Long hotelId, String hotelName, Integer hotelLevel, String address, Long roomId, String roomTitle, Long roomBedTypeId, String checkInDate, String checkOutDate, Integer count, Integer bookingDays, String linkUserName, String specialRequirement, BigDecimal payAmount, Integer roomPayType, Integer isHavingBreakfast, String roomBedTypeName, Integer checkInWeek, Integer checkOutWeek) {
        this.id = id;
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.hotelLevel = hotelLevel;
        this.address = address;
        this.roomId = roomId;
        this.roomTitle = roomTitle;
        this.roomBedTypeId = roomBedTypeId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.count = count;
        this.bookingDays = bookingDays;
        this.linkUserName = linkUserName;
        this.specialRequirement = specialRequirement;
        this.payAmount = payAmount;
        this.roomPayType = roomPayType;
        this.isHavingBreakfast = isHavingBreakfast;
        this.roomBedTypeName = roomBedTypeName;
        this.checkInWeek = checkInWeek;
        this.checkOutWeek = checkOutWeek;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public Integer getHotelLevel() {
        return hotelLevel;
    }

    public void setHotelLevel(Integer hotelLevel) {
        this.hotelLevel = hotelLevel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomTitle() {
        return roomTitle;
    }

    public void setRoomTitle(String roomTitle) {
        this.roomTitle = roomTitle;
    }

    public Long getRoomBedTypeId() {
        return roomBedTypeId;
    }

    public void setRoomBedTypeId(Long roomBedTypeId) {
        this.roomBedTypeId = roomBedTypeId;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        String indate=format.format(checkInDate);
        this.checkInDate = indate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        String outDate =format.format(checkOutDate);
        this.checkOutDate = outDate;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getBookingDays() {
        return bookingDays;
    }

    public void setBookingDays(Integer bookingDays) {
        this.bookingDays = bookingDays;
    }

    public String getLinkUserName() {
        return linkUserName;
    }

    public void setLinkUserName(String linkUserName) {
        this.linkUserName = linkUserName;
    }

    public String getSpecialRequirement() {
        return specialRequirement;
    }

    public void setSpecialRequirement(String specialRequirement) {
        this.specialRequirement = specialRequirement;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public Integer getRoomPayType() {
        return roomPayType;
    }

    public void setRoomPayType(Integer roomPayType) {
        this.roomPayType = roomPayType;
    }

    public Integer getIsHavingBreakfast() {
        return isHavingBreakfast;
    }

    public void setIsHavingBreakfast(Integer isHavingBreakfast) {
        this.isHavingBreakfast = isHavingBreakfast;
    }

    public String getRoomBedTypeName() {
        return roomBedTypeName;
    }

    public void setRoomBedTypeName(String roomBedTypeName) {
        this.roomBedTypeName = roomBedTypeName;
    }

    public Integer getCheckInWeek() {
        return checkInWeek;
    }

    public void setCheckInWeek(Integer checkInWeek) {
        this.checkInWeek = checkInWeek;
    }

    public Integer getCheckOutWeek() {
        return checkOutWeek;
    }

    public void setCheckOutWeek(Integer checkOutWeek) {
        this.checkOutWeek = checkOutWeek;
    }
}
