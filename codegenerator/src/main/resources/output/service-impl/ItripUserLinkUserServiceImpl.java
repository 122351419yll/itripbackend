package com.ytzl.itrip.service.impl;
import com.ytzl.itrip.biz.service.ItripUserLinkUserService;
import com.ytzl.itrip.dao.mapper.ItripUserLinkUserMapper;
import com.ytzl.itrip.beans.model.ItripUserLinkUser;
import com.ytzl.itrip.utils.common.EmptyUtils;
import com.ytzl.itrip.utils.common.Page;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.ytzl.itrip.utils.common.Constants;

@Service("itripUserLinkUserService")
public class ItripUserLinkUserServiceImpl implements ItripUserLinkUserService {

    @Resource
    private ItripUserLinkUserMapper itripUserLinkUserMapper;

    public ItripUserLinkUser getById(Long id)throws Exception{
        return itripUserLinkUserMapper.getById(id);
    }

    public List<ItripUserLinkUser> getListByMap(Map<String,Object> param)throws Exception{
        return itripUserLinkUserMapper.getListByMap(param);
    }

    public Integer getCountByMap(Map<String,Object> param)throws Exception{
        return itripUserLinkUserMapper.getCountByMap(param);
    }

    public Integer save(ItripUserLinkUser itripUserLinkUser)throws Exception{
            itripUserLinkUser.setCreationDate(new Date());
            return itripUserLinkUserMapper.save(itripUserLinkUser);
    }

    public Integer modify(ItripUserLinkUser itripUserLinkUser)throws Exception{
        itripUserLinkUser.setModifyDate(new Date());
        return itripUserLinkUserMapper.modify(itripUserLinkUser);
    }

    public Integer removeById(Long id)throws Exception{
        return itripUserLinkUserMapper.removeById(id);
    }

    public Page<List<ItripUserLinkUser>> queryPageByMap(Map<String,Object> param,Integer pageNo,Integer pageSize)throws Exception{
        Integer total = itripUserLinkUserMapper.getCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? Constants.DEFAULT_PAGE_NO : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? Constants.DEFAULT_PAGE_SIZE : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<ItripUserLinkUser> itripUserLinkUserList = itripUserLinkUserMapper.getListByMap(param);
        page.setRows(itripUserLinkUserList);
        return page;
    }

}
