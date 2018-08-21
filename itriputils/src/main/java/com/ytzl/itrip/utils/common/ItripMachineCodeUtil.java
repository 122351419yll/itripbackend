package com.ytzl.itrip.utils.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 生成机器码类
 */
public class ItripMachineCodeUtil {
    /**
     * 生成机器码
     * @return
     */
    public static String getMachineCode(Integer roomId){
        StringBuffer  orderNo=new StringBuffer();
        //本机机器码
        orderNo.append("M210060");
        orderNo.append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        String preMd5=roomId+System.currentTimeMillis()+RoundUtil.getRound()+"";
        orderNo.append(DigestUtil.hmacSign(preMd5,"yuntuzhilian"));
        return orderNo.toString();
    }

//    public static void main(String[] args) {
//       String a= getMachineCode(1);
//        System.out.println(a);
//    }
}
