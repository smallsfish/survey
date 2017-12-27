package com.hassdata.survey.po;

import java.io.Serializable;

public class County implements Serializable {
    private Integer id;
    private String countyname;
    private Integer cityid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountyname() {
        return countyname;
    }

    public void setCountyname(String countyname) {
        this.countyname = countyname;
    }

    public Integer getCityid() {
        return cityid;
    }

    public void setCityid(Integer cityid) {
        this.cityid = cityid;
    }
}
