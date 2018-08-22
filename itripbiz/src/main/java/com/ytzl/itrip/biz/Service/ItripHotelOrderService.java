package com.ytzl.itrip.biz.Service;
import com.ytzl.itrip.beans.model.ItripHotelOrder;
import java.util.List;
import java.util.Map;

import com.ytzl.itrip.dao.mapper.ItripHotelOrderSotreVo;
import com.ytzl.itrip.dao.mapper.ItripPersonalOrderRoomVo;
import com.ytzl.itrip.utils.common.Page;


public interface ItripHotelOrderService {

    public ItripHotelOrder getById(Long id)throws Exception;

    public ItripPersonalOrderRoomVo getPersonalOrderRoomVo(Long id) throws Exception;

    public List<ItripHotelOrder>	getListByMap(Map<String, Object> param)throws Exception;

    public Integer getCountByMap(Map<String, Object> param)throws Exception;

    public Integer save(ItripHotelOrder itripHotelOrder)throws Exception;

    public Integer modify(ItripHotelOrder itripHotelOrder)throws Exception;

    public Integer removeById(Long id)throws Exception;

    public List<ItripHotelOrderSotreVo> queryHotelStore(Map map) throws  Exception;

    public void validateHotelStore(Map map)throws Exception;

    public ItripHotelOrder getHotelOrderByOrderNo(String  orderNo)throws Exception;

    public Page<List<ItripHotelOrder>> queryPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize)throws Exception;
}
