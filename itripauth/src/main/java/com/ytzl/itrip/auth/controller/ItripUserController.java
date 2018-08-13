package com.ytzl.itrip.auth.controller;
import com.alibaba.fastjson.JSONObject;
import com.ytzl.itrip.auth.service.ItripUserService;
import com.ytzl.itrip.auth.service.ItripUserTokenVO;
import com.ytzl.itrip.beans.model.ItripPhone;
import com.ytzl.itrip.beans.model.ItripUser;
import com.ytzl.itrip.beans.model.ItripUserVo;
import com.ytzl.itrip.utils.common.DigestUtil;
import com.ytzl.itrip.utils.common.Dto;
import com.ytzl.itrip.utils.common.DtoUtil;
import com.ytzl.itrip.utils.common.RedisAPI;
import org.springframework.beans.BeanUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by Administrator on 2018/7/10.
 */
@Controller
@RequestMapping("/api")
public class ItripUserController{
    @Resource
    private ItripUserService itripUserService;
    @Resource
    private RedisAPI redisAPI;



    @RequestMapping(value = "getByid",method = RequestMethod.POST,
            produces = "application/json")

    @ResponseBody
    public Dto<ItripUser> getById(Long id){
        try {
            return DtoUtil.returnSuccess("查询 成功",
                    itripUserService.getById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("查询失败",
                    "1001");
        }
    }

    @RequestMapping(value = "/dologin",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto getuser(@RequestParam("name") String name,
                          @RequestParam("password") String password
                            ,HttpServletRequest rep){
        try {
                Map map = new HashMap();
                if (map!=null){
                 map.put("userCode",name);
                }
            List<ItripUser> finduser = itripUserService.getListByMap(map);
            ItripUser itripUser = finduser.get(0);
            if (null==itripUser){
                return DtoUtil.returnFail("用户名不正确","10001");
            }else {
                String pwd = DigestUtil.hmacSign(password,"yuntuzhilian");
                if (itripUser.getUserPassword().equals(pwd)) {
                   if (itripUser.getActivated()!=0){
                       Long time = Calendar.getInstance().getTimeInMillis();
                       String token ="token:"+"pc:"+
                               "-"+DigestUtils.md5DigestAsHex(name.getBytes())+
                               "-"+itripUser.getId()+
                               "-"+time+
                               "-"+DigestUtils.md5DigestAsHex(rep.getHeader("user-agent").getBytes()).substring(0,6);
                       redisAPI.set(token, JSONObject.toJSONString(itripUser));
                       ItripUserTokenVO itripuserTokenVo = new ItripUserTokenVO(time+60*60+1000,time,token.toString());
                       return  DtoUtil.returnSuccess("查询成功",itripuserTokenVo);
                   }else {
                       return  DtoUtil.returnFail("未激活","1001");
                   }
                }else {
                    return  DtoUtil.returnFail("密码不正确","1001");
                }
            }
        } catch (Exception e) {
                e.getMessage();
            return DtoUtil.returnFail("网络异常，登录失败","1001");
        }
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Object outlogin(HttpServletRequest request){
        String token = request.getHeader("token");
        String s = token.substring(token.length()-6,token.length());
        String[] split = token.split("-");
        if (redisAPI.exists(token)){
            String substring =DigestUtils.md5DigestAsHex(request.getHeader("user-agent").getBytes()).substring(0,6);
            if (split[4].equals(substring)){
                redisAPI.del(token);
                return DtoUtil.returnSuccess("退出成功");
            }else{
                return DtoUtil.returnFail("退出失败","1002");
            }

        }
        return  DtoUtil.returnFail("系统繁忙，请稍后再试","");

    }
    //用户注册异步刷新
    @RequestMapping(value = "/ckusr",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto zc(@RequestParam("name")String userCode){
        try {
            ItripUser user =itripUserService.getusercode(userCode);
            if (!user.getUserCode().equals(userCode)){
                return DtoUtil.returnSuccess();
            }else{
                return DtoUtil.returnFail("用户已注册","30002");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  DtoUtil.returnFail("系统繁忙，请稍后再试","");
    }
    //用户注册
    @RequestMapping(value = "/doregister",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody

    public Dto doregister(@RequestBody ItripUserVo itripUserVo){
        ItripUser itripUser = new ItripUser();
        BeanUtils.copyProperties(itripUserVo,itripUser);
        try {
            itripUserService.getuser(itripUser);
            return DtoUtil.returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  DtoUtil.returnFail("系统繁忙，请稍后再试","");
    }
    //用户注册激活
    @RequestMapping(value = "/activate",method = RequestMethod.PUT,produces = "application/json")
    @ResponseBody
    public Dto activ(String user,String code){
        try {
            ItripUser useryz  = itripUserService.getusercode(user);
            if(useryz!=null){
                if(redisAPI.exists("activate"+useryz.getUserCode())){
                    if(redisAPI.get("activate"+useryz.getUserCode()).equals(code)){
                        useryz.setActivated(1);
                        itripUserService.modify(useryz);
                        return DtoUtil.returnSuccess("激活成功");
                    }
                }
            }
            return DtoUtil.returnFail("激活失败","30002");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    //手机用户注册
    @RequestMapping(value = "/registerbyphone",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto phone(@RequestBody ItripPhone itripPhone){
        ItripUser itripUser= new ItripUser();
        BeanUtils.copyProperties(itripPhone,itripUser);
        try {

            itripUserService.phone(itripUser);
            return DtoUtil.returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  DtoUtil.returnFail("系统繁忙，请稍后再试","");

    }
    @RequestMapping(value = "/validatephone",method = RequestMethod.PUT,produces = "application/json")
    @ResponseBody
    public Dto vailphone(String user,String code){
        try {
            ItripUser userphone = itripUserService.getusercode(user);
            if (userphone!=null){
                if (redisAPI.exists("phone"+userphone.getUserCode())){
                    if(redisAPI.get("phone"+userphone.getUserCode()).equals(code)){
                        userphone.setActivated(1);
                        itripUserService.modify(userphone);
                        return DtoUtil.returnSuccess("激活成功");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  DtoUtil.returnFail("系统繁忙，请稍后再试","");

    }



}