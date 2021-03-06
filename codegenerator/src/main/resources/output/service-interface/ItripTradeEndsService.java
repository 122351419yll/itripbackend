package com.ytzl.itrip.service;
import com.ytzl.itrip.beans.model.ItripTradeEnds;
import java.util.List;
import java.util.Map;
import com.ytzl.itrip.utils.common.Page;


public interface ItripTradeEndsService {

    public ItripTradeEnds getById(Long id)throws Exception;

    public List<ItripTradeEnds>	getListByMap(Map<String,Object> param)throws Exception;

    public Integer getCountByMap(Map<String,Object> param)throws Exception;

    public Integer save(ItripTradeEnds itripTradeEnds)throws Exception;

    public Integer modify(ItripTradeEnds itripTradeEnds)throws Exception;

    public Integer removeById(Long id)throws Exception;

    public Page<List<ItripTradeEnds>> queryPageByMap(Map<String,Object> param,Integer pageNo,Integer pageSize)throws Exception;
}
