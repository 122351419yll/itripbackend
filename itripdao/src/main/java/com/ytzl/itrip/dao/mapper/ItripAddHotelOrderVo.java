package com.ytzl.itrip.dao.mapper;

import com.ytzl.itrip.beans.model.ItripUserLinkUser;

import java.util.List;

/**
 * Created by l骆明 on 2018/8/20.
 */
public class ItripAddHotelOrderVo {

    private String checkInDate;
    //退房时间
    private String checkOutDate;
    //消耗数量
    private Integer count;
    //酒店ID
    private Long hotelId;
    //酒店名称
    private String hotelName;
    //[修改订单时为必填，注:订单id]
    private Long id;
    //发票抬头
    private String invoiceHead;
    //[非必填，注：接收数字类型] 发票类型（0：个人 1：公司）
    private Integer invoiceType;
    //[非必填，注：接收数字类型] 是否需要发票（0：不需要 1：需要）
    private Integer isNeedInvoice;
    //[必填] 入住人(只传递：id、linkUserName)ItripUserLinkUser
    private List<ItripUserLinkUser> linkUser;
    //[非必填] 联系邮箱
    private String noticeEmail;
    // [非必填] 联系手机号
    private String noticePhone;
    //[必填，注：接收数字类型] 订单类型(0:旅游产品 1:酒店产品 2：机票产品)
    private Integer orderType;
    //[必填] 房间ID
    private Long roomId;
    //[非必填] 特殊需求
    private String specialRequirement;

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
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

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceHead() {
        return invoiceHead;
    }

    public void setInvoiceHead(String invoiceHead) {
        this.invoiceHead = invoiceHead;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public Integer getIsNeedInvoice() {
        return isNeedInvoice;
    }

    public void setIsNeedInvoice(Integer isNeedInvoice) {
        this.isNeedInvoice = isNeedInvoice;
    }

    public List<ItripUserLinkUser> getLinkUser() {
        return linkUser;
    }

    public void setLinkUser(List<ItripUserLinkUser> linkUser) {
        this.linkUser = linkUser;
    }

    public String getNoticeEmail() {
        return noticeEmail;
    }

    public void setNoticeEmail(String noticeEmail) {
        this.noticeEmail = noticeEmail;
    }

    public String getNoticePhone() {
        return noticePhone;
    }

    public void setNoticePhone(String noticePhone) {
        this.noticePhone = noticePhone;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getSpecialRequirement() {
        return specialRequirement;
    }

    public void setSpecialRequirement(String specialRequirement) {
        this.specialRequirement = specialRequirement;
    }

    public ItripAddHotelOrderVo() {
    }
}
