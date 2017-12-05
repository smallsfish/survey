package com.hassdata.survey.dto;

import java.io.Serializable;

public class NewsTypeDTO implements Serializable{
    private Integer aid;
    private Integer id;
    private String name;

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
