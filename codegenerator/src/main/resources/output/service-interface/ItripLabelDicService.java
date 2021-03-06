package com.ytzl.itrip.service;
import com.ytzl.itrip.beans.model.ItripLabelDic;
import java.util.List;
import java.util.Map;
import com.ytzl.itrip.utils.common.Page;


public interface ItripLabelDicService {

    public ItripLabelDic getById(Long id)throws Exception;

    public List<ItripLabelDic>	getListByMap(Map<String,Object> param)throws Exception;

    public Integer getCountByMap(Map<String,Object> param)throws Exception;

    public Integer save(ItripLabelDic itripLabelDic)throws Exception;

    public Integer modify(ItripLabelDic itripLabelDic)throws Exception;

    public Integer removeById(Long id)throws Exception;

    public Page<List<ItripLabelDic>> queryPageByMap(Map<String,Object> param,Integer pageNo,Integer pageSize)throws Exception;
}
