package com.hassdata.survey.dto;

import java.io.Serializable;

public class UserDTO implements Serializable{
    private Integer id;
    private Integer aid;
    private String account;
    private String password;
    private String schoolname;
    private String headmaster;
    private String address;
    private String playhousename;
    private long booknumber;
    private long childrennumber;
    private long teachernumber;
    private long withquestionnairenumber;

    private String lastlogintime;
    private String remarks;
    private String status;
    private String explain;
    private String operationuser;

    public long getBooknumber() {
        return booknumber;
    }

    public void setBooknumber(long booknumber) {
        this.booknumber = booknumber;
    }

    public long getChildrennumber() {
        return childrennumber;
    }

    public void setChildrennumber(long childrennumber) {
        this.childrennumber = childrennumber;
    }

    public long getTeachernumber() {
        return teachernumber;
    }

    public void setTeachernumber(long teachernumber) {
        this.teachernumber = teachernumber;
    }

    public long getWithquestionnairenumber() {
        return withquestionnairenumber;
    }

    public void setWithquestionnairenumber(long withquestionnairenumber) {
        this.withquestionnairenumber = withquestionnairenumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getOperationuser() {
        return operationuser;
    }

    public void setOperationuser(String operationuser) {
        this.operationuser = operationuser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
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

    public String getLastlogintime() {
        return lastlogintime;
    }

    public void setLastlogintime(String lastlogintime) {
        this.lastlogintime = lastlogintime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
