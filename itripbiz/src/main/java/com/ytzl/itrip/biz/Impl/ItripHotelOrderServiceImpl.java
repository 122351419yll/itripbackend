package com.ytzl.itrip.biz.Impl;
import com.ytzl.itrip.biz.Service.ItripHotelOrderService;
import com.ytzl.itrip.biz.Service.ItripHotelOrderService;
import com.ytzl.itrip.dao.mapper.ItripHotelOrderMapper;
import com.ytzl.itrip.beans.model.ItripHotelOrder;
import com.ytzl.itrip.dao.mapper.ItripHotelOrderSotreVo;
import com.ytzl.itrip.utils.common.EmptyUtils;
import com.ytzl.itrip.utils.common.ErrorCode;
import com.ytzl.itrip.utils.common.Page;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.ytzl.itrip.utils.common.Constants;

@Service("itripHotelOrderService")
public class ItripHotelOrderServiceImpl implements ItripHotelOrderService {

    @Resource
    private ItripHotelOrderMapper itripHotelOrderMapper;

    public ItripHotelOrder getById(Long id)throws Exception{
        return itripHotelOrderMapper.getById(id);
    }

    public List<ItripHotelOrder> getListByMap(Map<String,Object> param)throws Exception{
        return itripHotelOrderMapper.getListByMap(param);
    }

    public Integer getCountByMap(Map<String,Object> param)throws Exception{
        return itripHotelOrderMapper.getCountByMap(param);
    }

    public Integer save(ItripHotelOrder itripHotelOrder)throws Exception{
            itripHotelOrder.setCreationDate(new Date());
            return itripHotelOrderMapper.save(itripHotelOrder);
    }

    public Integer modify(ItripHotelOrder itripHotelOrder)throws Exception{
        itripHotelOrder.setModifyDate(new Date());
        return itripHotelOrderMapper.modify(itripHotelOrder);
    }

    public Integer removeById(Long id)throws Exception{
        return itripHotelOrderMapper.removeById(id);
    }

    @Override
    public void validateHotelStore(Map map) throws Exception {
        Integer count=Integer.parseInt(map.get("count").toString());
        List <ItripHotelOrderSotreVo> storeVos=itripHotelOrderMapper.queryHotelStore(map);
        if(null==storeVos||storeVos.size()<0){
            throw new Exception("入住时间必须大于退房时间");
        }
        if(storeVos.get(0).getStore()<count){
            throw new Exception("库存不足");
        }
    }

    @Override
    public List<ItripHotelOrderSotreVo> queryHotelStore(Map map) throws Exception {
        itripHotelOrderMapper.flushStore(map);
        return itripHotelOrderMapper.queryHotelStore(map);
    }

    public Page<List<ItripHotelOrder>> queryPageByMap(Map<String,Object> param,Integer pageNo,Integer pageSize)throws Exception{
        Integer total = itripHotelOrderMapper.getCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? Constants.DEFAULT_PAGE_NO : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? Constants.DEFAULT_PAGE_SIZE : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<ItripHotelOrder> itripHotelOrderList = itripHotelOrderMapper.getListByMap(param);
        page.setRows(itripHotelOrderList);
        return page;
    }

}
