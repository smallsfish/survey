package com.hassdata.survey.po;

import java.io.Serializable;

public class Province implements Serializable {
    private Integer id;
    private String provincename;

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
}
