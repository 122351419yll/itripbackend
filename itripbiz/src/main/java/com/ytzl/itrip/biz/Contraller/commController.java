package com.ytzl.itrip.biz.Contraller;

import com.sun.xml.internal.ws.developer.MemberSubmissionAddressing;
import com.ytzl.itrip.beans.model.*;
import com.ytzl.itrip.biz.Service.*;
import com.ytzl.itrip.dao.mapper.*;
import com.ytzl.itrip.utils.common.*;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 * Created by l骆明 on 2018/8/14.
 */
@Controller
@RequestMapping("/api")

public class commController {
    @Resource
    private RedisAPI redisAPI;
    @Resource
    private ItripCommentService itripCommentService;
    @Resource
    private ItripHotelService itripHotelService;
    @Resource
    private ItripImageService itripImageService;
    @Resource
    private ItripLabelDicService itripLabelDicService;
    @Resource
    private ValidationUtil validationUtils;
    @RequestMapping(value = "/comment/getcommentlist",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto getcommentlist(@RequestBody itripSearchCommentVO itripSearchCommentVO){
        Map map = new HashMap();
        map.put("hotelId",itripSearchCommentVO.getHotelId());
        map.put("isHavingImg",itripSearchCommentVO.getIsHavingImg());
        map.put("isOk",itripSearchCommentVO.getIsOk());
        map.put("pageNo",itripSearchCommentVO.getPageNo());
        map.put("pageSize",itripSearchCommentVO.getPageSize());
        try {
            List<ItripComment> itripComments = itripCommentService.getListByMap(map);
            for (ItripComment itripComment: itripComments) {
                itripSearchCommentVO itripSearchCommentVO1 = new itripSearchCommentVO();
                Page<ItripComment> page = new Page<>();

            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //根据酒店id查询评论数量
    @RequestMapping(value = "/comment/getcount/{hotelId}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto getcount(@PathVariable(value = "hotelId") long hotelId){
        if(hotelId!=0){
            Map map = new HashMap();
            map.put("hotelId",hotelId);
            try {
                List<ItripComment> listcomment = itripCommentService.getListByMap(map);
                ItripCountComment itripCountComment =new ItripCountComment();
                itripCountComment.setAllcomment(listcomment.size());
                // /所有的评论数量
                map.put("isOk",1);
                Integer list = itripCommentService.getCountByMap(map);//isok为1 的评论
                itripCountComment.setIsOk(list);
                map.remove("isOk");
                map.put("isOk",0);
                Integer listbad = itripCommentService.getCountByMap(map);//isok为0的评论
                itripCountComment.setIsOk(listbad);
                map.put("isHavingImg",1);
                map.remove("isOk");
                Integer img = itripCommentService.getCountByMap(map);//有图片
                itripCountComment.setHavingimg(img);
                return DtoUtil.returnSuccess("成功",itripCountComment);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
          return DtoUtil.returnFail("酒店id不能为空","100018");
        }
    return DtoUtil.returnFail("系统异常，无法获取数量","100018");
    }

    //获取酒店相关信息（酒店名称，酒店星级）
    @RequestMapping(value = "/comment/gethoteldesc/{hotelId}",method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public Dto gethoteldesc(@PathVariable(value = "hotelId") long hotelId){
        if(hotelId!=0){
            Map map = new HashMap();
            map.put("id",hotelId);
            try {
                List<ItripHotel> itripHotels = itripHotelService.getListByMap(map);
                ItripgethoteldescVo itripgethoteldescVo = new ItripgethoteldescVo();
                for (ItripHotel itripHotels1:itripHotels) {
                    itripgethoteldescVo.setHotelId(itripHotels1.getId());
                    itripgethoteldescVo.setHotelLevel(itripHotels1.getHotelLevel());
                    itripgethoteldescVo.setHotelName(itripHotels1.getHotelName());
                }
                return DtoUtil.returnSuccess("获取成功",itripgethoteldescVo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            return DtoUtil.returnFail("酒店Id不能为空","1000021");
        }

        return DtoUtil.returnFail("获取酒店相关信息错误","1000021");
    }

    //根据酒店查询平均分
    @RequestMapping(value = "/comment/gethotelscore/{hotelId}",method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public Dto gethotelscore(@PathVariable(value = "hotelId")long hotelId){
        if(hotelId!=0){
            try {
               Itripgethotelscore itripComment =itripCommentService.getAvgByMap(hotelId);
               return DtoUtil.returnSuccess("成功",itripComment);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return DtoUtil.returnFail("","10001");
    }

    //根据酒店查询平均分
    @RequestMapping(value = "/comment/getimg/{targetId}",method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public Dto getimg(@PathVariable(value = "targetId")long targetId){
        if(targetId!=0){
            Map map = new HashMap();
            map.put("targetId",targetId);
            try {
                List<ItripImage> itripImages =itripImageService.getListByMap(map);
                List<ItripjcommentgetimgVo> itripImages1 = new ArrayList<>();
                for (ItripImage itripImage:itripImages) {
                    ItripjcommentgetimgVo itripjcomment= new ItripjcommentgetimgVo();
                    itripjcomment.setImgUrl(itripImage.getImgUrl());
                    itripjcomment.setPosition(itripImage.getPosition());
                    itripImages1.add(itripjcomment);
                }
                return DtoUtil.returnSuccess("成功",itripImages1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            return DtoUtil.returnFail("酒店id不能为空","1000013");
        }

            return DtoUtil.returnFail("获取评论图片失败","1000012");
    }
    @RequestMapping(value = "/comment/gettraveltype",method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public Dto gettraveltype(){
        Map map = new HashMap();
        map.put("parentId",107);
        try {
            List<ItripLabelDic> itripLabelDicList = itripLabelDicService.getListByMap(map);
            List<Itripcommenttrave> list = new ArrayList<>();
            for (ItripLabelDic itripLabelDic:itripLabelDicList) {
                Itripcommenttrave itripcommenttrave = new Itripcommenttrave();
                itripcommenttrave.setDescription(itripLabelDic.getDescription());
                itripcommenttrave.setId(itripLabelDic.getId());
                itripcommenttrave.setName(itripLabelDic.getName());
                itripcommenttrave.setPic(itripLabelDic.getPic());
                list.add(itripcommenttrave);
            }
            return DtoUtil.returnSuccess("成功",list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DtoUtil.returnFail("获取旅游类型列表错误","1000019");
    }
    //新增评论接口
    @RequestMapping(value = "/comment/add",method = RequestMethod.POST,
            produces = "application/json")
    @ResponseBody
    public Dto add(@RequestBody Itripadd itripadd, HttpServletRequest request){
            String token = request.getHeader("token");
            ItripUser user = validationUtils.getUser(token);
            if (user!=null){
                ItripComment itripComment = new ItripComment();
                BeanUtils.copyProperties(itripadd,itripComment);
                try {
                    Integer itripsave = itripCommentService.save(itripComment);
                    if (itripsave!=0){
                        return DtoUtil.returnSuccess("添加评论成功",itripsave);
                    }else{
                        return DtoUtil.returnFail("添加评论失败","100002");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{

            }
        return DtoUtil.returnFail("token失效，请重新登录","100000");
    }
    //图片删除接口
    @RequestMapping(value = "/comment/delpic",method = RequestMethod.POST,
            produces = "application/json")
    @ResponseBody
    public Dto delpic(@RequestParam(value = "imgName")String imgName,HttpServletRequest request){
        String token = request.getHeader("token");
        ItripUser itripUser = validationUtils.getUser(token);
        if(itripUser!=null) {
            if (imgName != null) {
                try {
                    Boolean IsRight = itripImageService.IsImg(imgName);
                    if (IsRight) {
                        File file = new File(imgName);
                        file.delete();
                        if (IsRight) {
                            return DtoUtil.returnSuccess("图片删除成功");
                        } else {
                            return DtoUtil.returnFail("删除失败", "100000");
                        }
                    } else {
                        return DtoUtil.returnFail("文件不存在，删除失败", "100025");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return DtoUtil.returnFail("token失效，请重新登陆","100000");
    }



}

