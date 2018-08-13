package com.ytzl.itrip.auth.controller;

import com.ytzl.itrip.utils.common.Dto;
import com.ytzl.itrip.utils.common.DtoUtil;
import com.ytzl.itrip.utils.common.ErrorCode;
import com.ytzl.itrip.utils.common.RedisAPI;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2018/8/8.
 */
@Controller
@RequestMapping("/api")
public class TokenContoller {
    @Resource
    private  RedisAPI redisAPI;
    //token置换
    @RequestMapping(value = "/retoken",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto token(HttpServletRequest request){
        String token=request.getHeader("token");
        String[] splits = token.split("-");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            Date pase=simpleDateFormat.parse(splits[3]);
            String format = simpleDateFormat.format(pase);
            String date = simpleDateFormat.format(new Date(Calendar.getInstance().getTimeInMillis()));

            if(format.substring(0,8).equals(date.substring(0,8))){
                if (format.substring(8,10).equals(date.substring(8,10))){
                   int i = Integer.valueOf(date.substring(10,12))-Integer.valueOf(format.substring(10,12));
                   if(i>20){
                       String s = redisAPI.get(token);
                       redisAPI.del(token);
                       redisAPI.set(token,s,60*3);
                       return DtoUtil.returnFail("会话已经过期", ErrorCode.AUTH_TOKEN_INVALID);
                   }else {
                       String j = redisAPI.get(token);
                       redisAPI.set(token,j,60*3);
                   }
                }else{
                    int m = Integer.valueOf(date.substring(8,10))-Integer.valueOf(format.substring(8,10));
                    if (m>1){
                        String h = redisAPI.get(token);
                        redisAPI.del(token);
                        redisAPI.set(token,h,60*3);
                        return  DtoUtil.returnFail("会话已经过期",ErrorCode.AUTH_TOKEN_INVALID);
                    }else {
                        String s = redisAPI.get(token);
                        redisAPI.set(token,s,60*3);
                    }
                }
            }else {
                redisAPI.del(token);
                return DtoUtil.returnFail("会话已经过期",ErrorCode.AUTH_TOKEN_INVALID);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return DtoUtil.returnSuccess();
    }
}
