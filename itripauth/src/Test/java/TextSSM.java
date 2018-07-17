import com.ytzl.itrip.auth.service.ItripUserService;
import com.ytzl.itrip.beans.model.ItripUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Administrator on 2018/7/10.
 */
//指定运行的平台
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext-public.xml")
public class TextSSM {
//    @Resource
//    private Date date;

    @Resource
    private ItripUserService itripUserService;
    //表示该方法为测试方法
    @Test
    public  void testSpring() throws Exception{
        ItripUser itripUser =  itripUserService.getById(12l);
        System.out.println("呵呵："+itripUser);
    }
}
