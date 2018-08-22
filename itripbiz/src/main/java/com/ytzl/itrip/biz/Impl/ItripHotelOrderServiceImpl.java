package com.ytzl.itrip.biz.Impl;
import com.ytzl.itrip.beans.model.ItripHotel;
import com.ytzl.itrip.beans.model.ItripHotelRoom;
import com.ytzl.itrip.beans.model.ItripLabelDic;
import com.ytzl.itrip.biz.Service.ItripHotelOrderService;
import com.ytzl.itrip.biz.Service.ItripHotelOrderService;
import com.ytzl.itrip.dao.mapper.*;
import com.ytzl.itrip.beans.model.ItripHotelOrder;
import com.ytzl.itrip.utils.common.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("itripHotelOrderService")
public class ItripHotelOrderServiceImpl implements ItripHotelOrderService {

    @Resource
    private ItripHotelOrderMapper itripHotelOrderMapper;
    @Resource
    private ItripHotelMapper itripHotelMapper;
    @Resource
    private ItripHotelRoomMapper itripHotelRoomMapper;
    @Resource
    private ItripLabelDicMapper itripLabelDicMapper;

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
    public ItripHotelOrder getHotelOrderByOrderNo(String orderNo) throws Exception {
        /**
         * 根据订单编号获取到订单对象
         * @param orderNo 订单编号
         * @return 订单对象
         * @throws ItriptException 自定义异常
         */
            ItripHotelOrder itripHotelOrder=itripHotelOrderMapper.getHotelOrderByOrderNo(orderNo);
            if(EmptyUtils.isEmpty(orderNo)){
                throw new Exception("请传入参数");
            }
            if(EmptyUtils.isEmpty(itripHotelOrder)){
                throw new Exception("没有相关订单信息");
            }
            return itripHotelOrder;
    }

    @Override
    public ItripPersonalOrderRoomVo getPersonalOrderRoomVo(Long orderId) throws Exception {
        ItripPersonalOrderRoomVo itripPersonalOrderRoomVo = new ItripPersonalOrderRoomVo();
        ItripHotelOrder itripHotelOrder =itripHotelOrderMapper.getById(orderId);
        if (EmptyUtils.isNotEmpty(itripHotelOrder)){
            //将值赋给工具类
            BeanUtils.copyProperties(itripHotelOrder,itripPersonalOrderRoomVo);
            //由于时间类型的值无法交换，所以要手动获取
            itripPersonalOrderRoomVo.setCheckInDate(itripHotelOrder.getCheckInDate());
            itripPersonalOrderRoomVo.setCheckOutDate(itripHotelOrder.getCheckOutDate());
            itripPersonalOrderRoomVo.setCheckInWeek(DateUtil.getTodayDayOfWeek(itripHotelOrder.getCheckInDate()));
            itripPersonalOrderRoomVo.setCheckOutWeek(DateUtil.getTodayDayOfWeek(itripHotelOrder.getCheckOutDate()));

            ItripHotel itripHotel = itripHotelMapper.getById(itripHotelOrder.getHotelId());
            //放入相应的字段 hotelName id 重新赋值
            //hotelLevel address
            BeanUtils.copyProperties(itripHotel, itripPersonalOrderRoomVo);
            //trip_Hotel_room -->去此查询
            ItripHotelRoom itripHotelRoom = itripHotelRoomMapper.getById(itripHotelOrder.getRoomId());
            //放入相应的字段 id  roomBedTypeId roomTitle isHavingBreakfast
            BeanUtils.copyProperties(itripHotelRoom, itripPersonalOrderRoomVo);
            //支付类型字段不一致 需手动添加
            itripPersonalOrderRoomVo.setRoomPayType(itripHotelRoom.getPayType());
            //根据roomBedTypeId;床型 去字典表查询床型名称
            ItripLabelDic itripLabelDic = itripLabelDicMapper.getById(itripHotelRoom.getRoomBedTypeId());
            //把房型名称放入工具类
            itripPersonalOrderRoomVo.setRoomBedTypeName(itripLabelDic.getName());
        }
        return itripPersonalOrderRoomVo;
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
