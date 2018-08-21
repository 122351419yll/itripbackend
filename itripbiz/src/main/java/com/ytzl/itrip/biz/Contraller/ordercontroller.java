package com.ytzl.itrip.biz.Contraller;
import com.ytzl.itrip.beans.model.*;
import com.ytzl.itrip.biz.Service.*;
import com.ytzl.itrip.dao.mapper.*;
import com.ytzl.itrip.utils.common.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by l骆明 on 2018/8/17.
 */
@Controller
@RequestMapping("/api")
public class ordercontroller {
    @Resource
    private ValidationUtil validationUtil;
    @Resource
    private ItripHotelService itripHotelService;
    @Resource
    private ItripHotelOrderService itripHotelOrderService;
    @Resource
    private ItripHotelRoomService itripHotelRoomService;
    @Resource
    private ItripOrderLinkUserService itripOrderLinkUserService;
    @Resource
    private ItripTradeEndsService itripTradeEndsService;

    //根据订单id查看个人情况
    @RequestMapping(value = "/hotelorder/getpersonalorderinfo/{orderId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    private Dto getpersonalorderinfo(@PathVariable(value = "orderId") long orderId, HttpServletRequest requestr) {
        String token = requestr.getHeader("token");
        ItripUser itripUser = validationUtil.getUser(token);
        if (itripUser == null) {
            return DtoUtil.returnFail("token失效,请重新登陆", "100000");
        }
        if (orderId != 0) {
            Map map = new HashMap();
            map.put("id", orderId);
            try {
                List<ItripHotel> itripHotels = itripHotelService.getListByMap(map);
                ItripordergetpersonVo itripordergetpersonVo = new ItripordergetpersonVo();
                BeanUtils.copyProperties(itripHotels, itripordergetpersonVo);
                return DtoUtil.returnSuccess("成功", itripordergetpersonVo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return DtoUtil.returnFail("请传递相关参数", "100520");
        }

        return DtoUtil.returnFail("获取个人订单信息错误", "100527");
    }

    //根据个人订单列表，并分页显示
    @RequestMapping(value = "/hotelorder/getpersonalorderlist", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Dto getpersonalorderlist(@RequestBody ItripSearchOrderVO itripSearchOrderVO, HttpServletRequest request) {
        String token = request.getHeader("token");
        ItripUser itripUser = validationUtil.getUser(token);
        if (itripUser == null) {
            return DtoUtil.returnFail("token失效，请重新登录", "100000");
        }
        Map map = new HashMap();
        map.put("userId", itripUser.getId());
        if (EmptyUtils.isNotEmpty(itripSearchOrderVO.getLinkUserName())) {
            map.put("hotelName", itripSearchOrderVO.getLinkUserName());
        }
        if (EmptyUtils.isNotEmpty(itripSearchOrderVO.getOrderNo())) {
            map.put("orderNo", itripSearchOrderVO.getOrderNo());
        }
        if (EmptyUtils.isNotEmpty(itripSearchOrderVO.getStartDate())) {
            map.put("startDate", itripSearchOrderVO.getStartDate());
        }
        if (EmptyUtils.isNotEmpty(itripSearchOrderVO.getEndDate())) {
            map.put("creationDate", itripSearchOrderVO.getEndDate());
        }
        if (itripSearchOrderVO.getOrderStatus() >= 0) {
            map.put("orderStatus", itripSearchOrderVO.getOrderStatus());
        }
        if (itripSearchOrderVO.getOrderType() >= 0) {
            map.put("orderType", itripSearchOrderVO.getOrderType());
        }
        try {
            Page<ItripHotelOrder> page = itripHotelOrderService.queryPageByMap(map, itripSearchOrderVO.getPageNo(), itripSearchOrderVO.getPageSize());
            return DtoUtil.returnSuccess("成功", page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DtoUtil.returnFail("获取个人订单失败", "100503");
    }

    //根据个人订单列表，并分页显示
    //房间价格有问题
    @RequestMapping(value = "/hotelorder/getpreorderinfo", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Dto getpersonalorder(@RequestBody ValidateRoomStoreVo validateRoomStoreVo
            , HttpServletRequest request) {
        //token是否失效
        String token = request.getHeader("token");
        ItripUser itripUser = validationUtil.getUser(token);
        if (null == itripUser) {
            return DtoUtil.returnFail("token失效，请重新登陆", "");
        }
        //酒店id不能为空
        if (EmptyUtils.isEmpty(validateRoomStoreVo.getHotelId())) {
            return DtoUtil.returnFail("酒店Id不能为空", "10000435");
        }
        //房型id不能为空
        if (EmptyUtils.isEmpty(validateRoomStoreVo.getRoomId())) {
            return DtoUtil.returnFail("房型id不能为空", "1000465");
        }
        //构建查询参数
        Map map = new HashMap();
        map.put("startTime", validateRoomStoreVo.getCheckInDate());
        map.put("endTime", validateRoomStoreVo.getCheckOutDate());
        map.put("roomId", validateRoomStoreVo.getRoomId());
        map.put("hotelId", validateRoomStoreVo.getHotelId());
        //获取酒店最小库存
        try {
            List<ItripHotelOrderSotreVo> storeVoList = itripHotelOrderService.queryHotelStore(map);
            if (EmptyUtils.isEmpty(storeVoList) || storeVoList.size() == 0) {
                return DtoUtil.returnFail("入住时间必须大于退房时间", "100023");
            }
            //创建返回结果的工具类
            ItripHotelOrderSotreVo hotelOrderSotreVo = new ItripHotelOrderSotreVo();

            //获取库存
            int store = storeVoList.get(0).getStore();

            hotelOrderSotreVo.setStore(store);
            //通过utils工具类赋值
            BeanUtils.copyProperties(validateRoomStoreVo, hotelOrderSotreVo);
            //根据酒店id获取酒店的名字
            String hotelName = itripHotelService.getById(validateRoomStoreVo.getHotelId().longValue()).getHotelName();
            //将酒店的名称放到工具类中
            hotelOrderSotreVo.setHotelName(hotelName);
            //获取酒店房间的价钱 根据房间的id
            BigDecimal roomP = itripHotelRoomService.getById(validateRoomStoreVo.getRoomId().longValue()).getRoomPrice();
            Float roomPrice = roomP.floatValue();
            hotelOrderSotreVo.setHotelPrice(roomPrice);
            return DtoUtil.returnSuccess("获取成功", hotelOrderSotreVo);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常", "100205");
        }
    }

    /**
     * 生成一个订单 生成订单总共的业务操作为
     * ：添加一条订单记录
     * ：并不需要修改库存 只是增加一条订单记录 在真正支付的时候才会修改库存
     * ：添加联系人的订单记录 ItripOrderLinkUser
     *
     * @param itripAddHotelOrderVo 添加订单的工具类
     * @param request              HttpServletRequest
     * @return 工具类
     */
    @RequestMapping(value = "/addhotelorder", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Dto addhotelorder(@RequestBody ItripAddHotelOrderVo itripAddHotelOrderVo,
                             HttpServletRequest request) {
        String token = request.getHeader("token");
        ItripUser itripUser = validationUtil.getUser(token);
        if (itripUser == null) {
            return DtoUtil.returnFail("token失效", "100023");
        }
        if (itripAddHotelOrderVo == null) {
            return DtoUtil.returnFail("不能提交空，请填写订单信息", "100023");

        }
        if (EmptyUtils.isEmpty(itripAddHotelOrderVo.getHotelId())) {
            return DtoUtil.returnFail("酒店id不能为空", "100023");
        }
        if (EmptyUtils.isEmpty(itripAddHotelOrderVo.getRoomId())) {
            return DtoUtil.returnFail("房间id不能为空", "100023");
        }
        Map map = new HashMap();
        map.put("startTime", itripAddHotelOrderVo.getCheckInDate());
        map.put("endTime", itripAddHotelOrderVo.getCheckOutDate());
        map.put("roomId", itripAddHotelOrderVo.getRoomId());
        map.put("hotelId", itripAddHotelOrderVo.getHotelId());
        map.put("count", itripAddHotelOrderVo.getCount());
        try {
            Integer Dates = null;
                //获取到入住的天数 OutDate-InDate=Date
                    Dates = DateUtil.getDate(itripAddHotelOrderVo.getCheckOutDate(), itripAddHotelOrderVo.getCheckInDate());
            if (Dates <= 0) {
                return DtoUtil.returnFail("入住时间必须大于退房时间","100523");
            }
            //判断库存是否充足
            itripHotelOrderService.validateHotelStore(map);
            ItripHotelOrder itripHotelOrder = new ItripHotelOrder();
            //----------------------------从这里开始添加订单的参数
            //生成唯一的订单号
            String OrderNO = ItripMachineCodeUtil.getMachineCode(itripAddHotelOrderVo.getRoomId().intValue());
            //添加订单号
            itripHotelOrder.setOrderNo(OrderNO);
            //添加用户id
            itripHotelOrder.setUserId(itripUser.getId());
            //入住天数
            itripHotelOrder.setBookingDays(Dates);
            //判断下单的平台 0:WEB端 1:手机端 2:其他客户端
            if (token.indexOf("PC") > 0) {
                itripHotelOrder.setBookType(0);
            } else if (token.indexOf("MOBILE") > 0) {
                itripHotelOrder.setBookType(1);
            } else {
                itripHotelOrder.setBookType(2);
            }
            //获取相关联的用户
            List<ItripUserLinkUser> linkUsers = itripAddHotelOrderVo.getLinkUser();
            String LinkUserName = "";
            for (ItripUserLinkUser itripUserLinkUser : linkUsers) {
                //用逗号拼接到一起
                LinkUserName = itripUserLinkUser.getLinkUserName() + ",";
            }
            //因为是用逗号拼接所以最后一位也是逗号 去掉逗号存到数据库
            itripHotelOrder.setLinkUserName(LinkUserName.substring(0, LinkUserName.length() - 1));
            //将剩余字段添加到酒店订单中
            BeanUtils.copyProperties(itripAddHotelOrderVo, itripHotelOrder);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            itripHotelOrder.setCheckInDate(format.parse(itripAddHotelOrderVo.getCheckInDate()));
            itripHotelOrder.setCheckOutDate(format.parse(itripAddHotelOrderVo.getCheckOutDate()));
            //获取到住房信息 主要是获取到价格
            ItripHotelRoom itripHotelRoom = null;
            itripHotelRoom = itripHotelRoomService.getById(itripAddHotelOrderVo.getRoomId());
            //一天是n间房 两天是2n间房
            Integer RoomCount = itripAddHotelOrderVo.getCount() * Dates;
            BigDecimal price = null;
            //获取到价格
            price = BigDecimalUtil.OperationASMD(RoomCount, itripHotelRoom.getRoomPrice(),
                        BigDecimalUtil.BigDecimalOprations.multiply, 2,
                        BigDecimal.ROUND_DOWN);
            itripHotelOrder.setPayAmount(price);
            itripHotelOrder.setOrderStatus(0);
            //----------------------------到这里结束添加  所有参数添加完毕  添加一条订单记录 ↓
            itripHotelOrderService.save(itripHotelOrder);
            //之后是修改订单所对应的库存
            // 在sql映射文件save方法中添加如此字段 suseGeneratedKeys="true" keyProperty="id"
            //会在添加完成后自动获取到itripHotelOrder 的id 不用再去查询
            Map map1 = new HashMap();
            //准备需要返回的参数
            map1.put("orderNo", OrderNO);
            map1.put("id", itripHotelOrder.getId());
            //添加常用联系人的订单
            // 注意ItripUserLinkUser 是常用联系人
            //ItripOrderLinkUser是常用联系人的订单
            for (ItripUserLinkUser itripUserLinkUser : linkUsers) {
                ItripOrderLinkUser itripOrderLinkUser = new ItripOrderLinkUser();
                itripOrderLinkUser.setLinkUserId(itripUserLinkUser.getId());
                itripOrderLinkUser.setLinkUserName(itripUserLinkUser.getLinkUserName());
                itripOrderLinkUser.setCreationDate(new Date());
                itripOrderLinkUser.setOrderId(itripHotelOrder.getId());
                itripOrderLinkUser.setCreatedBy(itripHotelOrder.getCreatedBy());
                //添加常用联系人订单
                itripOrderLinkUserService.save(itripOrderLinkUser);
            }
            //----------------插入订单中间表
            ItripTradeEnds itripTradeEnds = new ItripTradeEnds();
            itripTradeEnds.setId(itripHotelOrder.getId());
            itripTradeEnds.setFlag("1");
            itripTradeEnds.setOrderNo(itripHotelOrder.getOrderNo());
            itripTradeEndsService.save(itripTradeEnds);
            return DtoUtil.returnSuccess("添加订单成功", map1);
        } catch (Exception e) {
            return DtoUtil.returnFail("添加失败", "1000523");
        }
    }

}