package com.ytzl.itrip.search.Controller;



import com.ytzl.itrip.search.beans.SearchHotCityVO;
import com.ytzl.itrip.search.beans.SearchHotelDetailsVO;
import com.ytzl.itrip.search.beans.SearchHotelFeild;
import com.ytzl.itrip.search.beans.SearchHotelVO;
import com.ytzl.itrip.search.Service.ItripHotelService;
import com.ytzl.itrip.utils.common.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2018/8/7.
 */
@Controller
@RequestMapping(value = "/api")
public class SearchController {
    @Resource
    private ItripHotelService itripHotelService;

    @RequestMapping(value = "/hotellist/searchItripHotelListByHotCity",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto searchItripHotelListByHotCity(@RequestBody SearchHotCityVO searchHotCityVO){
        if (EmptyUtils.isEmpty(searchHotCityVO.getCityId())){
            return DtoUtil.returnFail("城市id不能为空", "");
        }else {
            try {
                List<SearchHotelDetailsVO> voList = itripHotelService.searchItripHotelListByHotCity(searchHotCityVO);
                return DtoUtil.returnDataSuccess(voList);
            } catch (Exception e) {
                e.printStackTrace();
                return DtoUtil.returnFail("系统异常,获取失败","");
            }
        }
    }
    //查询酒店分页(用于查询酒店列表)
    @RequestMapping(value = "/hotellist/searchItripHotelPage",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto searchItripHotelPage(@RequestBody SearchHotelVO searchHotelVO) {
        if (EmptyUtils.isEmpty(searchHotelVO.getDestination())){
            return DtoUtil.returnFail("目的地不能为空","");
        }else{
            try {
                Page<SearchHotelFeild> feildPage = itripHotelService.searchItripHotelPage(searchHotelVO);
                return DtoUtil.returnDataSuccess(feildPage);
            } catch (Exception e) {
                e.printStackTrace();
                return DtoUtil.returnFail("系统异常,获取失败","");
            }
        }
    }


}


