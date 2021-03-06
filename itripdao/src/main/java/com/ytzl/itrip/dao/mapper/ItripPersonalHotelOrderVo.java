package com.ytzl.itrip.dao.mapper;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by pang on 2018/8/8.
 */
public class ItripPersonalHotelOrderVo {
    private Long id;//订单id
    private String orderNo;//订单号
    private Integer orderStatus;//订单状态 （0：待支付 1:已取消 2:支付成功 3:已消费 4：已点评）
    private BigDecimal payAmount;//支付金额
    private Integer payType; //支付方式:1:支付宝 2:微信 3:到店付
    private Date creationDate; //预定时间
    private Integer bookType;//预定方式（0:WEB端 1:手机端 2:其他客户端）
    private Integer roomPayType;//房间支持的支付方式
    /**
     * 订单流程
     *      1：{"1":"订单提交","2":"订单支付","3":"支付成功","4":"入住","5":"订单点评","6":"订单完成"}
     *      2：{"1":"订单提交","2":"订单支付","3":"订单取消"}
     */
    private Object orderProcess;
    private String processNode;
    private String noticePhone;//联系电话

    public ItripPersonalHotelOrderVo() {
    }

    public ItripPersonalHotelOrderVo(Long id, String orderNo, Integer orderStatus, BigDecimal payAmount, Integer payType, Date creationDate, Integer bookType, Integer roomPayType, Object orderProcess, String processNode, String noticePhone) {
        this.id = id;
        this.orderNo = orderNo;
        this.orderStatus = orderStatus;
        this.payAmount = payAmount;
        this.payType = payType;
        this.creationDate = creationDate;
        this.bookType = bookType;
        this.roomPayType = roomPayType;
        this.orderProcess = orderProcess;
        this.processNode = processNode;
        this.noticePhone = noticePhone;
    }

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
}
