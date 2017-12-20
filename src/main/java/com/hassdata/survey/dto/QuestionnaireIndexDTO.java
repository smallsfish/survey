package com.hassdata.survey.dto;

import java.io.Serializable;

public class QuestionnaireIndexDTO implements Serializable {
    private String id;
    private String name;
    private String time;
    private Integer status;//0 可以参与，1 暂未开始，2 已结束

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
