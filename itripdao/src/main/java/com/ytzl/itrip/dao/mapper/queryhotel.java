package com.ytzl.itrip.dao.mapper;

/**
 * Created by Administrator on 2018/8/8.
 */
public class queryhotel {
    private String name;
    private  long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public queryhotel(String name, long id) {
        this.name = name;
        this.id = id;
    }

    public queryhotel() {
    }
}
