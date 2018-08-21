package com.ytzl.itrip.biz.Contraller;

import com.ytzl.itrip.beans.model.ItripHotelOrder;
import com.ytzl.itrip.beans.model.ItripOrderLinkUser;
import com.ytzl.itrip.beans.model.ItripUser;
import com.ytzl.itrip.beans.model.ItripUserLinkUser;
import com.ytzl.itrip.biz.Service.ItripHotelOrderService;
import com.ytzl.itrip.biz.Service.ItripOrderLinkUserService;
import com.ytzl.itrip.biz.Service.ItripUserLinkUserService;
import com.ytzl.itrip.dao.mapper.*;
import com.ytzl.itrip.utils.common.Dto;
import com.ytzl.itrip.utils.common.DtoUtil;
import com.ytzl.itrip.utils.common.ValidationUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class userInfoController {
    @Resource
    private ValidationUtil validationUtil;
    @Resource
    private ItripUserLinkUserService itripUserLinkUserService;
    @Resource
    private ItripOrderLinkUserService itripOrderLinkUserService;
    @Resource
    private ItripHotelOrderService itripHotelOrderService;

    @RequestMapping(value = "/userinfo/queryuserlinkuser",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto queryuserlinkuser(@RequestBody ItripSearchUserLinkUserVO itripSearchUserLinkUserVO,
                                 HttpServletRequest  request){
        String token = request.getHeader("token");
        ItripUser itripUser = validationUtil.getUser(token);
        if(itripUser!=null){
            Map map = new HashMap();
            if(itripSearchUserLinkUserVO.getLinkUserName()!=null){
                map.put("linkUserName",itripSearchUserLinkUserVO.getLinkUserName());
            }
            map.put("userId",itripUser.getId());
            try {
                List<ItripUserLinkUser> itripUserLinkUsers = itripUserLinkUserService.getListByMap(map);
                return DtoUtil.returnSuccess("成功",itripUserLinkUsers);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return DtoUtil.returnFail("获取常用联系人失败","1000401");
    }
    //增加联系人
    @RequestMapping(value = "/userinfo/adduserlinkuser",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto userInfo(@RequestBody ItripAddUserLinkUserVO itripAddUserLinkUserVO, HttpServletRequest request) {
        String token = request.getHeader("token");
        ItripUser itripUser = validationUtil.getUser(token);
        itripAddUserLinkUserVO.setUserId(itripUser.getId());
        itripAddUserLinkUserVO.setLinkIdCardType(0);
        if (itripUser != null) {
            ItripUserLinkUser itripUserLinkUser = new ItripUserLinkUser();
            BeanUtils.copyProperties(itripAddUserLinkUserVO, itripUserLinkUser);
            try {
                Integer intUserLinkUser = itripUserLinkUserService.save(itripUserLinkUser);
                if (intUserLinkUser != 0) {
                    return DtoUtil.returnSuccess("成功", intUserLinkUser);
                } else {
                    return DtoUtil.returnFail("不能提交空，请填写常用联系人", "100412");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return DtoUtil.returnFail("token失效", "100000");
        }

        return DtoUtil.returnFail("新增常用联系人失败", "100411");
    }
    //修改常用联系人
    @RequestMapping(value = "/userinfo/modifyuserlinkuser",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto modifyuserlinkuser(@RequestBody ItripModifyUserLinkUserVO itripModifyUserLinkUserVO,HttpServletRequest request){
        String token = request.getHeader("token");
        ItripUser itripUser = validationUtil.getUser(token);
        itripModifyUserLinkUserVO.setUserId(itripUser.getId());
        itripModifyUserLinkUserVO.setLinkIdCardType(0);
        if(itripUser!=null){
            ItripUserLinkUser itripUserLinkUser = new ItripUserLinkUser();
            BeanUtils.copyProperties(itripModifyUserLinkUserVO,itripUserLinkUser);
            itripUserLinkUser.setId(Long.valueOf(itripModifyUserLinkUserVO.getId()));
            try {
                Integer list= itripUserLinkUserService.modify(itripUserLinkUser);
                if(list!=0){
                    return DtoUtil.returnSuccess("修改成功",list);
                }else {
                    return DtoUtil.returnFail("修改信息失败","100421");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            return DtoUtil.returnFail("token失效","100000");
        }
        return DtoUtil.returnFail("修改信息失败","100421");
    }


    //删除常用联系人
    @RequestMapping(value = "/userinfo/deluserlinkuser",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto deluserlinkuser(@RequestParam(value = "ids") Long ids,HttpServletRequest request){
        String token = request.getHeader("token");
        ItripUser itripUser = validationUtil.getUser(token);
        if(itripUser!=null){
            try {
                Map map = new HashMap();
                map.put("linkUserId",ids);
                List<ItripOrderLinkUser> itripOrderLinkUsers= null;
                try {
                    itripOrderLinkUsers = itripOrderLinkUserService.getListByMap(map);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Boolean flag=true;
                for (ItripOrderLinkUser itripOrderLinkUser:itripOrderLinkUsers) {
                    Map map1=new HashMap();
                    map1.put("id",itripOrderLinkUser.getOrderId());
                    List<ItripHotelOrder> itripHotelOrders= null;
                    try {
                        itripHotelOrders = itripHotelOrderService.getListByMap(map1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    for (ItripHotelOrder itripHotelOrder:itripHotelOrders) {
                        if(itripHotelOrder.getOrderStatus()==0){
                            flag=false;
                            return DtoUtil.returnFail("有订单无法删除","1000431");
                        }
                }
                    if(true){
                        Integer deluser = itripUserLinkUserService.removeById(ids);
                        if(deluser!=0){
                            return DtoUtil.returnSuccess("删除成功",deluser);
                        }
                    } else {
                        return DtoUtil.returnFail("删除失败","1000432");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return DtoUtil.returnFail("删除常用联系人失败","100432");
    }
}

