package com.ytzl.itrip.utils.common;

import com.alibaba.fastjson.JSON;
import com.ytzl.itrip.beans.model.ItripUser;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ValidationUtil {
    @Resource
    private RedisAPI redisAPI;
    public ItripUser getUser(String token){
        //判断token是否存在
        try {
            if(!redisAPI.exists(token)){
                return null;
            }else{
                String itripUserJson = redisAPI.get(token);
                ItripUser itripUser = JSON.parseObject(itripUserJson, ItripUser.class);
                return itripUser;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
