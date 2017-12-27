package com.hassdata.survey.po;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{
    private Integer id;
    private String account;
    private String password;
    private String schoolname;
    private String headmaster;
    private String address;
    private String playhousename;
    private Integer booknumber;
    private String remarks;
    private Date lastlogintime;
    private Integer status;
    private Integer operationuser;
    private Integer countyid;
    private String explains;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOperationuser() {
        return operationuser;
    }

    public void setOperationuser(Integer operationuser) {
        this.operationuser = operationuser;
    }

    public String getExplains() {
        return explains;
    }

    public void setExplains(String explains) {
        this.explains = explains;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSchoolname() {
        return schoolname;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }

    public String getHeadmaster() {
        return headmaster;
    }

    public void setHeadmaster(String headmaster) {
        this.headmaster = headmaster;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlayhousename() {
        return playhousename;
    }

    public void setPlayhousename(String playhousename) {
        this.playhousename = playhousename;
    }

    public Integer getBooknumber() {
        return booknumber;
    }

    public Integer getCountyid() {
        return countyid;
    }

    public void setCountyid(Integer countyid) {
        this.countyid = countyid;
    }

    public void setBooknumber(Integer booknumber) {
        this.booknumber = booknumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getLastlogintime() {
        return lastlogintime;
    }

    public void setLastlogintime(Date lastlogintime) {
        this.lastlogintime = lastlogintime;
    }
}
