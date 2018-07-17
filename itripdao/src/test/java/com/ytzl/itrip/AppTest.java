package com.ytzl.itrip;
import com.ytzl.itrip.beans.model.ItripUser;
import com.ytzl.itrip.dao.mapper.ItripUserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.IOException;
import java.io.InputStream;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    public static void main(String[] args) throws Exception{
        InputStream inputStream =Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        ItripUserMapper itripUserMapper = session.getMapper(ItripUserMapper.class);
        ItripUser itripUser = itripUserMapper.getById(12l);
        System.out.println(itripUser.toString());
    }
}
