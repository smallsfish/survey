package com.hassdata.survey.po;

import java.io.Serializable;

public class Role_Resource implements Serializable {
    private Integer id;
    private Integer rid;
    private Integer reid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getReid() {
        return reid;
    }

    public void setReid(Integer reid) {
        this.reid = reid;
    }
}
