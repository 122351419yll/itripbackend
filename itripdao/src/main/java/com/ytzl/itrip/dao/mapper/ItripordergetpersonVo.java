package com.ytzl.itrip.dao.mapper;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by l骆明 on 2018/8/17.
 */
public class ItripordergetpersonVo {
    private Long id;//订单Id

    private String orderNo;//订单编号

    private Integer orderStatus;//订单状态（0：待支付 1：已取消 2：支付成功 3：已消费（已入住））

    private BigDecimal payAmount;//支付押金

    private Integer payType;//支付方式 1：支付宝 2：微信 3：到店支付

    private Date creationDate;//预定时间

    private Integer bookType;//预定方式（0：WEB端 1：手机端 2：其他客户端）

    private Integer roomPayType; //房间支持的支付方式

    /**
     *订单流程
     * 1 待付款、待评价（已消费）、未出行（支付成功）
     * 流程 ：已提交------》待支付------》支付成功------》已入住-------已点评
     * 2已取消
     * 流程：已提交——》待支付----》已取消
     *
     */
    private Object orderProcess;//用于表示走那个流程

    private String processNode;//用于表示流程走到第几步啦

    private String noticePhone;//联系电话

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getBookType() {
        return bookType;
    }

    public void setBookType(Integer bookType) {
        this.bookType = bookType;
    }

    public Integer getRoomPayType() {
        return roomPayType;
    }

    public void setRoomPayType(Integer roomPayType) {
        this.roomPayType = roomPayType;
    }

    public Object getOrderProcess() {
        return orderProcess;
    }

    public void setOrderProcess(Object orderProcess) {
        this.orderProcess = orderProcess;
    }

    public String getProcessNode() {
        return processNode;
    }

    public void setProcessNode(String processNode) {
        this.processNode = processNode;
    }

    public String getNoticePhone() {
        return noticePhone;
    }

    public void setNoticePhone(String noticePhone) {
        this.noticePhone = noticePhone;
    }

    public ItripordergetpersonVo() {
    }
}
