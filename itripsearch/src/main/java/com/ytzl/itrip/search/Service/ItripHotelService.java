package com.ytzl.itrip.search.Service;


import com.ytzl.itrip.search.beans.SearchHotCityVO;
import com.ytzl.itrip.search.beans.SearchHotelVO;
import com.ytzl.itrip.utils.common.Page;


import java.util.List;

/**
 * Created by Administrator on 2018/8/8.
 */
public interface ItripHotelService<T> {
    //查询酒店分页
    public Page<T> searchItripHotelPage(SearchHotelVO searchHotelVO) throws Exception;
    //根据热门城市查询酒店
    public List<T> searchItripHotelListByHotCity(SearchHotCityVO searchHotCityVO) throws Exception;
}
