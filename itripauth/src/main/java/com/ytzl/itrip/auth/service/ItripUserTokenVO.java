package com.ytzl.itrip.auth.service;

/**
 * Created by Administrator on 2018/7/11.
 */
public class ItripUserTokenVO {
    private  long gen;
    private  long abc;
   private  String token;

    public ItripUserTokenVO(long gen, long abc, String token) {
        this.gen = gen;
        this.abc = abc;
        this.token = token;
    }

    public long getGen() {
        return gen;
    }

    public void setGen(long gen) {
        this.gen = gen;
    }

    public long getAbc() {
        return abc;
    }

    public void setAbc(long abc) {
        this.abc = abc;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
