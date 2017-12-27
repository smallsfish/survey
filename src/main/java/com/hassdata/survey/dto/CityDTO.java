package com.hassdata.survey.dto;

import java.io.Serializable;

public class CityDTO implements Serializable {
    private Integer id;
    private String provincename;
    private String cityname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProvincename() {
        return provincename;
    }

    public void setProvincename(String provincename) {
        this.provincename = provincename;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }
}
