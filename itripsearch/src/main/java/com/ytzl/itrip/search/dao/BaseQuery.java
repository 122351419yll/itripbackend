package com.ytzl.itrip.search.dao;
import com.ytzl.itrip.utils.common.Constants;
import com.ytzl.itrip.utils.common.EmptyUtils;
import com.ytzl.itrip.utils.common.Page;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import java.util.List;

/**
 * Created by Administrator on 2018\8\17 0017.
 */
public class BaseQuery<T> {
    private HttpSolrClient solrClient;
    public BaseQuery(String baseUrl){
        solrClient=new HttpSolrClient(baseUrl);
//        设置解析器
        solrClient.setParser(new XMLResponseParser());
//        设置延迟时间
        solrClient.setConnectionTimeout(1000);
    }
    /**
     * 分页查询
     */
    public Page<T> queryPage(SolrQuery solrQuery,Integer pageNo,Integer pageSize,Class clazz)throws Exception{
        Integer currPage= EmptyUtils.isNotEmpty(pageNo)?
                pageNo: Constants.DEFAULT_PAGE_NO;
        Integer pSize=EmptyUtils.isNotEmpty(pageSize)?
                pageSize:Constants.DEFAULT_PAGE_SIZE;
        solrQuery.setStart((currPage-1)*pSize);
        solrQuery.setRows(pSize);
//    获取查询返回结果
        QueryResponse queryResponse=solrClient.query(solrQuery);
//    获取总条数
        Integer total=Long.valueOf(queryResponse.getResults().getNumFound()).intValue();
        Page<T> page=new Page<T>(currPage,pSize,total);
//    获取数据
        List<T> rows=queryResponse.getBeans(clazz);
        page.setRows(rows);
        return page;

    }
    public List<T> queryList(SolrQuery solrQuery,Integer pageSize,Class clazz)throws Exception{
        if (EmptyUtils.isNotEmpty(pageSize)){
            solrQuery.setStart(0);
            solrQuery.setRows(pageSize);
        }
        QueryResponse queryResponse=solrClient.query(solrQuery);
//    返回查询结果
        return queryResponse.getBeans(clazz);
    }
}
