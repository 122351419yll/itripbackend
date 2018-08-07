package com.ytzl.itrip.auth.service.Impl;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.ytzl.itrip.auth.service.ItripUserService;
import com.ytzl.itrip.auth.service.ItripUserTokenVO;
import com.ytzl.itrip.auth.service.phoneUtil;
import com.ytzl.itrip.beans.model.ItripUser;
import com.ytzl.itrip.dao.mapper.ItripUserMapper;
import com.ytzl.itrip.utils.common.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("itripUserService")
public class ItripUserServiceImpl implements ItripUserService {
    @Resource
    private SimpleMailMessage activationMailMessage;
    @Resource
    private JavaMailSenderImpl mailSender;

    @Resource
    private RedisAPI redisAPI;
    //邮箱注册
    public   void sendActivationMail(String mailTo, String activationCode) {
        activationMailMessage.setTo(mailTo);
        activationMailMessage.setText("注册邮箱："+mailTo +"  激活码："+activationCode);
        mailSender.send(activationMailMessage);
    }

    //手机注册
    @Override
    public Integer phone(ItripUser itripUser) throws Exception {
        itripUser.setActivated(0);
        itripUser.setCreationDate(new Date());
        itripUser.setUserPassword(DigestUtil.hmacSign(itripUser.getUserPassword(),"yuntuzhilian"));
        Integer i=itripUserMapper.save(itripUser);
        if (i!=0){
            Integer activationCode = DigestUtil.randomCode();
            redisAPI.set("phone"+itripUser.getUserCode(),""+activationCode);
            phoneUtil.senAcativalPhone(itripUser.getUserCode(),activationCode.toString());
            return i;
        }
        return null;
    }

    //用户实际注册
    @Override

    public Integer getuser(ItripUser itripUser) throws Exception {
        itripUser.setCreationDate(new Date());
        itripUser.setActivated(0);
        itripUser.setUserPassword(DigestUtil.hmacSign(itripUser.getUserPassword(),"yuntuzhilian"));
        Integer i=itripUserMapper.save(itripUser);
        if (i!=0){
            Integer activationCode = DigestUtil.randomCode();
            redisAPI.set("activate"+itripUser.getUserCode(),""+activationCode,60*3);
            sendActivationMail(itripUser.getUserCode(),""+activationCode);
            return  i;
        }else{
            return  null;
        }


    }

    //用户注册前的异步刷新
    @Override
    public ItripUser getusercode(String usercode) throws Exception {
        Map map = new HashMap<>();
        if (usercode!=null){
            map.put("usercode",usercode);
        }
        List<ItripUser> list = getListByMap(map);
        if (list==null || list.size()==0){
            return null;
        }
        return list.get(0);


    }

    @Resource
    private ItripUserMapper itripUserMapper;

    public ItripUser getById(Long id)throws Exception{
        return itripUserMapper.getById(id);
    }

    public List<ItripUser> getListByMap(Map<String,Object> param)throws Exception{
        return itripUserMapper.getListByMap(param);
    }

    public Integer getCountByMap(Map<String,Object> param)throws Exception{
        return itripUserMapper.getCountByMap(param);
    }

    public Integer save(ItripUser itripUser)throws Exception{
            itripUser.setCreationDate(new Date());
            return itripUserMapper.save(itripUser);
    }

    public Integer modify(ItripUser itripUser)throws Exception{
        itripUser.setModifyDate(new Date());
        return itripUserMapper.modify(itripUser);
    }

    public Integer removeById(Long id)throws Exception{
        return itripUserMapper.removeById(id);
    }

    public List<ItripUserTokenVO> find(String userCode,String password){
        return null;
    }
    public Page<List<ItripUser>> queryPageByMap(Map<String,Object> param,Integer pageNo,Integer pageSize)throws Exception{
        Integer total = itripUserMapper.getCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? Constants.DEFAULT_PAGE_NO : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? Constants.DEFAULT_PAGE_SIZE : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<ItripUser> itripUserList = itripUserMapper.getListByMap(param);
        page.setRows(itripUserList);
        return page;
    }


}
