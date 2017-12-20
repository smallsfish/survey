package com.hassdata.survey.dto;

import java.io.Serializable;

public class DataIndexDTO implements Serializable {
    private String id;
    private String name;
    private String time;
    private Integer status;//0 查看详情，1 进行中，2 未开始

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
