package com.ytzl.itrip.biz.Impl;
import com.ytzl.itrip.biz.Service.ItripHotelFeatureService;
import com.ytzl.itrip.biz.Service.ItripHotelFeatureService;
import com.ytzl.itrip.dao.mapper.ItripHotelFeatureMapper;
import com.ytzl.itrip.beans.model.ItripHotelFeature;
import com.ytzl.itrip.utils.common.EmptyUtils;
import com.ytzl.itrip.utils.common.Page;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.ytzl.itrip.utils.common.Constants;

@Service("itripHotelFeatureService")
public class ItripHotelFeatureServiceImpl implements ItripHotelFeatureService {

    @Resource
    private ItripHotelFeatureMapper itripHotelFeatureMapper;

    public ItripHotelFeature getById(Long id)throws Exception{
        return itripHotelFeatureMapper.getById(id);
    }

    public List<ItripHotelFeature> getListByMap(Map<String,Object> param)throws Exception{
        return itripHotelFeatureMapper.getListByMap(param);
    }

    public Integer getCountByMap(Map<String,Object> param)throws Exception{
        return itripHotelFeatureMapper.getCountByMap(param);
    }

    public Integer save(ItripHotelFeature itripHotelFeature)throws Exception{
            itripHotelFeature.setCreationDate(new Date());
            return itripHotelFeatureMapper.save(itripHotelFeature);
    }

    public Integer modify(ItripHotelFeature itripHotelFeature)throws Exception{
        itripHotelFeature.setModifyDate(new Date());
        return itripHotelFeatureMapper.modify(itripHotelFeature);
    }

    public Integer removeById(Long id)throws Exception{
        return itripHotelFeatureMapper.removeById(id);
    }

    public Page<List<ItripHotelFeature>> queryPageByMap(Map<String,Object> param,Integer pageNo,Integer pageSize)throws Exception{
        Integer total = itripHotelFeatureMapper.getCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? Constants.DEFAULT_PAGE_NO : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? Constants.DEFAULT_PAGE_SIZE : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<ItripHotelFeature> itripHotelFeatureList = itripHotelFeatureMapper.getListByMap(param);
        page.setRows(itripHotelFeatureList);
        return page;
    }

}
