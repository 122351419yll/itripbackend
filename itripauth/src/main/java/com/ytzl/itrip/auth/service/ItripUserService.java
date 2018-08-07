package com.ytzl.itrip.auth.service;
import com.ytzl.itrip.beans.model.ItripUser;
import java.util.List;
import java.util.Map;
import com.ytzl.itrip.utils.common.Page;


public interface ItripUserService {

    public ItripUser getById(Long id)throws Exception;

    public List<ItripUser>	getListByMap(Map<String, Object> param)throws Exception;

    public Integer getCountByMap(Map<String, Object> param)throws Exception;

    public Integer save(ItripUser itripUser)throws Exception;

    public Integer modify(ItripUser itripUser)throws Exception;

    public Integer removeById(Long id)throws Exception;
    //用户注册异步刷新
    public ItripUser getusercode(String usercode) throws  Exception;

    //用户注册
    public Integer getuser(ItripUser itripUser) throws Exception;

    //用户手机注册
    public Integer phone(ItripUser itripUser) throws  Exception;

    public List<ItripUserTokenVO> find(String userCode,String password);

    public Page<List<ItripUser>> queryPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize)throws Exception;
}
