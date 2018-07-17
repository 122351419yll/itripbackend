package com.ytzl.itrip.service.impl;
import com.ytzl.itrip.biz.service.ItripTradeEndsService;
import com.ytzl.itrip.dao.mapper.ItripTradeEndsMapper;
import com.ytzl.itrip.beans.model.ItripTradeEnds;
import com.ytzl.itrip.utils.common.EmptyUtils;
import com.ytzl.itrip.utils.common.Page;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.ytzl.itrip.utils.common.Constants;

@Service("itripTradeEndsService")
public class ItripTradeEndsServiceImpl implements ItripTradeEndsService {

    @Resource
    private ItripTradeEndsMapper itripTradeEndsMapper;

    public ItripTradeEnds getById(Long id)throws Exception{
        return itripTradeEndsMapper.getById(id);
    }

    public List<ItripTradeEnds> getListByMap(Map<String,Object> param)throws Exception{
        return itripTradeEndsMapper.getListByMap(param);
    }

    public Integer getCountByMap(Map<String,Object> param)throws Exception{
        return itripTradeEndsMapper.getCountByMap(param);
    }

    public Integer save(ItripTradeEnds itripTradeEnds)throws Exception{
            itripTradeEnds.setCreationDate(new Date());
            return itripTradeEndsMapper.save(itripTradeEnds);
    }

    public Integer modify(ItripTradeEnds itripTradeEnds)throws Exception{
        itripTradeEnds.setModifyDate(new Date());
        return itripTradeEndsMapper.modify(itripTradeEnds);
    }

    public Integer removeById(Long id)throws Exception{
        return itripTradeEndsMapper.removeById(id);
    }

    public Page<List<ItripTradeEnds>> queryPageByMap(Map<String,Object> param,Integer pageNo,Integer pageSize)throws Exception{
        Integer total = itripTradeEndsMapper.getCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? Constants.DEFAULT_PAGE_NO : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? Constants.DEFAULT_PAGE_SIZE : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<ItripTradeEnds> itripTradeEndsList = itripTradeEndsMapper.getListByMap(param);
        page.setRows(itripTradeEndsList);
        return page;
    }

}
