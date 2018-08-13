package com.ytzl.itrip.dao.mapper;

import com.ytzl.itrip.beans.model.ItripAreaDic;
import com.ytzl.itrip.beans.model.ItripHotel;
import com.ytzl.itrip.beans.model.ItripHotelFeature;
import com.ytzl.itrip.beans.model.ItripLabelDic;

import java.util.List;

/**
 * Created by l骆明 on 2018/8/10.
 */
public class itripfeatureVo {
    private String hotelName;
    private List<ItripAreaDic> tradingAreaNameList;
    private List<ItripLabelDic> hotelFeatureList;

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public List<ItripAreaDic> getTradingAreaNameList() {
        return tradingAreaNameList;
    }

    public List<ItripLabelDic> getHotelFeatureList() {
        return hotelFeatureList;
    }

    public void setHotelFeatureList(List<ItripLabelDic> hotelFeatureList) {
        this.hotelFeatureList = hotelFeatureList;
    }

    public void setTradingAreaNameList(List<ItripAreaDic> tradingAreaNameList) {
        this.tradingAreaNameList = tradingAreaNameList;
    }


    public itripfeatureVo() {
    }


}
