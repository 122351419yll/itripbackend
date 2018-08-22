package com.ytzl.itrip.dao.mapper;

import com.ytzl.itrip.beans.model.ItripImage;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * Created by l骆明 on 2018/8/16.
 */
public class Itripadd {
    private String content;//品论内容
    private long hotelId;//酒店id
    private Integer facilitiesScore ;//设施评分
    private Integer hygieneScore;//卫生评分
    private Integer isHavingImg;//是否包含图片 0：无图片 1：有图片
    private Integer isOk;//是否满意 0:有待改善 1：值得推荐
    private List<ItripImage> itripImages;//评论图片、根据用户是否上传图片
    private Integer orderId;//订单id
    private Integer positionScore;//位置评分
    private Integer productId;//房型id
    private Integer productType;//订单类型 0：旅游铲平 1：酒店铲平 2：机票产品
    private Integer serviceScore;//服务评分
    private String tripMode;//出游类型

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getHotelId() {
        return hotelId;
    }

    public void setHotelId(long hotelId) {
        this.hotelId = hotelId;
    }

    public Integer getFacilitiesScore() {
        return facilitiesScore;
    }

    public void setFacilitiesScore(Integer facilitiesScore) {
        this.facilitiesScore = facilitiesScore;
    }

    public Integer getHygieneScore() {
        return hygieneScore;
    }

    public void setHygieneScore(Integer hygieneScore) {
        this.hygieneScore = hygieneScore;
    }

    public Integer getIsHavingImg() {
        return isHavingImg;
    }

    public void setIsHavingImg(Integer isHavingImg) {
        this.isHavingImg = isHavingImg;
    }

    public Integer getIsOk() {
        return isOk;
    }

    public void setIsOk(Integer isOk) {
        this.isOk = isOk;
    }

    public List<ItripImage> getItripImages() {
        return itripImages;
    }

    public void setItripImages(List<ItripImage> itripImages) {
        this.itripImages = itripImages;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getPositionScore() {
        return positionScore;
    }

    public void setPositionScore(Integer positionScore) {
        this.positionScore = positionScore;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public Integer getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(Integer serviceScore) {
        this.serviceScore = serviceScore;
    }

    public String getTripMode() {
        return tripMode;
    }

    public void setTripMode(String tripMode) {
        this.tripMode = tripMode;
    }

    public Itripadd() {
    }
}