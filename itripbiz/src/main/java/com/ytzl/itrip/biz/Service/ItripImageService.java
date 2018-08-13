package com.ytzl.itrip.biz.Service;
import com.ytzl.itrip.beans.model.ItripImage;
import java.util.List;
import java.util.Map;
import com.ytzl.itrip.utils.common.Page;


public interface ItripImageService {

    public ItripImage getById(Long id)throws Exception;

    public List<ItripImage>	getListByMap(Map<String, Object> param)throws Exception;

    public Integer getCountByMap(Map<String, Object> param)throws Exception;

    public Integer save(ItripImage itripImage)throws Exception;

    public Integer modify(ItripImage itripImage)throws Exception;

    public Integer removeById(Long id)throws Exception;

    public Page<List<ItripImage>> queryPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize)throws Exception;
}
