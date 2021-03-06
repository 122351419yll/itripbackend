package com.ytzl.itrip.service;
import com.ytzl.itrip.beans.model.ItripHotelRoom;
import java.util.List;
import java.util.Map;
import com.ytzl.itrip.utils.common.Page;


public interface ItripHotelRoomService {

    public ItripHotelRoom getById(Long id)throws Exception;

    public List<ItripHotelRoom>	getListByMap(Map<String,Object> param)throws Exception;

    public Integer getCountByMap(Map<String,Object> param)throws Exception;

    public Integer save(ItripHotelRoom itripHotelRoom)throws Exception;

    public Integer modify(ItripHotelRoom itripHotelRoom)throws Exception;

    public Integer removeById(Long id)throws Exception;

    public Page<List<ItripHotelRoom>> queryPageByMap(Map<String,Object> param,Integer pageNo,Integer pageSize)throws Exception;
}
