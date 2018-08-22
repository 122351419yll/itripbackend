package com.ytzl.itrip.dao.mapper;
import com.ytzl.itrip.beans.model.ItripHotelOrder;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface ItripHotelOrderMapper {
	/**
	 * 根据订单编号获取到订单对象
	 * @param orderNo 订单编号
	 * @return 订单对象
	 * @throws Exception 自定义异常
	 */
	public ItripHotelOrder getHotelOrderByOrderNo(@Param("orderNo") String orderNo)throws Exception;

	public ItripHotelOrder getById(@Param(value = "id") Long id)throws Exception;

	public List<ItripHotelOrder>	getListByMap(Map<String, Object> param)throws Exception;

	public Integer getCountByMap(Map<String, Object> param)throws Exception;

	public Integer save(ItripHotelOrder itripHotelOrder)throws Exception;

	public Integer modify(ItripHotelOrder itripHotelOrder)throws Exception;
	//调用库存
	 void flushStore(Map<String,Object>param) throws  Exception;
	//计算库存
	public List<ItripHotelOrderSotreVo> queryHotelStore(Map map)throws Exception;

	public Integer removeById(@Param(value = "id") Long id)throws Exception;

}
