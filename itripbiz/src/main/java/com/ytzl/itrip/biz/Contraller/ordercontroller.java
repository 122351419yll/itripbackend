package com.ytzl.itrip.biz.Contraller;
import com.alibaba.fastjson.JSON;
import com.ytzl.itrip.beans.model.*;
import com.ytzl.itrip.biz.Service.*;
import com.ytzl.itrip.dao.mapper.*;
import com.ytzl.itrip.utils.common.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import javax.annotation.Resource;
import javax.print.Doc;
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
    @Resource
    private ItripHotelTempStoreService itripHotelTempStoreService;

    //根据订单id查看个人情况
    @RequestMapping(value = "/hotelorder/getpersonalorderinfo/{orderId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    private Dto getpersonalorderinfo(@PathVariable(value = "orderId") long orderId, HttpServletRequest requestr) {
        String token = requestr.getHeader("token");
        ItripUser itripUser = validationUtil.getUser(token);
        if (itripUser == null) {
            return DtoUtil.returnFail("token失效,请重新登陆", "100000");
        }
        //创建返回的工具类
        ItripPersonalHotelOrderVo itripPersonalHotelOrderVo =new ItripPersonalHotelOrderVo();
        try {
            ItripHotelOrder itripHotelOrder =itripHotelOrderService.getById(orderId);
            //向工具类传入数据
            BeanUtils.copyProperties(itripHotelOrder,itripPersonalHotelOrderVo);
            //根据酒店订单中的id查询房间信息
            ItripHotelRoom itripHotelRoom = itripHotelRoomService.getById(orderId);
            if (itripHotelOrder!=null){
                itripPersonalHotelOrderVo.setPayType(itripHotelRoom.getPayType());
            }
            /**
             * 添加订单流程
             *       orderStatusSuccess=
             *           {"1":"订单提交","2":"订单支付","3":"支付成功",
             *               "4":"入住","5":"订单点评","6":"订单完成"}
             *       orderStatusCancel={"1":"订单提交","2":"订单支付","3":"订单取消"}
             *
             * 订单状态 getOrderStatus
             *   （0：待支付 1:已取消 2:支付成功 3:已消费 4：已点评）
             */
            //去配置文件读取流程信息 （↑） →↓
            String orderStatusSuccess=PropertiesUtils.get("public.properties","orderStatusSuccess");
            String orderStatusCancel=PropertiesUtils.get("public.properties","orderStatusCancel");
            if(itripHotelOrder.getOrderStatus()==0){
                itripPersonalHotelOrderVo.setOrderProcess(JSON.parse(orderStatusSuccess));
                itripPersonalHotelOrderVo.setProcessNode("2");
            }
            if(itripHotelOrder.getOrderStatus()==1){
                itripPersonalHotelOrderVo.setOrderProcess(JSON.parse(orderStatusCancel));
                itripPersonalHotelOrderVo.setProcessNode("3");
            }
            if(itripHotelOrder.getOrderStatus()==2){
                itripPersonalHotelOrderVo.setOrderProcess(JSON.parse(orderStatusSuccess));
                itripPersonalHotelOrderVo.setProcessNode("3");
            }
            if(itripHotelOrder.getOrderStatus()==3){
                itripPersonalHotelOrderVo.setOrderProcess(JSON.parse(orderStatusSuccess));
                itripPersonalHotelOrderVo.setProcessNode("5");
            }
            if(itripHotelOrder.getOrderStatus()==4){
                itripPersonalHotelOrderVo.setOrderProcess(JSON.parse(orderStatusSuccess));
                itripPersonalHotelOrderVo.setProcessNode("6");
            }
            return DtoUtil.returnSuccess("获取个人订单信息成功",itripPersonalHotelOrderVo);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("获取个人订单信息失败","100000");
        }
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
    @RequestMapping(value = "/hotelorder/addhotelorder", method = RequestMethod.POST, produces = "application/json")
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
            return DtoUtil.returnFail( "添加失败", "1000523");
        }
    }


    //根据订单id 查看个人订单详情-房型相关信息
    @RequestMapping(value = "/hotelorder/getpersonalorderroominfo/{orderId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public  Dto getpersonalorderromminfo(@PathVariable Long orderId,HttpServletRequest request)
    {
        String token = request.getHeader("token");
        ItripUser itripUser =validationUtil.getUser(token);
        try {
            if(EmptyUtils.isNotEmpty(orderId)){
                ItripPersonalOrderRoomVo itripPersonalOrderRoomVo =itripHotelOrderService.getPersonalOrderRoomVo(orderId);
                if (EmptyUtils.isNotEmpty(itripPersonalOrderRoomVo)){
                    return DtoUtil.returnSuccess("获取房型信息成功",itripPersonalOrderRoomVo);
                }else {
                    return DtoUtil.returnFail("没有与之相关订单房型信息","1000503");
                }
            }else {
                return DtoUtil.returnFail("请传递参数：orderId","1000529");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return DtoUtil.returnFail("获取信息失败","10000");
    }
    /*

    根据订单id 获取订单信息
    return
     */
    @RequestMapping(value = "/hotelorder/queryOrderById/{orderId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Dto queryOrderById(@PathVariable Long orderId,HttpServletRequest request){
        String token = request.getHeader("token");
        ItripUser itripUser = validationUtil.getUser(token);
        if (itripUser==null){
            return DtoUtil.returnFail("token失效，请重新登陆","10000");
        }
        ItriptModifyHotelOrderVo itriptModifyHotelOrderVo = new ItriptModifyHotelOrderVo();
        try {
            ItripHotelOrder itripHotelOrder = itripHotelOrderService.getById(orderId);
            BeanUtils.copyProperties(itripHotelOrder,itriptModifyHotelOrderVo);
            Map map = new HashMap();
            map.put("orderId",itripHotelOrder.getId());
            List<ItripOrderLinkUser> itripHotelOrders = itripOrderLinkUserService.getListByMap(map);
            itriptModifyHotelOrderVo.setItripOrderLinkUserList(itripHotelOrders);
            return DtoUtil.returnSuccess("获取订单成功",itriptModifyHotelOrderVo);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("获取订单失败","100000");
        }
    }
    //支付成功后查询订单
    @RequestMapping(value = "/querysuccessorderinfo/{id}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
   public Dto querysuccessorderinfo(@PathVariable("id")long id,HttpServletRequest request){
        String token = request.getHeader("token");
        ItripUser itripUser = validationUtil.getUser(token);
        if (itripUser==null){
            return DtoUtil.returnFail("token失效","100000");
        }
        try {
            ItripHotel itripHotel = itripHotelService.getById(id);
            return  DtoUtil.returnSuccess("true",itripHotel);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常","100000");
        }
    }
    //扫描中间表执行更新库存的操作
    @RequestMapping(value = "/hotelorder/scanTradeEnd ",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto scanTradeEnd(@RequestParam("orderNo")String orderNo){
        try {
            ItripHotelOrder itripHotelOrder = itripHotelOrderService.getHotelOrderByOrderNo(orderNo);

            ItripTradeEnds itripTradeEnds = new ItripTradeEnds();
            //订单ID
            itripTradeEnds.setId(itripHotelOrder.getId());
            //订单编号(注意非支付宝交易编号tradeNo)
            itripTradeEnds.setOrderNo(orderNo);
            //flag标识字段(默认0：未处理；1：处理中)
            itripTradeEnds.setFlag("1");
            try {
                //修改中间表为处理中
                itripTradeEndsService.modify(itripTradeEnds);
                //删减库存
                Map map=new HashMap();
                map.put("roomId",itripHotelOrder.getRoomId());
                map.put("count",itripHotelOrder.getCount());
                map.put("startTime",itripHotelOrder.getCheckInDate());
                map.put("endTime",itripHotelOrder.getCheckOutDate());
                itripHotelTempStoreService.modifyHotelStore(map);
                //id、订单编号上方已经赋过值 所以只需修改flag 这个属性即可
                itripTradeEnds.setFlag("2");
                //修改中间表为处理完成
                itripTradeEndsService.modify(itripTradeEnds);
                return DtoUtil.returnSuccess("true");
            } catch (Exception e) {
                return DtoUtil.returnFail("系统异常","1000000");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常","100000");
        }
    }
    @RequestMapping(value = "/hotelorder/updateorderstatusandpaytype",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto updateorderstatusandpaytype(@RequestBody ItriptModifyHotelOrderVo itriptModifyHotelOrderVo,
                                           HttpServletRequest request){
        String token=request.getHeader("token");
        ItripUser itripUser=validationUtil.getUser(token);
        if(itripUser==null){
            return DtoUtil.returnFail("Token失效，请重新登录","100000");
        }
        if(EmptyUtils.isEmpty(itriptModifyHotelOrderVo)){
            return DtoUtil.returnFail("不能提交空，请填写订单信息","100000");
        }
        Map map = new HashMap();
        map.put("hotelId",itriptModifyHotelOrderVo.getHotelId());
        //拿hotelId去查询房型
        try {
            List<ItripHotelRoom> itripHotelRooms=itripHotelRoomService.getListByMap(map);
            Integer payType=itripHotelRooms.get(0).getPayType();
            if((itriptModifyHotelOrderVo.getPayType()==3 || itriptModifyHotelOrderVo.getPayType()==1)&&payType==itriptModifyHotelOrderVo.getPayType()){
                ItripHotelOrder itripHotelOrder = new ItripHotelOrder();
                //将itripModifyHotelOrderVO中的值复制到itripHotelOrder
                BeanUtils.copyProperties(itriptModifyHotelOrderVo,itripHotelOrder);
                itripHotelOrderService.modify(itripHotelOrder);
                return DtoUtil.returnSuccess("true");
            }else {
                return DtoUtil.returnFail("该房间不支持线下支付类型","100521");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常","10000");
        }
    }
}

