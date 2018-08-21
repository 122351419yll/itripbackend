package com.ytzl.itrip.biz.Impl;
import com.ytzl.itrip.biz.Service.ItripOrderLinkUserService;
import com.ytzl.itrip.biz.Service.ItripOrderLinkUserService;
import com.ytzl.itrip.dao.mapper.ItripOrderLinkUserMapper;
import com.ytzl.itrip.beans.model.ItripOrderLinkUser;
import com.ytzl.itrip.utils.common.EmptyUtils;
import com.ytzl.itrip.utils.common.Page;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.ytzl.itrip.utils.common.Constants;

@Service("itripOrderLinkUserService")
public class ItripOrderLinkUserServiceImpl implements ItripOrderLinkUserService {

    @Resource
    private ItripOrderLinkUserMapper itripOrderLinkUserMapper;

    public ItripOrderLinkUser getById(Long id)throws Exception{
        return itripOrderLinkUserMapper.getById(id);
    }

    public List<ItripOrderLinkUser> getListByMap(Map<String,Object> param)throws Exception{
        return itripOrderLinkUserMapper.getListByMap(param);
    }

    public Integer getCountByMap(Map<String,Object> param)throws Exception{
        return itripOrderLinkUserMapper.getCountByMap(param);
    }

    public Integer save(ItripOrderLinkUser itripOrderLinkUser)throws Exception{
            itripOrderLinkUser.setCreationDate(new Date());
            return itripOrderLinkUserMapper.save(itripOrderLinkUser);
    }

    public Integer modify(ItripOrderLinkUser itripOrderLinkUser)throws Exception{
        itripOrderLinkUser.setModifyDate(new Date());
        return itripOrderLinkUserMapper.modify(itripOrderLinkUser);
    }

    public Integer removeById(Long id)throws Exception{
        return itripOrderLinkUserMapper.removeById(id);
    }

    public Page<List<ItripOrderLinkUser>> queryPageByMap(Map<String,Object> param,Integer pageNo,Integer pageSize)throws Exception{
        Integer total = itripOrderLinkUserMapper.getCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? Constants.DEFAULT_PAGE_NO : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? Constants.DEFAULT_PAGE_SIZE : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<ItripOrderLinkUser> itripOrderLinkUserList = itripOrderLinkUserMapper.getListByMap(param);
        page.setRows(itripOrderLinkUserList);
        return page;
    }

}
