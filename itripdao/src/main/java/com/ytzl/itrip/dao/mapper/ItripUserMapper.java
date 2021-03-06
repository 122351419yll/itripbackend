package com.ytzl.itrip.dao.mapper;
import com.ytzl.itrip.beans.model.ItripUser;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface ItripUserMapper {

	public ItripUser getById(@Param(value = "id") Long id)throws Exception;

	public List<ItripUser>	getListByMap(Map<String, Object> param)throws Exception;

	public Integer getCountByMap(Map<String, Object> param)throws Exception;

	public Integer save(ItripUser itripUser)throws Exception;

	public Integer modify(ItripUser itripUser)throws Exception;

	public Integer removeById(@Param(value = "id") Long id)throws Exception;

}
