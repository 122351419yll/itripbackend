package com.ytzl.itrip.biz.Contraller;

import com.ytzl.itrip.beans.model.*;
import com.ytzl.itrip.dao.mapper.*;
import com.ytzl.itrip.biz.Service.*;
import com.ytzl.itrip.utils.common.Dto;
import com.ytzl.itrip.utils.common.DtoUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Administrator on 2018/8/8.
 */
@Controller
@RequestMapping("/api")
public class controller {
    @Resource
    private ItripAreaDicService itripAreaDicService;
    @Resource
    private ItripImageService itripImageService;
    @Resource
    private ItripLabelDicService itripLabelDicService;

    @Resource
    private ItripHotelService itripHotelService;
    @Resource
    private ItripHotelFeatureService itripHotelFeatureService;

    @Resource
    private  ItripHotelRoomService itripHotelRoomService;


    //查询国内，国外的热门城市（1：国内，2：国外）/api
    @RequestMapping(value = "/hotel/queryhotcity/{type}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto queryhotcity(@PathVariable Integer type){
        Map map = new HashMap();
        if(type!=0){
            map.put("isHot",1);
            map.put("isChina",type);
            try {
                List<ItripAreaDic> list =itripAreaDicService.getListByMap(map);
                if (list!=null || list.size()!=0) {
                    return DtoUtil.returnSuccess("成功",list);
                }else{
                    return  DtoUtil.returnFail("系统异常，获取失败","30005");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return  DtoUtil.returnFail("系统异常,获取失败","10202");
            }
        }else{
            return  DtoUtil.returnFail("hotelId不能为空","30005");
        }

    }
    //热门城市
    @RequestMapping(value = "/hotel/queryhotelfeature",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto queryhotelfeature(){
            Map map = new HashMap();
            map.put("parentId",16);
            try {
                List<ItripAreaDic> list = itripLabelDicService.getListByMap(map);
                if (list!=null || list.size()!=0){
                    return DtoUtil.returnSuccess("成功",list);
                }else{
                    return  DtoUtil.returnFail("系统异常","30005");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        return DtoUtil.returnFail("系统异常","30005");
    }
    //查询酒店图片
    @RequestMapping(value = "/hotel/getimg/{targetId}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto getimg(@PathVariable(value = "targetId") int targetId){
        Map map =new HashMap();
        map.put("targetId",targetId);
        try {
            List<ItripAreaDic> list =itripImageService.getListByMap(map);
            if(list!=null || list.size()!=0){
                return DtoUtil.returnSuccess("成功",list);
            }else {
                return DtoUtil.returnFail("系统异常","30005");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DtoUtil.returnFail("系统异常","30005");
    }
    //查询酒店id查询酒店特色,商圈
    @RequestMapping(value = "/hotel/getvideodesc/{hotelId} ",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto getvideoedesc(@PathVariable(value = "hotelId") int hotelId){
    if(hotelId!=0){
        itripfeatureVo itripfeatureVo = new itripfeatureVo();
     Map map = new HashMap();
     map.put("id",hotelId);
        try {
            List<ItripHotel> listByMap =itripHotelService.getListByMap(map);
            Map map1 = new HashMap();
            map1.put("parent",listByMap.get(0).getCityId());
            Map map2 = new HashMap();
            map2.put("hotelId",hotelId);
            Map map3 = new HashMap();
            List<ItripHotelFeature> listByMap2 =itripHotelFeatureService .getListByMap(map2);
            map3.put("id",listByMap2.get(0).getFeatureId());
            String hotelName = listByMap.get(0).getHotelName();
            List<ItripAreaDic> listByMap1 = itripAreaDicService.getListByMap(map1);
            List<ItripLabelDic> listByMap3 = itripLabelDicService.getListByMap(map3);
            itripfeatureVo itripLabelVO=new itripfeatureVo();
            itripLabelVO.setHotelName(hotelName);
            itripLabelVO.setTradingAreaNameList(listByMap1);
            itripLabelVO.setHotelFeatureList(listByMap3);
            return DtoUtil.returnSuccess("",itripLabelVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }else{
        return DtoUtil.returnFail("酒店id不能为空","100214");
    }
    return null;
    }
    //根据id查询酒店特色和介绍
    @RequestMapping(value = "/hotel/queryhoteldetails/{id}", method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public Dto queryhoteldetails(@PathVariable(value = "id")int id ){
        if(id!=0){
            try {
                Map map = new HashMap();
                map.put("id",id);
                List<ItripHotel> listhotel = itripHotelService.getListByMap(map);
                Map map1 = new HashMap();
                map1.put("hotelId",id);
                List<ItripHotelFeature> listfeature = itripHotelFeatureService.getListByMap(map1);
                Map map2 = new HashMap();
                map2.put("id",listfeature.get(0).getFeatureId());
                List<ItripLabelDic> itripLabelDicList = itripLabelDicService.getListByMap(map2);
                List<ItripByid> itripByids = new ArrayList<>();
                for (ItripLabelDic itriplableDic:itripLabelDicList) {
                    ItripByid itripByid = new ItripByid();
                    itripByid.setName(itriplableDic.getName());
                    itripByid.setDescription(itriplableDic.getDescription());
                    itripByids.add(itripByid);
                }
                return DtoUtil.returnSuccess("成功",itripByids);
            } catch (Exception e) {
                e.printStackTrace();
                return  DtoUtil.returnFail("系统异常获取失败","10211");
            }
        }else{
            return DtoUtil.returnFail("酒店id不能为空","10210");
        }
    }
    //根据酒店id查询酒店设施
    @RequestMapping(value = "/hotel/queryhotelfacilities/{id}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto queryhotelfacilities(@PathVariable(value = "id") long id){
        if(id!=0){
            Map map = new HashMap();
            map.put("id",id);
            try {
                List<ItripHotel> itripHotels = itripHotelService.getListByMap(map);
                return DtoUtil.returnSuccess("成功",itripHotels.get(0).getFacilities());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            return DtoUtil.returnFail("酒店id不能为空","10206");
        }
        return DtoUtil.returnFail("系统异常获取失败","10207");
    }
    //根据酒店id查询酒店设施
    @RequestMapping(value = "/hotel/queryhotelpolicy/{id}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto queryhotelpolicy(@PathVariable(value = "id") long id){
        if(id!=0){
            Map map = new HashMap();
            map.put("id",id);
            try {
                List<ItripHotel> itripHotels = itripHotelService.getListByMap(map);
                return DtoUtil.returnSuccess("成功",itripHotels.get(0).getHotelPolicy());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            return DtoUtil.returnFail("酒店id不能为空","10208");
        }
        return DtoUtil.returnFail("系统异常，获取失败","10209");
    }
    //查询商圈
    @RequestMapping(value = "/hotel/querytradearea/{cityId}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto querytradearea(@PathVariable(value = "cityId")long cityid){
        if(cityid!=0){
            Map map = new HashMap();
            map.put("id",cityid);
            try {
                List<ItripAreaDic> list =itripAreaDicService.getListByMap(map);
                List<Itripquerytradearea> itripquerytradeareas = new ArrayList<>();
                for (ItripAreaDic itripAreaDIC:list) {
                    Itripquerytradearea itripquerytradearea = new Itripquerytradearea();
                    itripquerytradearea.setId(itripAreaDIC.getId());
                    itripquerytradearea.setName(itripAreaDIC.getName());
                    itripquerytradeareas.add(itripquerytradearea);
                }
                return DtoUtil.returnSuccess("查询成功",itripquerytradeareas);
            } catch (Exception e) {
                e.printStackTrace();
                return DtoUtil.returnFail("系统异常","10204");
            }

        }else{
            return DtoUtil.returnFail("酒店id不能为空","10203");
        }
    }
    //根据targetId查询房型图片
    @RequestMapping(value = "/hotelroom/getimg/{targetId}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto getimg(@PathVariable(value = "targetId")String targetId){
        if(targetId!=null){
            Map map = new HashMap();
            map.put("position",targetId);
            try {
                List<ItripImage> itripImages = itripImageService.getListByMap(map);
                List<ItripgetimgVo> itripgetimgVos=new ArrayList<>();
                for (ItripImage itripImage:itripImages) {
                    ItripgetimgVo itripgetimgVo = new ItripgetimgVo();
                    itripgetimgVo.setPosition(itripImage.getPosition());
                    itripgetimgVo.setImgUrl(itripImage.getImgUrl());
                    itripgetimgVos.add(itripgetimgVo);
                }
                return DtoUtil.returnSuccess("成功",itripgetimgVos);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            return DtoUtil.returnFail("酒店id不能为空","100302");
        }

        return DtoUtil.returnFail("获取酒店房型图片失败","100301");
    }
//查询酒店房间床型列表
    @RequestMapping(value = "/hotelroom/queryhotelroombed",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto queryhotelroombed(){
        Map map = new HashMap();
        map.put("parentId",1);
        try {
            List<ItripLabelDic> itripLabelDicList= itripLabelDicService.getListByMap(map);
            List<Itripqueryhotelroombed> itripqueryhotelroombeds = new ArrayList<>();
            for (ItripLabelDic itripLabelDic:itripLabelDicList) {
                Itripqueryhotelroombed itripqueryhotelroombed = new Itripqueryhotelroombed();
                itripqueryhotelroombed.setId(itripLabelDic.getId());
                itripqueryhotelroombed.setName(itripLabelDic.getName());
                itripqueryhotelroombed.setDescription(itripLabelDic.getDescription());
                itripqueryhotelroombed.setPic(itripLabelDic.getPic());
                itripqueryhotelroombeds.add(itripqueryhotelroombed);
            }
            return DtoUtil.returnSuccess("成功",itripqueryhotelroombeds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DtoUtil.returnFail("获取酒店房间床型失败","100305");
    }
    //查询酒店房间列表
    @RequestMapping(value = "/hotelroom/queryhotelroombyhotel",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto queryhotelroombyhotel(@RequestBody ItripqueryhotelroombyhotelVo itripqueryhotelroombyhotelVo) {
        if(itripqueryhotelroombyhotelVo.getHotelId()!=0){
            if(itripqueryhotelroombyhotelVo.getEndDate()!=null &&
                    itripqueryhotelroombyhotelVo.getStartDate()!=null){
                if(itripqueryhotelroombyhotelVo.getStartDate().compareTo(itripqueryhotelroombyhotelVo.getEndDate())<0){
                    Map map = new HashMap();
                    map.put("endDate",new Date());
                    map.put("hotelId",itripqueryhotelroombyhotelVo.getHotelId());
                    map.put("isBooke",itripqueryhotelroombyhotelVo.getIsBook());
                    map.put("isCancel",itripqueryhotelroombyhotelVo.getIsCancel());
                    map.put("isHavingBreakfast",itripqueryhotelroombyhotelVo.getIsHavingBreakfast());
                    map.put("isTimelyResponse",itripqueryhotelroombyhotelVo.getIsTimelyResponse());
                    map.put("payType",itripqueryhotelroombyhotelVo.getPayType());
                    map.put("roomBedTypeId",itripqueryhotelroombyhotelVo.getRoomBedTypeId());
                    map.put("startDate",new Date());

                    try {
                        List<ItripHotelRoom> itripHotelRooms = itripHotelRoomService.getListByMap(map);
                        List<ItripqueryhotelroombyhotelVo> itripqueryhotelroombyhotelVos = new ArrayList<>();
                        for (ItripHotelRoom itriphotelRoom:itripHotelRooms) {
                            ItripqueryhotelroombyhotelVo itripqueryhotelroombyhotelVo1 =
                                    new ItripqueryhotelroombyhotelVo();
                            itripqueryhotelroombyhotelVo1.setHotelId(itriphotelRoom.getHotelId());
                            itripqueryhotelroombyhotelVo1.setIsBook(itriphotelRoom.getIsBook());
                            itripqueryhotelroombyhotelVo1.setIsCancel(itriphotelRoom.getIsCancel());
                            itripqueryhotelroombyhotelVo1.setIsHavingBreakfast(itriphotelRoom.getIsHavingBreakfast());
                            itripqueryhotelroombyhotelVo1.setIsTimelyResponse(itriphotelRoom.getIsTimelyResponse());
                            itripqueryhotelroombyhotelVo1.setPayType(itriphotelRoom.getPayType());
                            itripqueryhotelroombyhotelVo1.setRoomBedTypeId(itriphotelRoom.getRoomBedTypeId());
                            itripqueryhotelroombyhotelVos.add(itripqueryhotelroombyhotelVo1);
                        }
                        return DtoUtil.returnSuccess("成功",itripqueryhotelroombyhotelVos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }else {
                return DtoUtil.returnFail("酒店入住日期或者退房日期为空","100303");
            }
        }else{
            return DtoUtil.returnFail("酒店id不能为空","100303");
        }
        return DtoUtil.returnFail("系统异常", "100304");
    }
}


