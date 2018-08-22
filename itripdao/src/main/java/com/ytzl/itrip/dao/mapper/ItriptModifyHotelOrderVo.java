package com.ytzl.itrip.dao.mapper;

import com.ytzl.itrip.beans.model.ItripOrderLinkUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pang on 2018/8/7.
 */
public class ItriptModifyHotelOrderVo {
    // [必填，主键] ,
    private Long id;
    //[必填，注：接收数字类型] 支付方式(:1:支付宝 2:微信 3:到店付)
    private Integer payType;
    private Integer orderType;
    private String orderNo;
    private Long hotelId;
    private String hotelName;
    private Long roomId;
    private Integer count;
    private Integer  bookingDays;
    private Date checkInDate;
    private Date checkOutDate;
    private String noticePhone;
    private String noticeEmail;
    private String specialRequirement;
    private Integer isNeedInvoice;
    private Integer invoiceType;
    private String invoiceHead;
    private String linkUserName;
    private Integer bookType;
    // (Array[ItripOrderLinkUserVo], optional),
    private List<ItripOrderLinkUser> itripOrderLinkUserList;

    public ItriptModifyHotelOrderVo() {
    }

    public ItriptModifyHotelOrderVo(Long id, Integer payType, Integer orderType, String orderNo, Long hotelId, String hotelName, Long roomId, Integer count, Integer bookingDays, Date checkInDate, Date checkOutDate, String noticePhone, String noticeEmail, String specialRequirement, Integer isNeedInvoice, Integer invoiceType, String invoiceHead, String linkUserName, Integer bookType, List<ItripOrderLinkUser> itripOrderLinkUserList) {
        this.id = id;
        this.payType = payType;
        this.orderType = orderType;
        this.orderNo = orderNo;
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.roomId = roomId;
        this.count = count;
        this.bookingDays = bookingDays;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.noticePhone = noticePhone;
        this.noticeEmail = noticeEmail;
        this.specialRequirement = specialRequirement;
        this.isNeedInvoice = isNeedInvoice;
        this.invoiceType = invoiceType;
        this.invoiceHead = invoiceHead;
        this.linkUserName = linkUserName;
        this.bookType = bookType;
        this.itripOrderLinkUserList = itripOrderLinkUserList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
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

    public String getNoticePhone() {
        return noticePhone;
    }

    public void setNoticePhone(String noticePhone) {
        this.noticePhone = noticePhone;
    }

    public String getNoticeEmail() {
        return noticeEmail;
    }

    public void setNoticeEmail(String noticeEmail) {
        this.noticeEmail = noticeEmail;
    }

    public String getSpecialRequirement() {
        return specialRequirement;
    }

    public void setSpecialRequirement(String specialRequirement) {
        this.specialRequirement = specialRequirement;
    }

    public Integer getIsNeedInvoice() {
        return isNeedInvoice;
    }

    public void setIsNeedInvoice(Integer isNeedInvoice) {
        this.isNeedInvoice = isNeedInvoice;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceHead() {
        return invoiceHead;
    }

    public void setInvoiceHead(String invoiceHead) {
        this.invoiceHead = invoiceHead;
    }

    public String getLinkUserName() {
        return linkUserName;
    }

    public void setLinkUserName(String linkUserName) {
        this.linkUserName = linkUserName;
    }

    public Integer getBookType() {
        return bookType;
    }

    public void setBookType(Integer bookType) {
        this.bookType = bookType;
    }

    public List<ItripOrderLinkUser> getItripOrderLinkUserList() {
        return itripOrderLinkUserList;
    }

    public void setItripOrderLinkUserList(List<ItripOrderLinkUser> itripOrderLinkUserList) {
        this.itripOrderLinkUserList = itripOrderLinkUserList;
    }
}
