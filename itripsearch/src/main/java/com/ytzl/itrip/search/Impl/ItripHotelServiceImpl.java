package com.ytzl.itrip.search.Impl;


import com.ytzl.itrip.search.beans.SearchHotCityVO;
import com.ytzl.itrip.search.beans.SearchHotelDetailsVO;
import com.ytzl.itrip.search.beans.SearchHotelFeild;
import com.ytzl.itrip.search.beans.SearchHotelVO;
import com.ytzl.itrip.search.Service.ItripHotelService;
import com.ytzl.itrip.utils.common.*;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/8/8.
 */
@Service("itripHotelService")
public class ItripHotelServiceImpl implements ItripHotelService {

    private static final String url= PropertiesUtils.get("public.properties","baseUrl");
    @Override
    public Page<SearchHotelFeild> searchItripHotelPage(SearchHotelVO searchHotelVO) throws Exception {
        HttpSolrClient solrClient=new HttpSolrClient(url);
        SolrQuery solrQuery=new SolrQuery();
        solrQuery.set("q", "*:*");// 参数q  查询所有
        //参数fq, 给query增加过滤查询条件
        StringBuffer stringBuffer=new StringBuffer();
        if (EmptyUtils.isNotEmpty(searchHotelVO.getDestination())){
            stringBuffer.append("destination:"+searchHotelVO.getDestination());
        }
        if (EmptyUtils.isNotEmpty(searchHotelVO.getKeywords())){
            stringBuffer.append(" AND keyword:"+searchHotelVO.getKeywords());
        }
        solrQuery.addFilterQuery(stringBuffer.toString());
        //酒店级别
        if (EmptyUtils.isNotEmpty(searchHotelVO.getHotelLevel())){
            solrQuery.addFilterQuery("hotelLevel:"+searchHotelVO.getHotelLevel());
        }
        //酒店价格
        if (EmptyUtils.isNotEmpty(searchHotelVO.getMaxPrice())){
            solrQuery.addFilterQuery("maxPrice:[* TO "+searchHotelVO.getMaxPrice()+"]");
        }
        if (EmptyUtils.isNotEmpty(searchHotelVO.getMinPrice())){
            solrQuery.addFilterQuery("minPrice:["+searchHotelVO.getMinPrice()+" TO *]");
        }
        //酒店特色
        if (EmptyUtils.isNotEmpty(searchHotelVO.getFeatureIds())){
            String[] split = searchHotelVO.getFeatureIds().split(",");
            StringBuffer buffer=new StringBuffer();
            for (int i=0;i<split.length;i++){
                String s = split[i];
                if (i==0){
                    buffer.append("featureIds:*,"+s+",*");
                }else{
                    buffer.append("OR featureIds:*,"+s+",*");
                }
            }
            solrQuery.addFilterQuery(buffer.toString());
        }
        //字段排序
        //升序
        if (EmptyUtils.isNotEmpty(searchHotelVO.getAscSort())){
            solrQuery.addSort(searchHotelVO.getAscSort(), SolrQuery.ORDER.asc);
        }
        //降序
        if (EmptyUtils.isNotEmpty(searchHotelVO.getDescSort())){
            solrQuery.addSort(searchHotelVO.getDescSort(),SolrQuery.ORDER.desc);
        }
        Integer currPage= EmptyUtils.isNotEmpty(searchHotelVO.getPageNo())?searchHotelVO.getPageNo(): Constants.DEFAULT_PAGE_NO;
        Integer pSize=EmptyUtils.isNotEmpty(searchHotelVO.getPageSize())?searchHotelVO.getPageSize():Constants.DEFAULT_PAGE_SIZE;
        solrQuery.setStart((currPage-1)*pSize);
        solrQuery.setRows(searchHotelVO.getPageSize());
        //获取查询返回结果
        QueryResponse response=solrClient.query(solrQuery);
        //获取总条数
        Integer total=Long.valueOf(response.getResults().getNumFound()).intValue();
        Page<SearchHotelFeild> page=new Page<>(currPage,pSize,total);
        //获取数据
        List<SearchHotelFeild> rows = response.getBeans(SearchHotelFeild.class);
        page.setRows(rows);  /*每一页多少值*/
        //获取查询结果
        return page ;
    }

    @Override
    public  List<SearchHotelDetailsVO> searchItripHotelListByHotCity(SearchHotCityVO searchHotCityVO) throws Exception {
        HttpSolrClient solrServer = new HttpSolrClient(url);
        SolrQuery query = new SolrQuery();
        query.set("q", "*:*");// 参数q  查询所有
        //参数fq, 给query增加过滤查询条件
        query.addFilterQuery("cityId:"+searchHotCityVO.getCityId());
        //设置分页参数
        query.setStart(0);
        query.setRows(searchHotCityVO.getCount());//每一页多少值
        //获取查询结果
        QueryResponse response = solrServer.query(query);
        //得到实体对象
        List<SearchHotelDetailsVO> beans = response.getBeans(SearchHotelDetailsVO.class);
        return beans;
    }
}
