package com.ytzl.itrip.auth.controller;
import com.alibaba.fastjson.JSONObject;
import com.ytzl.itrip.auth.service.ItripUserService;
import com.ytzl.itrip.auth.service.ItripUserTokenVO;
import com.ytzl.itrip.beans.model.ItripUser;
import com.ytzl.itrip.utils.common.DigestUtil;
import com.ytzl.itrip.utils.common.Dto;
import com.ytzl.itrip.utils.common.DtoUtil;
import com.ytzl.itrip.utils.common.RedisAPI;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.DispatcherServlet;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
            return DtoUtil.returnSuccess("查询成功",
                    itripUserService.getById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("查询失败",
                    "1001");
        }
    }

    @RequestMapping(value = "/dologin",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Object getuser(@RequestParam("name") String name,
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
        return null;
    }
}
