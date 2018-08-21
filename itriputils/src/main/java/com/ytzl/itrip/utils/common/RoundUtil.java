package com.ytzl.itrip.utils.common;

/**
 * 获取（1000-9999）随机数工具类
 */
public class RoundUtil {
    private static Integer Round;

    
    public static Integer getRound(){
        Round=(int)Math.round(Math.random() * (9999-1000) + 1000);
        return Round;
    }

//    public static void main(String[] args) {
//        Integer Round=RoundUtil.getRound();
//        System.out.println(Round.toString());
//    }
}
