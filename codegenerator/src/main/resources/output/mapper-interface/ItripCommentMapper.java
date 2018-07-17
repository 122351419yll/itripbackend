package com.ytzl.itrip.dao.mapper;
import com.ytzl.itrip.beans.model.ItripComment;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface ItripCommentMapper {

	public ItripComment getById(@Param(value = "id") Long id)throws Exception;

	public List<ItripComment>	getListByMap(Map<String,Object> param)throws Exception;

	public Integer getCountByMap(Map<String,Object> param)throws Exception;

	public Integer save(ItripComment itripComment)throws Exception;

	public Integer modify(ItripComment itripComment)throws Exception;

	public Integer removeById(@Param(value = "id") Long id)throws Exception;

}
