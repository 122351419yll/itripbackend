package com.ytzl.itrip.dao.mapper;

/**
 * Created by l骆明 on 2018/8/13.
 */
public class Itripquerytradearea {
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Itripquerytradearea() {
    }

    public Itripquerytradearea(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
