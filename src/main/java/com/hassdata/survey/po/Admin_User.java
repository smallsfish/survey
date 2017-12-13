package com.hassdata.survey.po;

import java.io.Serializable;
import java.util.Date;

public class Admin_User implements Serializable{
    private Integer id;
    private String account;
    private String password;
    private String email;
    private String headimage;
    private String identifier;
    private Date createdatetime;
    private Date lastlogintime;
    private String remarks;
    private String salt;
    private Boolean locked;


    public String getCredentialsSalt(){
        return account+salt;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeadimage() {
        return headimage;
    }

    public void setHeadimage(String headimage) {
        this.headimage = headimage;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Date getCreatedatetime() {
        return createdatetime;
    }

    public void setCreatedatetime(Date createdatetime) {
        this.createdatetime = createdatetime;
    }

    public Date getLastlogintime() {
        return lastlogintime;
    }

    public void setLastlogintime(Date lastlogintime) {
        this.lastlogintime = lastlogintime;
    }

    @Override
    public String toString() {
        return "Admin_User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", headimage='" + headimage + '\'' +
                ", identifier='" + identifier + '\'' +
                ", createdatetime='" + createdatetime + '\'' +
                ", lastlogintime='" + lastlogintime + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
